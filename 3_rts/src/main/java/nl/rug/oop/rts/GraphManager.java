package nl.rug.oop.rts;

import java.util.ArrayList;
import java.util.List;

public class GraphManager {
    private List<Node> nodes;
    private List<Edge> edges;

    public GraphManager() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public void addNode(Node node){
        nodes.add(node);

    }

    public void removeNode(Node node) {
        for(Edge edge : node.getEdgeList()) {
            edges.remove(edge);
        }
        nodes.remove(node);
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public void removeEdge(Edge edge) {
        edges.remove(edge);
    }
}
