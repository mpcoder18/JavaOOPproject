package nl.rug.oop.rts;

import nl.rug.oop.rts.graph.Edge;
import nl.rug.oop.rts.graph.Node;
import nl.rug.oop.rts.graph.model.GraphModel;

import java.util.ArrayList;
import java.util.List;

public class RemoveNodeCommand implements Command {
    private final GraphModel model;
    private final Node node;
    private final List<Edge> connectedEdges;

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
        soundPlayer.playSound("src/main/resources/delete.wav");
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
        soundPlayer.playSound("src/main/resources/undoDelete.wav");
    }
}
