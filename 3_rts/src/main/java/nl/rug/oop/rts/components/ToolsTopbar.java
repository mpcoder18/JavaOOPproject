package nl.rug.oop.rts.components;

import nl.rug.oop.rts.graph.Edge;
import nl.rug.oop.rts.graph.GraphManager;
import nl.rug.oop.rts.graph.Node;
import nl.rug.oop.rts.objects.Army;
import nl.rug.oop.rts.objects.Unit;
import nl.rug.oop.rts.observable.ButtonObserver;

import javax.swing.*;
import java.io.File;
import java.util.List;

/**
 * Topbar with buttons to add and remove nodes and edges. Controller of the MVC pattern.
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
     * @param graphManager GraphManager to observe
     */
    public ToolsTopbar(GraphManager graphManager) {
        // TODO: make a separate class for the buttons
        addNodeButton = createAddNodeButton(graphManager);
        removeNodeButton = createRemoveNodeButton(graphManager);
        addEdgeButton = createAddEdgeButton(graphManager);
        removeEdgeButton = createRemoveEdgeButton(graphManager);
        simulateStepButton = createSimulateStepButton(graphManager);
        saveButton = createSaveButton(graphManager);
        loadButton = createLoadButton(graphManager);

        ButtonObserver btnObs = new ButtonObserver(graphManager, removeNodeButton, addEdgeButton, removeEdgeButton);
        graphManager.addObserver(btnObs);
    }

    private JButton createAddNodeButton(GraphManager graphManager) {
        JButton button = new JButton("Add Node");
        button.addActionListener(e -> {
            Node node = new Node(graphManager.getNodes().size() + 1,
                    "Node " + (graphManager.getNodes().size() + 1), 0, 0);
            graphManager.addNode(node);
        });
        return button;
    }

    private JButton createRemoveNodeButton(GraphManager graphManager) {
        JButton button = new JButton("Remove Node");
        button.addActionListener(e -> {
            if (graphManager.getSelected() instanceof Node) {
                graphManager.removeNode((Node) graphManager.getSelected());
                graphManager.deselect();
            }
        });
        return button;
    }

    private JButton createAddEdgeButton(GraphManager graphManager) {
        JButton button = new JButton("Add Edge");
        button.addActionListener(e -> {
            if (graphManager.getSelected() instanceof Node) {
                if (graphManager.getStartNode() == null) {
                    graphManager.setStartNode((Node) graphManager.getSelected());
                }
            }
        });
        return button;
    }

    private JButton createRemoveEdgeButton(GraphManager graphManager) {
        JButton button = new JButton("Remove Edge");
        button.addActionListener(e -> {
            if (graphManager.getSelected() instanceof Edge edge) {
                edge.getStartNode().removeEdge(edge);
                edge.getEndNode().removeEdge(edge);
                graphManager.deselect();
                graphManager.removeEdge(edge);
            }
        });
        return button;
    }

    private JButton createSimulateStepButton(GraphManager graphManager) {
        JButton button = new JButton("▶");
        button.addActionListener(e -> {
            graphManager.getSimulation().step();
        });
        return button;
    }

    private JButton createSaveButton(GraphManager graphManager) {
        JButton button = new JButton("Save");
        button.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save graph");
            fileChooser.setSelectedFile(new File("graph.json"));
            fileChooser.addActionListener(e1 -> {
                if (e1.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
                    System.out.println("Saving to " + fileChooser.getSelectedFile().getAbsolutePath());
                    // TODO: Save the graph to a file
                    Node node = graphManager.getNodes().get(0);
                    System.out.println(node.toJson().toJsonString());
                }
            });
            fileChooser.showSaveDialog(null);
        });
        return button;
    }

    private JButton createLoadButton(GraphManager graphManager) {
        JButton button = new JButton("Load");
        button.addActionListener(e -> {
            graphManager.load();
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
