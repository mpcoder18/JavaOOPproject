package nl.rug.oop.rts.observable;

import lombok.AllArgsConstructor;
import nl.rug.oop.rts.graph.Edge;
import nl.rug.oop.rts.graph.controller.GraphController;
import nl.rug.oop.rts.graph.Node;

import javax.swing.*;

/**
 * Class to update buttons based on the selected node or edge.
 */
@AllArgsConstructor
public class ButtonObserver implements Observer {
    private final GraphController graphController;
    private final JButton removeNodeButton;
    private final JButton addEdgeButton;
    private final JButton removeEdgeButton;

    @Override
    public void update() {
        removeNodeButton.setEnabled(graphController.getSelected() instanceof Node);
        addEdgeButton.setEnabled(graphController.getSelected() instanceof Node);
        removeEdgeButton.setEnabled(graphController.getSelected() instanceof Edge);
    }
}
