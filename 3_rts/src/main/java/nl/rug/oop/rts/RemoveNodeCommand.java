package nl.rug.oop.rts;

import nl.rug.oop.rts.graph.Node;
import nl.rug.oop.rts.graph.model.GraphModel;

public class RemoveNodeCommand implements Command {
    private final GraphModel model;
    private final Node node;

    public RemoveNodeCommand(GraphModel model, Node node) {
        this.model = model;
        this.node = node;
    }

    @Override
    public void execute() {
        model.getNodes().remove(node);
    }

    @Override
    public void undo() {
        model.getNodes().add(node);
    }
}
