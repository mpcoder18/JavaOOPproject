package nl.rug.oop.rts.components;

import nl.rug.oop.rts.observable.ButtonObserver;
import nl.rug.oop.rts.graph.GraphManager;
import nl.rug.oop.rts.graph.Node;

import javax.swing.*;

public class ToolsTopbar extends JPanel {
    private final JButton addNodeButton;
    private final JButton removeNodeButton;
    private final JButton addEdgeButton;
    private final JButton removeEdgeButton;

    public ToolsTopbar(GraphManager graphManager) {
        addNodeButton = new JButton("Add Node");
        removeNodeButton = new JButton("Remove Node");
        addEdgeButton = new JButton("Add Edge");
        removeEdgeButton = new JButton("Remove Edge");

        ButtonObserver buttonObserver = new ButtonObserver(graphManager, addNodeButton, removeNodeButton, addEdgeButton, removeEdgeButton);
        graphManager.addObserver(buttonObserver);
        graphManager.notifyObservers();

        addNodeButton.addActionListener(e -> {
            Node node = new Node(graphManager.getNodes().size() + 1, "Node " + (graphManager.getNodes().size() + 1), 0, 0);
            graphManager.addNode(node);
            graphManager.notifyObservers();
        });

        removeNodeButton.addActionListener(e -> {
            if (graphManager.getSelectedNode() != null) {
                graphManager.removeNode(graphManager.getSelectedNode());
                graphManager.setSelectedNode(null);
            }
            graphManager.notifyObservers();
        });

        addEdgeButton.addActionListener(e -> {
            if (graphManager.getSelectedNode() != null) {
                if (graphManager.getStartNode() == null) {
                    graphManager.setStartNode(graphManager.getSelectedNode());
                }
            }
            graphManager.notifyObservers();
        });

        removeEdgeButton.addActionListener(e -> {
            if (graphManager.getSelectedEdge() != null) {
                graphManager.removeEdge(graphManager.getSelectedEdge());
                graphManager.setSelectedEdge(null);
            }
            graphManager.notifyObservers();
        });
    }

    public void addToToolbar(JToolBar toolBar) {
        toolBar.add(addNodeButton);
        toolBar.add(removeNodeButton);
        toolBar.add(addEdgeButton);
        toolBar.add(removeEdgeButton);
    }
}
