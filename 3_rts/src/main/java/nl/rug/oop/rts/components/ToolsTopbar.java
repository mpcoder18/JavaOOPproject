package nl.rug.oop.rts.components;

import nl.rug.oop.rts.components.buttons.*;
import nl.rug.oop.rts.graph.controller.GraphController;
import nl.rug.oop.rts.observable.ButtonObserver;

import javax.swing.*;
import java.awt.*;

/**
 * Topbar with buttons to add and remove nodes and edges, simulate a step, and save and load the graph.
 */
public class ToolsTopbar extends JPanel {
    private final JButton addNodeButton;
    private final JButton removeNodeButton;
    private final JButton addEdgeButton;
    private final JButton removeEdgeButton;
    private final JButton simulateStepButton;
    private final JButton saveButton;
    private final JButton loadButton;
    private final JLabel zoomLabel = new JLabel("Zoom (100%)");
    private final JButton zoomInButton;
    private final JButton zoomOutButton;
    private final JButton undoButton;
    private final JButton redoButton;

    /**
     * Create a new tools topbar.
     *
     * @param controller GraphController to control the graph
     */
    public ToolsTopbar(GraphController controller) {
        addNodeButton = new AddNodeButton(controller);
        removeNodeButton = new RemoveNodeButton(controller);
        addEdgeButton = new AddEdgeButton(controller);
        removeEdgeButton = new RemoveEdgeButton(controller);
        simulateStepButton = new SimulationStepButton(controller);
        saveButton = new SaveButton(controller);
        loadButton = new LoadButton(controller);
        zoomInButton = new ZoomInButton(controller);
        zoomOutButton = new ZoomOutButton(controller);
        undoButton = new UndoButton(controller);
        redoButton = new RedoButton(controller);

        ButtonObserver btnObs = new ButtonObserver(controller, removeNodeButton, addEdgeButton,
                removeEdgeButton, simulateStepButton, undoButton, redoButton, zoomLabel);
        controller.addObserver(btnObs);
        btnObs.update();
    }

    /**
     * Add the buttons to the toolbar.
     *
     * @param toolBar JToolBar to add the buttons to
     */
    public void addToToolbar(JToolBar toolBar) {
        toolBar.add(addNodeButton);
        toolBar.add(removeNodeButton);
        toolBar.add(addEdgeButton);
        toolBar.add(removeEdgeButton);
        toolBar.add(simulateStepButton);

        toolBar.addSeparator(new Dimension(20, 20));
        toolBar.add(saveButton);
        toolBar.add(loadButton);
        toolBar.addSeparator(new Dimension(20, 20));

        toolBar.add(zoomLabel);
        toolBar.add(zoomInButton);
        toolBar.add(zoomOutButton);

        toolBar.addSeparator(new Dimension(20, 20));
        toolBar.add(undoButton);
        toolBar.add(redoButton);
    }
}
