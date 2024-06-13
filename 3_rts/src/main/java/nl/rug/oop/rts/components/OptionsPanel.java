package nl.rug.oop.rts.components;

import nl.rug.oop.rts.graph.Edge;
import nl.rug.oop.rts.graph.GraphManager;
import nl.rug.oop.rts.graph.Node;
import nl.rug.oop.rts.graph.Selectable;
import nl.rug.oop.rts.graph.events.Event;
import nl.rug.oop.rts.graph.events.EventType;
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
        createSidePanelOptions();
        createArmiesList();
        createEventsList();
    }

    private void createSidePanelOptions() {
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        JTextField nameField = new JTextField(graphManager.getSelected().getName());
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, nameField.getPreferredSize().height));
        nameField.addActionListener(e -> {
            graphManager.getSelected().setName(nameField.getText());
            graphManager.modified();
        });
        optionsPanel.add(nameField);
        optionsPanel.add(createAddArmyButton());
        optionsPanel.add(createAddEventButton());
        add(optionsPanel);
    }

    // TODO: Extract these methods to separate classes
    private void createArmiesList() {
        JPanel armiesPanel = new JPanel();
        armiesPanel.setLayout(new BoxLayout(armiesPanel, BoxLayout.Y_AXIS));
        for (Army army : graphManager.getSelected().getArmies()) {
            JPanel armyPanel = new JPanel();
            armyPanel.setLayout(new BoxLayout(armyPanel, BoxLayout.X_AXIS));
            JLabel armyLabel = new JLabel(army.getFaction().toString() + " (" + army.getUnits().size() + " units)");
            JButton removeArmy = new JButton("x");
            removeArmy.addActionListener(e -> {
                graphManager.removeArmy(army);
            });
            armyPanel.add(armyLabel);
            armyPanel.add(removeArmy);
            armiesPanel.add(armyPanel);
        }
        armiesPanel.add(new Box.Filler(new Dimension(0, 0), new Dimension(0, 0),
                new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE)));
        JScrollPane armiesScrollPane = new JScrollPane(armiesPanel);
        armiesScrollPane.setPreferredSize(new Dimension(200, 200));
        add(armiesScrollPane);
    }

    private void createEventsList() {
        JPanel eventsPanel = new JPanel();
        eventsPanel.setLayout(new BoxLayout(eventsPanel, BoxLayout.Y_AXIS));
        for (Event event : graphManager.getSelected().getEvents()) {
            JPanel eventPanel = new JPanel();
            eventPanel.setLayout(new BoxLayout(eventPanel, BoxLayout.X_AXIS));
            JLabel eventLabel = new JLabel(event.getType().getFormattedName());
            JButton removeEvent = new JButton("x");
            removeEvent.addActionListener(e -> {
                graphManager.removeEvent(event);
            });
            eventPanel.add(eventLabel);
            eventPanel.add(removeEvent);
            eventsPanel.add(eventPanel);
        }
        eventsPanel.add(new Box.Filler(new Dimension(0, 0), new Dimension(0, 0),
                new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE)));
        JScrollPane eventsScrollPane = new JScrollPane(eventsPanel);
        eventsScrollPane.setPreferredSize(new Dimension(200, 200));
        add(eventsScrollPane);
    }

    private JButton createAddArmyButton() {
        JButton addArmyButton = new JButton("Add army");
        addArmyButton.addActionListener(e -> {
            int option = JOptionPane.showOptionDialog(this, "Select a faction", "Add army",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    Faction.values(), Faction.values()[0]);
            if (option != JOptionPane.CLOSED_OPTION) {
                graphManager.addArmy(Faction.values()[option]);
            }
        });
        return addArmyButton;
    }

    private JButton createAddEventButton() {
        JButton addEventButton = new JButton("Add event");
        addEventButton.addActionListener(e -> {
            EventType[] eventTypes = EventType.values();
            String[] eventTypeNames = new String[eventTypes.length];
            for (int i = 0; i < eventTypes.length; i++) {
                eventTypeNames[i] = eventTypes[i].getFormattedName();
            }
            int option = JOptionPane.showOptionDialog(this, "Select an event", "Add event",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    eventTypeNames, eventTypeNames[0]);
            if (option != JOptionPane.CLOSED_OPTION) {
                EventType selectedEventType = eventTypes[option];
                graphManager.addEvent(selectedEventType);
            }
        });
        return addEventButton;
    }

    // TODO: add events add/remove buttons
    private void displayEdgeOptions() {
        Selectable selectedEdge = graphManager.getSelected();
        String edgeName = selectedEdge == null ? "" : selectedEdge.getName();
        JTextField nameField = new JTextField(edgeName);
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, nameField.getPreferredSize().height));
        nameField.addActionListener(e -> {
            graphManager.getSelected().setName(nameField.getText());
            graphManager.modified();
        });
        Edge edge = (Edge) graphManager.getSelected();
        JLabel startNodeLabel = new JLabel("Start node: " + edge.getStartNode().getName());
        JLabel endNodeLabel = new JLabel("End node: " + edge.getEndNode().getName());
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
        if (graphManager.getSelected() instanceof Node) {
            displayNodeOptions();
        } else if (graphManager.getSelected() instanceof Edge) {
            displayEdgeOptions();
        } else {
            displayMessage();
        }
        revalidate();
        repaint();
    }
}
