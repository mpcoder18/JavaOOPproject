package nl.rug.oop.rts.graph;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.rts.graph.events.Event;
import nl.rug.oop.rts.objects.Army;

import java.util.ArrayList;
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
    @Setter
    private List<Army> armies;
    private List<Event> events;

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
        this.armies = new ArrayList<>();
    }

    /**
     * Get the other node connected to this edge.
     *
     * @param node the current node
     * @return the other node
     */
    public Node getOtherNode(Node node) {
        if (node.equals(startNode)) {
            return endNode;
        } else if (node.equals(endNode)) {
            return startNode;
        } else {
            return null;
        }
    }
}
