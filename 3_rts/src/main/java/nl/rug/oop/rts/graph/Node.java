package nl.rug.oop.rts.graph;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a node in the graph.
 */
@Getter
public class Node {
    private final int ID;
    @Setter
    private String name;
    private List<Edge> edgeList;
    @Setter
    private int x;
    @Setter
    private int y;
    @Setter
    private boolean selected;

    /**
     * Create a new node.
     *
     * @param id   ID of the node
     * @param name name of the node
     * @param x    x coordinate of the node
     * @param y    y coordinate of the node
     */
    public Node(int id, String name, int x, int y) {
        this.ID = id;
        this.name = name;
        this.edgeList = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.selected = false;
    }

    public void addEdge(Edge edge) {
        edgeList.add(edge);
    }

    public void removeEdge(Edge edge) {
        edgeList.remove(edge);
    }
}
