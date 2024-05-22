package nl.rug.oop.rts;

public class Edge {
    private final int ID;
    private final String name;
    private final Node startNode;
    private final Node endNode;

    public Edge(int ID, String name, Node startNode, Node endNode) {
        this.ID = ID;
        this.name = name;
        this.startNode = startNode;
        this.endNode = endNode;
    }
}
