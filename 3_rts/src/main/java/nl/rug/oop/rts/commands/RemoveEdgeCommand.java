package nl.rug.oop.rts.commands;

import nl.rug.oop.rts.util.SoundPlayer;
import nl.rug.oop.rts.graph.Edge;
import nl.rug.oop.rts.graph.model.GraphModel;

/**
 * Command to remove an edge from the graph.
 */
public class RemoveEdgeCommand implements Command {
    private final GraphModel model;
    private final Edge edge;

    public RemoveEdgeCommand(GraphModel model, Edge edge) {
        this.model = model;
        this.edge = edge;
    }

    @Override
    public void execute() {
        model.getEdges().remove(edge);
        edge.getStartNode().getEdgeList().remove(edge);
        edge.getEndNode().getEdgeList().remove(edge);
        SoundPlayer soundPlayer = new SoundPlayer();
        soundPlayer.playSound("delete.wav");
    }

    @Override
    public void undo() {
        model.getEdges().add(edge);
        edge.getStartNode().getEdgeList().add(edge);
        edge.getEndNode().getEdgeList().add(edge);
        SoundPlayer soundPlayer = new SoundPlayer();
        soundPlayer.playSound("undoDelete.wav");
    }
}
