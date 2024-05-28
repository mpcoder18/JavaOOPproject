package nl.rug.oop.rts;

import lombok.Getter;
import lombok.Setter;

public class Edge {
    private final int ID;
    @Getter
    private final String name;
    @Getter
    private final Node startNode;
    @Getter
    private final Node endNode;
    @Getter
    @Setter
    private boolean selected;

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
