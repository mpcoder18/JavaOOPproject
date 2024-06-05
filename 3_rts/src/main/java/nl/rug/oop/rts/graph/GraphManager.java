package nl.rug.oop.rts.graph;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.rts.observable.Observable;
import nl.rug.oop.rts.observable.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the graph.
 */

// TODO: this is the model and should handle notification
@Getter
public class GraphManager implements Observable {
    private final List<Node> nodes;
    private final List<Edge> edges;
    private final List<Observer> observers;
    @Setter
    private Node selectedNode;
    @Setter
    private Node startNode;
    @Setter
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
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public void removeEdge(Edge edge) {
        edges.remove(edge);
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }
}
