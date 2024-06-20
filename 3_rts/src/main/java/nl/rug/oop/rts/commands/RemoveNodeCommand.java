package nl.rug.oop.rts.commands;

import nl.rug.oop.rts.util.SoundPlayer;
import nl.rug.oop.rts.graph.Edge;
import nl.rug.oop.rts.graph.Node;
import nl.rug.oop.rts.graph.model.GraphModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Command to remove a node from the graph.
 */
public class RemoveNodeCommand implements Command {
    private final GraphModel model;
    private final Node node;
    private final List<Edge> connectedEdges;

    /**
     * Create a new command to remove a node from the graph.
     *
     * @param model GraphModel to change
     * @param node  Node to remove
     */
    public RemoveNodeCommand(GraphModel model, Node node) {
        this.model = model;
        this.node = node;
        this.connectedEdges = new ArrayList<>(node.getEdgeList());
    }

    @Override
    public void execute() {
        for (Edge edge : connectedEdges) {
            model.getEdges().remove(edge);
            edge.getStartNode().getEdgeList().remove(edge);
            edge.getEndNode().getEdgeList().remove(edge);
        }
        model.getNodes().remove(node);
        SoundPlayer soundPlayer = new SoundPlayer();
        soundPlayer.playSound("delete.wav");
    }

    @Override
    public void undo() {
        model.getNodes().add(node);
        for (Edge edge : connectedEdges) {
            model.getEdges().add(edge);
            edge.getStartNode().getEdgeList().add(edge);
            edge.getEndNode().getEdgeList().add(edge);
        }
        SoundPlayer soundPlayer = new SoundPlayer();
        soundPlayer.playSound("undoDelete.wav");
    }
}