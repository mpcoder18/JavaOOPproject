package nl.rug.oop.rts.components;

import nl.rug.oop.rts.graph.Edge;
import nl.rug.oop.rts.graph.GraphManager;
import nl.rug.oop.rts.graph.Node;
import nl.rug.oop.rts.observable.ButtonObserver;

import javax.swing.*;

/**
 * Topbar with buttons to add and remove nodes and edges. Controller of the MVC pattern.
 */
public class ToolsTopbar extends JPanel {
    private final JButton addNodeButton;
    private final JButton removeNodeButton;
    private final JButton addEdgeButton;
    private final JButton removeEdgeButton;

    /**
     * Create a new tools topbar.
     *
     * @param graphManager GraphManager to observe
     */
    public ToolsTopbar(GraphManager graphManager) {
        addNodeButton = createAddNodeButton(graphManager);
        removeNodeButton = createRemoveNodeButton(graphManager);
        addEdgeButton = createAddEdgeButton(graphManager);
        removeEdgeButton = createRemoveEdgeButton(graphManager);

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
            if (graphManager.getSelectedEdge() != null) {
                Edge edge = graphManager.getSelectedEdge();
                graphManager.getSelectedEdge().getStartNode().removeEdge(edge);
                graphManager.getSelectedEdge().getEndNode().removeEdge(edge);
                graphManager.deselect();
                graphManager.removeEdge(edge);
                graphManager.setSelectedEdge(null);
            }
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
    }
}
