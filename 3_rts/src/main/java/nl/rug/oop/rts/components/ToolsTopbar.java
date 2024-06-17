package nl.rug.oop.rts.components;

import nl.rug.oop.rts.graph.Edge;
import nl.rug.oop.rts.graph.Node;
import nl.rug.oop.rts.graph.controller.GraphController;
import nl.rug.oop.rts.observable.ButtonObserver;

import javax.swing.*;
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

    /**
     * Create a new tools topbar.
     *
     * @param controller GraphController to control the graph
     */
    public ToolsTopbar(GraphController controller) {
        addNodeButton = createAddNodeButton(controller);
        removeNodeButton = createRemoveNodeButton(controller);
        addEdgeButton = createAddEdgeButton(controller);
        removeEdgeButton = createRemoveEdgeButton(controller);
        simulateStepButton = createSimulateStepButton(controller);
        saveButton = createSaveButton(controller);
        loadButton = createLoadButton(controller);

        ButtonObserver btnObs = new ButtonObserver(controller, removeNodeButton, addEdgeButton,
                removeEdgeButton, simulateStepButton);
        controller.addObserver(btnObs);
    }

    private JButton createAddNodeButton(GraphController graphController) {
        JButton button = new JButton("Add Node");
        button.addActionListener(e -> {
            graphController.addNode(graphController.getNodes().size() + 1,
                    "Node " + (graphController.getNodes().size() + 1),
                    0, 0);
        });
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
        JButton button = new JButton("Add Edge");
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
        button.addActionListener(e -> {
            graphController.getSimulation().step();
        });
        return button;
    }

    private JButton createSaveButton(GraphController graphController) {
        JButton button = new JButton("Save");
        button.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save graph");
            fileChooser.setSelectedFile(new File("graph.json"));
            fileChooser.addActionListener(e1 -> {
                if (e1.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
                    System.out.println("Saving to " + fileChooser.getSelectedFile().getAbsolutePath());
                    // TODO: Save the graph to a file
                    Node node = graphController.getNodes().get(0);
                    System.out.println(node.toJson().toJsonString());
                }
            });
            fileChooser.showSaveDialog(null);
        });
        return button;
    }

    private JButton createLoadButton(GraphController graphController) {
        JButton button = new JButton("Load");
        button.addActionListener(e -> {
            // TODO: Load the graph from a file
        });
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
        toolBar.add(saveButton);
        toolBar.add(loadButton);
    }
}
