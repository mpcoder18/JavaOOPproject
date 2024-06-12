package nl.rug.oop.rts.graph;

import lombok.Getter;
import nl.rug.oop.rts.objects.Army;
import nl.rug.oop.rts.objects.Faction;
import nl.rug.oop.rts.objects.Unit;
import nl.rug.oop.rts.observable.Observable;
import nl.rug.oop.rts.observable.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Manages the graph. Model of the MVC pattern.
 */

// TODO: this is the model and should handle notification
@Getter
public class GraphManager implements Observable {
    private final List<Node> nodes;
    private final List<Edge> edges;
    private final List<Observer> observers;
    private Node selectedNode;
    private Node startNode;
    private Edge selectedEdge;
    private final int nodeSize = 80;

    /**
     * Create a new GraphManager.
     */
    public GraphManager() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        observers = new ArrayList<>();
        startNode = null;
    }

    public void addNode(Node node) {
        nodes.add(node);
        notifyAllObservers();
    }

    /**
     * Remove a node from the graph.
     *
     * @param node Node to remove
     */
    public void removeNode(Node node) {
        for (Edge edge : node.getEdgeList()) {
            removeEdge(edge);
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

    public void setSelectedNode(Node node) {
        selectedNode = node;
        notifyAllObservers();
    }

    public void setSelectedEdge(Edge edge) {
        selectedEdge = edge;
        notifyAllObservers();
    }

    public void setStartNode(Node node) {
        startNode = node;
        notifyAllObservers();
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void modified() {
        notifyAllObservers();
    }

    /**
     * Add an army to the selected node.
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
        selectedNode.getArmies().add(army);
        notifyAllObservers();
    }

    public void removeArmy(Army army) {
        selectedNode.getArmies().remove(army);
        notifyAllObservers();
    }
}
