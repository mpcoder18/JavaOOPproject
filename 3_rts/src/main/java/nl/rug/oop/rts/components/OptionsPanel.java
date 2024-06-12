package nl.rug.oop.rts.components;

import nl.rug.oop.rts.graph.GraphManager;
import nl.rug.oop.rts.objects.Army;
import nl.rug.oop.rts.objects.Faction;
import nl.rug.oop.rts.observable.Observer;

import javax.swing.*;
import java.awt.*;

/**
 * Panel to display options for selected node or edge.
 */
public class OptionsPanel extends JPanel implements Observer {
    private final GraphManager graphManager;
    private final JLabel messageLabel;

    /**
     * Create a new OptionsPanel.
     *
     * @param graphManager GraphManager to observe
     */
    public OptionsPanel(GraphManager graphManager) {
        this.graphManager = graphManager;
        messageLabel = new JLabel("Select a node or edge");
        add(messageLabel);
        this.observe(graphManager);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new Box.Filler(new Dimension(0, 0), new Dimension(0, 0),
                new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE)));
    }

    private void displayNodeOptions() {
        JTextField nameField = new JTextField(graphManager.getSelectedNode().getName());
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, nameField.getPreferredSize().height));
        nameField.addActionListener(e -> {
            graphManager.getSelectedNode().setName(nameField.getText());
            graphManager.modified();
        });
        add(nameField);

        JButton addArmyButton = new JButton("Add army");
        addArmyButton.addActionListener(e -> {
            int option = JOptionPane.showOptionDialog(this, "Select a faction", "Add army",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    Faction.values(), Faction.values()[0]);
            if (option != JOptionPane.CLOSED_OPTION) {
                graphManager.addArmy(Faction.values()[option]);
            }
        });
        add(addArmyButton);

        // List armies and add remove button
        for (Army army : graphManager.getSelectedNode().getArmies()) {
            JPanel armyPanel = new JPanel();
            armyPanel.setLayout(new BoxLayout(armyPanel, BoxLayout.X_AXIS));
            JLabel armyLabel = new JLabel(army.getFaction().toString());
            JButton removeArmy = new JButton("x");
            removeArmy.addActionListener(e -> {
                graphManager.removeArmy(army);
            });
            armyPanel.add(armyLabel);
            armyPanel.add(removeArmy);
            add(armyPanel);
        }
    }

    private void displayEdgeOptions() {
        JTextField nameField = new JTextField(graphManager.getSelectedEdge().getName());
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, nameField.getPreferredSize().height));
        nameField.addActionListener(e -> {
            graphManager.getSelectedEdge().setName(nameField.getText());
            graphManager.modified();
        });
        JLabel startNodeLabel = new JLabel("Start node: " + graphManager.getSelectedEdge().getStartNode().getName());
        JLabel endNodeLabel = new JLabel("End node: " + graphManager.getSelectedEdge().getEndNode().getName());
        add(nameField);
        add(startNodeLabel);
        add(endNodeLabel);
    }

    private void displayMessage() {
        add(messageLabel);
    }

    /**
     * Update the panel based on the selected node or edge.
     */
    public void update() {
        removeAll();
        if (graphManager.getSelectedNode() != null) {
            displayNodeOptions();
        } else if (graphManager.getSelectedEdge() != null) {
            displayEdgeOptions();
        } else {
            displayMessage();
        }
        revalidate();
        repaint();
    }
}
