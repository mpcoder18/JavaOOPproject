package nl.rug.oop.rts;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final int ID;
    @Getter
    private final String name;
    @Getter
    private List<Edge> edgeList;
    @Getter
    private int x;
    @Getter
    private int y;

    public Node(int id, String name, int x, int y) {
        this.ID = id;
        this.name = name;
        this.edgeList = new ArrayList<>();
        this.x = x;
        this.y = y;
    }

}
