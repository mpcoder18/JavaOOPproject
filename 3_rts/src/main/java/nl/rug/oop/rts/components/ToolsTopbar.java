package nl.rug.oop.rts.components;

import nl.rug.oop.rts.components.buttons.AddNodeButton;
import nl.rug.oop.rts.graph.Edge;
import nl.rug.oop.rts.graph.Node;
import nl.rug.oop.rts.graph.controller.GraphController;
import nl.rug.oop.rts.graph.model.GraphModel;
import nl.rug.oop.rts.observable.ButtonObserver;

import javax.swing.*;
import java.awt.*;
import java.io.File;

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
        removeNodeButton = createRemoveNodeButton(controller);
        addEdgeButton = createAddEdgeButton(controller);
        removeEdgeButton = createRemoveEdgeButton(controller);
        simulateStepButton = createSimulateStepButton(controller);
        saveButton = createSaveButton(controller);
        loadButton = createLoadButton(controller);
        zoomInButton = createZoomInButton(controller);
        zoomOutButton = createZoomOutButton(controller);
        undoButton = createUndoButton(controller);
        redoButton = createRedoButton(controller);

        ButtonObserver btnObs = new ButtonObserver(controller, removeNodeButton, addEdgeButton,
                removeEdgeButton, simulateStepButton, undoButton, redoButton);
        controller.addObserver(btnObs);
    }

    private JButton createAddNodeButton(GraphController graphController) {
        JButton button = new JButton("Add Node (Q)");
        button.addActionListener(e -> graphController.addNode(graphController.getNodes().size() + 1,
                "Node " + (graphController.getNodes().size() + 1),
                0, 0));
        return button;
    }

    private JButton createRemoveNodeButton(GraphController graphController) {
        JButton button = new JButton("Remove Node");
        button.addActionListener(e -> {
            if (graphController.getSelected() instanceof Node) {
                graphController.removeNode((Node) graphController.getSelected());
                graphController.deselect();
            }
        });
        return button;
    }

    private JButton createAddEdgeButton(GraphController graphController) {
        JButton button = new JButton("Add Edge (E)");
        button.addActionListener(e -> {
            if (graphController.getSelected() instanceof Node) {
                if (graphController.getStartNode() == null) {
                    graphController.setStartNode((Node) graphController.getSelected());
                }
            }
        });
        return button;
    }

    private JButton createRemoveEdgeButton(GraphController graphController) {
        JButton button = new JButton("Remove Edge");
        button.addActionListener(e -> {
            if (graphController.getSelected() instanceof Edge edge) {
                edge.getStartNode().removeEdge(edge);
                edge.getEndNode().removeEdge(edge);
                graphController.deselect();
                graphController.removeEdge(edge);
            }
        });
        return button;
    }

    private JButton createSimulateStepButton(GraphController graphController) {
        JButton button = new JButton("▶ (Step 0)");
        button.addActionListener(e -> graphController.getSimulation().step());
        return button;
    }

    private JButton createSaveButton(GraphController graphController) {
        JButton button = new JButton("Save");
        button.setToolTipText("Save (Ctrl+S)");
        button.addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            graphController.getSaveManager().saveGameChooser(graphController.getModel(), parentFrame);
        });
        return button;
    }
    private JButton createLoadButton(GraphController graphController) {
        JButton button = new JButton("Load");
        button.setToolTipText("Load (Ctrl+O)");
        button.addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            GraphModel loadedModel = graphController.getSaveManager().loadGameChooser(parentFrame);
            if (loadedModel != null) {
                graphController.setModel(loadedModel);
            }
        });
        return button;
    }

    private JButton createZoomInButton(GraphController graphController) {
        JButton button = new JButton("+");
        button.addActionListener(e -> graphController.zoomIn());
        return button;
    }

    private JButton createZoomOutButton(GraphController graphController) {
        JButton button = new JButton("-");
        button.addActionListener(e -> graphController.zoomOut());
        return button;
    }

    private JButton createUndoButton(GraphController graphController) {
        JButton button = new JButton("↩");
        button.setToolTipText("Undo (Ctrl+Z)");
        button.addActionListener(e -> graphController.undo());
        return button;
    }

    private JButton createRedoButton(GraphController graphController) {
        JButton button = new JButton("↪");
        button.setToolTipText("Redo (Ctrl+Y)");
        button.addActionListener(e -> graphController.redo());
        return button;
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

        // TODO: Rework the layout of the toolbar
        toolBar.addSeparator(new Dimension(20, 20));
        toolBar.add(saveButton);
        toolBar.add(loadButton);
        toolBar.addSeparator(new Dimension(20, 20));

        JLabel zoomLabel = new JLabel("Zoom: ");
        toolBar.add(zoomLabel);
        toolBar.add(zoomInButton);
        toolBar.add(zoomOutButton);

        toolBar.addSeparator(new Dimension(20, 20));
        toolBar.add(undoButton);
        toolBar.add(redoButton);
    }
}
