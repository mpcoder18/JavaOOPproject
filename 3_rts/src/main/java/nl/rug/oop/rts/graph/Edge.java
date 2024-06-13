package nl.rug.oop.rts.graph;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.rts.objects.Army;

import java.util.List;

/**
 * Class to represent an edge in the graph.
 */
@Getter
public class Edge implements Selectable {
    private final int ID;
    private final Node startNode;
    private final Node endNode;
    @Setter
    private String name;
    @Setter
    private boolean selected;
    private List<Army> armies;

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
