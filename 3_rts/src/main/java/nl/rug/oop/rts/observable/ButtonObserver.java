package nl.rug.oop.rts.observable;

import nl.rug.oop.rts.graph.GraphManager;

import javax.swing.*;

/**
 * Class to update buttons based on the selected node or edge.
 */
public class ButtonObserver implements Observer {
    private final GraphManager graphManager;
    private final JButton removeNodeButton;
    private final JButton addEdgeButton;
    private final JButton removeEdgeButton;

    /**
     * Create a new ButtonObserver.
     *
     * @param gM         GraphManager to observe
     * @param rmNodeBtn  JButton to remove a node
     * @param addEdgeBtn JButton to add an edge
     * @param rmEdgeBtn  JButton to remove an edge
     */
    public ButtonObserver(GraphManager gM, JButton rmNodeBtn, JButton addEdgeBtn, JButton rmEdgeBtn) {
        this.graphManager = gM;
        this.removeNodeButton = rmNodeBtn;
        this.addEdgeButton = addEdgeBtn;
        this.removeEdgeButton = rmEdgeBtn;
    }

    @Override
    public void update() {
        removeNodeButton.setEnabled(graphManager.getSelectedNode() != null);
        addEdgeButton.setEnabled(graphManager.getSelectedNode() != null);
        removeEdgeButton.setEnabled(graphManager.getSelectedEdge() != null);
    }
}
