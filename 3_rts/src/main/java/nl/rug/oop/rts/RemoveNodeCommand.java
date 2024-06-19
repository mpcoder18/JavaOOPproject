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
        List<Edge> edgesToRemove = new ArrayList<>();
        for (Edge edge : model.getEdges()) {
            if (edge.getStartNode() == node || edge.getEndNode() == node) {
                edgesToRemove.add(edge);
            }
        }
        for (Edge edge : edgesToRemove) {
            model.removeEdge(edge);
        }
        model.getNodes().remove(node);
    }

    @Override
    public void undo() {
        model.getNodes().add(node);
        for (Edge edge : connectedEdges) {
            model.addEdge(edge);
        }
    }
}
