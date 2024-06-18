package nl.rug.oop.rts.graph.model;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.rts.JsonList;
import nl.rug.oop.rts.JsonObject;
import nl.rug.oop.rts.SaveManager;
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

/**
 * Model for the graph.
 */
@Getter
public class GraphModel implements Observable {
    private final List<Observer> observers;
    private int nodeSize = 80;
    private final EventFactory eventFactory;
    private final List<Node> nodes;
    private final List<Edge> edges;
    @Setter
    private Node startNode;
    private Selectable selected;
    @Setter
    private Simulation simulation;
    @Setter
    private int SimulationStep = 0;
    private final List<EventRecord> eventRecords;
    @Setter
    private int offsetX;
    @Setter
    private int offsetY;
    private Point mousePosition;
    private final SaveManager saveManager;

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

    public GraphModel(Object nodeSize, Object simulationStep, JsonList edges, JsonList nodes, JsonList eventRecords) {
        this.nodes = new ArrayList<>();
        for (Object node : nodes.getValues()) {
            this.nodes.add(new Node((JsonObject) node));
        }
        this.edges = new ArrayList<>();
        for (Object edge : edges.getValues()) {
            Edge newEdge = new Edge((JsonObject) edge);
            // set the start node to the node with id (int) ((JsonObject) edge).get("StartNode"))
            for (Node node : this.nodes) {
                if (node.getID() == (int) ((JsonObject) edge).get("StartNode")) {
                    newEdge.setStartNode(node);
                    break;
                }
            }
            // set the end node to the node with id (int) ((JsonObject) edge).get("EndNode"))
            for (Node node : this.nodes) {
                if (node.getID() == (int) ((JsonObject) edge).get("EndNode")) {
                    newEdge.setEndNode(node);
                    break;
                }
            }
            this.edges.add(newEdge);
        }
        this.observers = new ArrayList<>();
        startNode = null;
        selected = null;
        eventFactory = new EventFactory();
        this.SimulationStep = (int) simulationStep;
        this.eventRecords = new ArrayList<>();
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
                System.out.println("Could not find target edge with ID " + ((JsonObject) eventRecord).get("TargetId"));
            }
            this.eventRecords.add(newEventRecord);
        }
        this.nodeSize = (int) nodeSize;
        saveManager = new SaveManager();
    }

    public void addNode(int ID, String name, int x, int y) {
        nodes.add(new Node(ID, name, x, y));
        notifyAllObservers();
    }

    /**
     * Remove a node from the graph.
     *
     * @param node The node to remove
     */
    public void removeNode(Node node) {
        for (Edge edge : node.getEdgeList()) {
            edges.remove(edge);
        }
        nodes.remove(node);
        notifyAllObservers();
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
        notifyAllObservers();
    }

    public void removeEdge(Edge edge) {
        edges.remove(edge);
        notifyAllObservers();
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
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

    public void removeArmy(Army army) {
        selected.getArmies().remove(army);
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
            Point topLeft = new Point(Math.min(x1, x2), Math.min(y1, y2));
            Point bottomRight = new Point(Math.max(x1, x2), Math.max(y1, y2));
            Rectangle edgeBounds = new Rectangle(topLeft.x, topLeft.y, bottomRight.x - topLeft.x, bottomRight.y - topLeft.y);
            if (edgeBounds.contains(point)) {
                int d = Math.abs((x2 - x1) * (y1 - y0) - (x1 - x0) * (y2 - y1))
                        / (int) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
                if (d <= padding) {
                    return edge;
                }
            }
        }
        return null;
    }

    public void zoomIn() {
        nodeSize += 10;
        notifyAllObservers();
    }

    public void zoomOut() {
        if (nodeSize > 40) {
            nodeSize -= 10;
            notifyAllObservers();
        }
    }
}
