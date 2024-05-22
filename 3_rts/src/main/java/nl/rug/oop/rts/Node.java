package nl.rug.oop.rts;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final int ID;
    private final String name;
    private List<Edge> edgeList;

    public Node(int id, String name) {
        this.ID = id;
        this.name = name;
        this.edgeList = new ArrayList<>();
    }

    public List<Edge> getEdgeList() {
        return edgeList;
    }
}
