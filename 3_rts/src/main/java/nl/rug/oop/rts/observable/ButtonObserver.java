package nl.rug.oop.rts.observable;

import lombok.AllArgsConstructor;
import nl.rug.oop.rts.graph.Edge;
import nl.rug.oop.rts.graph.Node;
import nl.rug.oop.rts.graph.controller.GraphController;

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
    private final JButton stepButton;
    private final JButton undoButton;
    private final JButton redoButton;

    @Override
    public void update() {
        removeNodeButton.setEnabled(graphController.getSelected() instanceof Node);
        addEdgeButton.setEnabled(graphController.getSelected() instanceof Node);
        removeEdgeButton.setEnabled(graphController.getSelected() instanceof Edge);
        // Set the text of the step button based on the current step
        stepButton.setText("▶ (Step " + graphController.getStep() + ")");
        undoButton.setEnabled(graphController.canUndo());
        redoButton.setEnabled(graphController.canRedo());
    }
}
