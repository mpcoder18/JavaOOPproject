package nl.rug.oop.rts;

import javax.swing.*;

public class ButtonObserver implements Observer {
    private final JButton addNodeButton;
    private final JButton removeNodeButton;
    private final JButton addEdgeButton;
    private final JButton removeEdgeButton;
    private final GraphManager graphManager;

    public ButtonObserver(GraphManager graphManager, JButton addNodeButton, JButton removeNodeButton, JButton addEdgeButton, JButton removeEdgeButton) {
        this.graphManager = graphManager;
        this.addNodeButton = addNodeButton;
        this.removeNodeButton = removeNodeButton;
        this.addEdgeButton = addEdgeButton;
        this.removeEdgeButton = removeEdgeButton;
    }

    @Override
    public void update() {
        removeNodeButton.setEnabled(graphManager.getSelectedNode() != null);
        addEdgeButton.setEnabled(graphManager.getSelectedNode() != null);
        removeEdgeButton.setEnabled(graphManager.getSelectedEdge() != null);
    }
}
