package nl.rug.oop.rts.graph.model;

import lombok.Getter;
import lombok.Setter;
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
    private final int nodeSize = 80;
    private final EventFactory eventFactory;
    private List<Node> nodes;
    private List<Edge> edges;
    @Setter
    private Node startNode;
    private Selectable selected;
    @Setter
    private Simulation simulation;
    @Setter
    private int SimulationStep = 0;
    private List<EventRecord> eventRecords;
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
}
