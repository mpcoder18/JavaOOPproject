package nl.rug.oop.rts.components;

import nl.rug.oop.rts.graph.GraphManager;
import nl.rug.oop.rts.observable.Observer;

import javax.swing.*;

public class OptionsPanel extends JPanel implements Observer {
    private final GraphManager graphManager;
    private final JLabel messageLabel;

    public OptionsPanel(GraphManager graphManager) {
        this.graphManager = graphManager;
        messageLabel = new JLabel("Select a node or edge");
        add(messageLabel);
        this.observe(graphManager);
    }

    public void update() {
        removeAll();
        if (graphManager.getSelectedNode() != null) {
            JLabel nameLabel = new JLabel("Name:");
            JTextField nameField = new JTextField(graphManager.getSelectedNode().getName());
            nameField.addActionListener(e -> {
                graphManager.getSelectedNode().setName(nameField.getText());
                graphManager.notifyAllObservers();
            });
            add(nameLabel);
            add(nameField);
        } else if (graphManager.getSelectedEdge() != null) {
            JLabel nameLabel = new JLabel("Name:");
            JTextField nameField = new JTextField(graphManager.getSelectedEdge().getName());
            nameField.addActionListener(e -> {
                graphManager.getSelectedEdge().setName(nameField.getText());
                graphManager.notifyAllObservers();
            });
            add(nameLabel);
            add(nameField);
        } else {
            add(messageLabel);
        }
        revalidate();
        repaint();
    }
}
