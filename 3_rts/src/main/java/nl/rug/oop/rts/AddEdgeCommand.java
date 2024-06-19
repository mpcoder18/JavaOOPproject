package nl.rug.oop.rts;

import nl.rug.oop.rts.graph.Edge;
import nl.rug.oop.rts.graph.model.GraphModel;

public class AddEdgeCommand implements Command {
    private final GraphModel model;
    private final Edge edge;

    public AddEdgeCommand(GraphModel model, Edge edge) {
        this.model = model;
        this.edge = edge;
    }

    @Override
    public void execute() {
        model.getEdges().add(edge);
        edge.getStartNode().getEdgeList().add(edge);
        edge.getEndNode().getEdgeList().add(edge);
    }

    @Override
    public void undo() {
        edge.getStartNode().getEdgeList().remove(edge);
        edge.getEndNode().getEdgeList().remove(edge);
        model.getEdges().remove(edge);
    }
}
