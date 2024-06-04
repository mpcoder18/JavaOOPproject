package nl.rug.oop.rts.graph;

import lombok.Getter;
import lombok.Setter;

/**
 * Class to represent an edge in the graph.
 */
@Getter
public class Edge {
    private final int ID;
    private final Node startNode;
    private final Node endNode;
    @Setter
    private String name;
    @Setter
    private boolean selected;

    /**
     * Create a new edge.
     *
     * @param ID        ID of the edge
     * @param name      name of the edge
     * @param startNode start node of the edge
     * @param endNode   end node of the edge
     */
    public Edge(int ID, String name, Node startNode, Node endNode) {
        this.ID = ID;
        this.name = name;
        this.startNode = startNode;
        this.endNode = endNode;
        startNode.addEdge(this);
        endNode.addEdge(this);
        this.selected = false;
    }
}
