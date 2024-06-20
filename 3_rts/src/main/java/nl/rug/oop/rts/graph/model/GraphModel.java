package nl.rug.oop.rts.graph.model;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.rts.*;
import nl.rug.oop.rts.graph.Edge;
import nl.rug.oop.rts.graph.Node;
import nl.rug.oop.rts.graph.Selectable;
import nl.rug.oop.rts.graph.Simulation;
import nl.rug.oop.rts.graph.events.Event;
import nl.rug.oop.rts.graph.events.EventFactory;
import nl.rug.oop.rts.graph.events.EventRecord;
import nl.rug.oop.rts.graph.events.EventType;
import nl.rug.oop.rts.objects.Army;
import nl.rug.oop.rts.objects.Faction;
import nl.rug.oop.rts.objects.Unit;
import nl.rug.oop.rts.observable.Observable;
import nl.rug.oop.rts.observable.Observer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * Model for the graph.
 */
@Getter
public class GraphModel implements Observable {
    private final List<Observer> observers;
    private final EventFactory eventFactory;
    private final List<Node> nodes;
    private final List<Edge> edges;
    private final List<EventRecord> eventRecords;
    private final SaveManager saveManager;
    private final Stack<Command> undoStack = new Stack<>();
    private final Stack<Command> redoStack = new Stack<>();
    private int nodeSize = 80;
    private Node startNode;
    private Selectable selected;
    @Setter
    private Simulation simulation;
    @Setter
    private int SimulationStep = 0;
    @Setter
    private int offsetX;
    @Setter
    private int offsetY;
    private Point mousePosition;

    /**
     * Create a new GraphModel.
     */
    public GraphModel() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        observers = new ArrayList<>();
        startNode = null;
        selected = null;
        eventFactory = new EventFactory();
        eventRecords = new ArrayList<>();
        saveManager = new SaveManager();
    }

    /**
     * Create a new GraphModel from JsonObjects and JsonLists.
     *
     * @param nodeSize       The size of the nodes
     * @param simulationStep The current simulation step
     * @param edges          The edges in the graph
     * @param nodes          The nodes in the graph
     * @param eventRecords   The event records in the graph
     */
    public GraphModel(Object nodeSize, Object simulationStep, JsonList edges, JsonList nodes, JsonList eventRecords) {
        this.nodes = initializeNodes(nodes);
        this.edges = initializeEdges(edges);
        this.observers = new ArrayList<>();
        startNode = null;
        selected = null;
        eventFactory = new EventFactory();
        this.SimulationStep = (int) simulationStep;
        this.eventRecords = initializeEventRecords(eventRecords);
        this.nodeSize = (int) nodeSize;
        saveManager = new SaveManager();
    }

    private List<Node> initializeNodes(JsonList nodes) {
        List<Node> nodeList = new ArrayList<>();
        if (nodes != null) {
            for (Object node : nodes.getValues()) {
                nodeList.add(new Node((JsonObject) node));
            }
        }
        return nodeList;
    }

    private List<Edge> initializeEdges(JsonList edges) {
        List<Edge> edgeList = new ArrayList<>();
        if (edges != null) {
            for (Object edge : edges.getValues()) {
                Edge newEdge = new Edge((JsonObject) edge);
                // set the start node to the node with id (int) ((JsonObject) edge).get("StartNode"))
                for (Node node : this.nodes) {
                    if (node.getID() == (int) ((JsonObject) edge).get("StartNode")) {
                        newEdge.setStartNode(node);
                        node.addEdge(newEdge);
                        break;
                    }
                }
                // set the end node to the node with id (int) ((JsonObject) edge).get("EndNode"))
                for (Node node : this.nodes) {
                    if (node.getID() == (int) ((JsonObject) edge).get("EndNode")) {
                        newEdge.setEndNode(node);
                        node.addEdge(newEdge);
                        break;
                    }
                }
                edgeList.add(newEdge);
            }
        }
        return edgeList;
    }

    private List<EventRecord> initializeEventRecords(JsonList eventRecords) {
        List<EventRecord> eventRecordList = new ArrayList<>();
        if (eventRecords != null) {
            for (Object eventRecord : eventRecords.getValues()) {
                EventRecord newEventRecord = new EventRecord((JsonObject) eventRecord);
                if (((JsonObject) eventRecord).get("TargetType").equals("Node")) {
                    for (Node node : this.nodes) {
                        if (node.getID() == Integer.parseInt((String) ((JsonObject) eventRecord).get("TargetId"))) {
                            newEventRecord.setTarget(node);
                            break;
                        }
                    }
                } else {
                    for (Edge edge : this.edges) {
                        if (edge.getID() == Integer.parseInt((String) ((JsonObject) eventRecord).get("TargetId"))) {
                            newEventRecord.setTarget(edge);
                            break;
                        }
                    }
                }
                eventRecordList.add(newEventRecord);
            }
        }
        return eventRecordList;
    }

    /**
     * Add a node to the graph.
     *
     * @param ID ID of the node
     * @param name Name of the node
     * @param x X coordinate of the node
     * @param y Y coordinate of the node
     */
    public void addNode(int ID, String name, int x, int y) {
        Node node = new Node(ID, name, x, y);
        Command addNodeCommand = new AddNodeCommand(this, node);
        addNodeCommand.execute();
        undoStack.push(addNodeCommand);
        redoStack.clear();
        notifyAllObservers();
    }

    /**
     * Remove a node from the graph.
     *
     * @param node The node to remove
     */
    public void removeNode(Node node) {

        Command removeNodeCommand = new RemoveNodeCommand(this, node);
        removeNodeCommand.execute();
        undoStack.push(removeNodeCommand);
        redoStack.clear();
        notifyAllObservers();
    }

    /**
     * Add an edge to the graph.
     *
     * @param edge The edge to add
     */
    public void addEdge(Edge edge) {
        Command addEdgeCommand = new AddEdgeCommand(this, edge);
        addEdgeCommand.execute();
        undoStack.push(addEdgeCommand);
        redoStack.clear();
        notifyAllObservers();
    }

    /**
     * Remove an edge from the graph.
     *
     * @param edge The edge to remove
     */
    public void removeEdge(Edge edge) {
        Command removeEdgeCommand = new RemoveEdgeCommand(this, edge);
        removeEdgeCommand.execute();
        undoStack.push(removeEdgeCommand);
        redoStack.clear();
        notifyAllObservers();
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Add an army to the selected node.
     *
     * @param faction Faction of the army
     */
    public void addArmy(Faction faction) {
        Random rand = new Random();
        int numUnits = rand.nextInt(41) + 10; // Random number between 10 and 50
        List<Unit> units = new ArrayList<>();
        for (int i = 0; i < numUnits; i++) {
            units.add(new Unit(rand.nextInt(3) + 1, rand.nextInt(3) + 1,
                    faction.getUnitNames().get(rand.nextInt(faction.getUnitNames().size()))));
        }
        Army army = new Army(units, faction);
        selected.getArmies().add(army);
        notifyAllObservers();
    }

    /**
     * Remove an army from the selected node.
     *
     * @param army       The army to remove
     * @param selectable The node to remove the army from
     */
    public void removeArmy(Army army, Selectable selectable) {
        selectable.getArmies().remove(army);
        SoundPlayer soundPlayer = new SoundPlayer();
        soundPlayer.playSound("armyDeath.wav");
        notifyAllObservers();
    }

    /**
     * Add an event to the selected node.
     *
     * @param eventType Type of event to add
     */
    public void addEvent(EventType eventType) {
        Event event = eventFactory.createEvent(eventType);
        selected.getEvents().add(event);
        notifyAllObservers();
    }

    public void removeEvent(Event event) {
        selected.getEvents().remove(event);
        notifyAllObservers();
    }

    /**
     * Select a node or edge.
     *
     * @param selected The node or edge to select
     */
    public void select(Selectable selected) {
        if (this.selected != null) {
            this.selected.deselect();
        }
        selected.select();
        this.selected = selected;
        notifyAllObservers();
    }

    /**
     * Deselect the currently selected node or edge.
     */
    public void deselect() {
        if (selected != null) {
            selected.deselect();
            selected = null;
            notifyAllObservers();
        }
    }

    public void setStartNode(Node startNode) {
        this.startNode = startNode;
        notifyAllObservers();
    }

    /**
     * Create a new edge between the start node and the end node.
     *
     * @param endNode The end node of the edge
     */
    public void createStartNodeEdge(Node endNode) {
        for (Edge edge : edges) {
            if ((edge.getStartNode() == startNode && edge.getEndNode() == endNode)
                    || (edge.getStartNode() == endNode && edge.getEndNode() == startNode)) {
                setStartNode(null);
                deselect();
                return;
            }
        }
        Edge newEdge = new Edge(edges.size(), "Edge " + edges.size(), startNode, endNode);
        addEdge(newEdge);
        setStartNode(null);
        deselect();
    }

    public void setMousePosition(Point mousePosition) {
        this.mousePosition = mousePosition;
        notifyAllObservers();
    }

    /**
     * Convert the current state of the graph to a JSON object.
     *
     * @return The JSON object representing the graph
     */
    public JsonObject toJson() {
        JsonObject json = new JsonObject()
                .put("NodeSize", nodeSize)
                .put("SimulationStep", SimulationStep);
        JsonList jsonNodes = new JsonList(new Object[0]);
        for (Node node : nodes) {
            jsonNodes.add(node.toJson());
        }
        json.put("Nodes", jsonNodes);
        JsonList jsonEdges = new JsonList(new Object[0]);
        for (Edge edge : edges) {
            jsonEdges.add(edge.toJson());
        }
        json.put("Edges", jsonEdges);
        JsonList jsonEventRecords = new JsonList(new Object[0]);
        for (EventRecord eventRecord : eventRecords) {
            jsonEventRecords.add(eventRecord.toJson());
        }
        json.put("EventRecords", jsonEventRecords);
        return json;
    }

    /**
     * Find a node at a given point.
     *
     * @param point The point to check
     * @return The node at the point
     */
    public Node findNodeAt(Point point) {
        for (int i = nodes.size() - 1; i >= 0; i--) {
            Node node = nodes.get(i);
            if (node.getX() <= point.x && point.x <= node.getX() + nodeSize
                    && node.getY() <= point.y && point.y <= node.getY() + nodeSize) {
                return node;
            }
        }
        return null;
    }

    /**
     * Find an edge at a given point.
     *
     * @param point The point to check
     * @return The edge at the point
     */
    public Edge findEdgeAt(Point point) {
        int padding = 20;
        for (int i = edges.size() - 1; i >= 0; i--) {
            Edge edge = edges.get(i);
            int x0 = point.x;
            int y0 = point.y;
            int x1 = edge.getStartNode().getX() + nodeSize / 2;
            int y1 = edge.getStartNode().getY() + nodeSize / 2;
            int x2 = edge.getEndNode().getX() + nodeSize / 2;
            int y2 = edge.getEndNode().getY() + nodeSize / 2;

            boolean isApproxHorizontal = Math.abs(y2 - y1) <= 2 * padding;
            boolean isApproxVertical = Math.abs(x2 - x1) <= 2 * padding;

            if (isApproxHorizontal) {
                if (Math.abs(y1 - y0) <= padding && Math.min(x1, x2) <= x0 && x0 <= Math.max(x1, x2)) {
                    return edge;
                }
            } else if (isApproxVertical) {
                if (Math.abs(x1 - x0) <= padding && Math.min(y1, y2) <= y0 && y0 <= Math.max(y1, y2)) {
                    return edge;
                }
            } else {
                Point topLeft = new Point(Math.min(x1, x2), Math.min(y1, y2));
                Point bottomRight = new Point(Math.max(x1, x2), Math.max(y1, y2));
                Rectangle edgeBounds = new Rectangle(topLeft.x, topLeft.y,
                        bottomRight.x - topLeft.x, bottomRight.y - topLeft.y);
                if (edgeBounds.contains(point)) {
                    int d = Math.abs((x2 - x1) * (y1 - y0) - (x1 - x0) * (y2 - y1))
                            / (int) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
                    if (d <= padding) {
                        return edge;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Zoom in the graph.
     */
    public void zoomIn() {
        if (nodeSize < 200) {
            nodeSize += 10;
            notifyAllObservers();
        }
    }

    /**
     * Zoom out the graph.
     */
    public void zoomOut() {
        if (nodeSize > 40) {
            nodeSize -= 10;
            notifyAllObservers();
        }
    }

    /**
     * Undo the last command.
     */
    public void undo() {
        if (!undoStack.isEmpty()) {
            Command command = undoStack.pop();
            command.undo();
            redoStack.push(command);
            notifyAllObservers();
        }
    }

    /**
     * Redo the last undone command.
     */
    public void redo() {
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            command.execute();
            undoStack.push(command);
            notifyAllObservers();
        }
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }

    public void setSelectedName(String name) {
        selected.setName(name);
        notifyAllObservers();
    }

    /**
     * Step the simulation.
     */
    public void stepSimulation() {
        simulation.step();
        SimulationStep++;
        notifyAllObservers();
    }

    public int getZoom() {
        return (int) ((double) nodeSize / 80 * 100);
    }
}
