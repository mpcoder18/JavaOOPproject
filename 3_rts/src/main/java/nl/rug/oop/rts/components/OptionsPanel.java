package nl.rug.oop.rts.components;

import nl.rug.oop.rts.graph.Edge;
import nl.rug.oop.rts.graph.Node;
import nl.rug.oop.rts.graph.Selectable;
import nl.rug.oop.rts.graph.controller.GraphController;
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
    private final GraphController graphController;
    private final JLabel messageLabel;

    /**
     * Create a new OptionsPanel.
     *
     * @param graphController GraphManager to observe
     */
    public OptionsPanel(GraphController graphController) {
        this.graphController = graphController;
        messageLabel = new JLabel("Select a node or edge");
        add(messageLabel);
        this.observe(graphController.getModel());
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
        JTextField nameField = new JTextField(graphController.getSelected().getName());
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, nameField.getPreferredSize().height));
        nameField.addActionListener(e -> {
            graphController.getSelected().setName(nameField.getText());
            graphController.getModel().notifyAllObservers();
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
        for (Army army : graphController.getSelected().getArmies()) {
            JPanel armyPanel = new JPanel();
            armyPanel.setLayout(new BoxLayout(armyPanel, BoxLayout.X_AXIS));
            JLabel armyLabel = new JLabel(army.getFaction().toString() + " (" + army.getUnits().size() + " units)");
            JButton removeArmy = new JButton("x");
            removeArmy.addActionListener(e -> {
                graphController.removeArmy(army);
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
        for (Event event : graphController.getSelected().getEvents()) {
            JPanel eventPanel = new JPanel();
            eventPanel.setLayout(new BoxLayout(eventPanel, BoxLayout.X_AXIS));
            JLabel eventLabel = new JLabel(event.getType().getFormattedName());
            JButton removeEvent = new JButton("x");
            removeEvent.addActionListener(e -> {
                graphController.removeEvent(event);
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

    private void createPastEventsList() {
        JPanel pastEventsPanel = new JPanel();
        pastEventsPanel.setLayout(new BoxLayout(pastEventsPanel, BoxLayout.Y_AXIS));
        graphController.getEventRecords().forEach(eventRecord -> {
            JPanel eventPanel = new JPanel();
            eventPanel.setLayout(new BoxLayout(eventPanel, BoxLayout.X_AXIS));
            JLabel eventLabel = new JLabel(eventRecord.getStep() + ". " +
                    eventRecord.getEvent().getType().getFormattedName() +
                    " at " + eventRecord.getTarget().getName());
            eventLabel.setToolTipText(eventRecord.getEvent().getDescription());
            eventPanel.add(eventLabel);
            pastEventsPanel.add(eventPanel);
        });
        pastEventsPanel.add(new Box.Filler(new Dimension(0, 0), new Dimension(0, 0),
                new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE)));
        JScrollPane pastEventsScrollPane = new JScrollPane(pastEventsPanel);
        pastEventsScrollPane.setPreferredSize(new Dimension(200, 200));
        add(pastEventsScrollPane);
    }

    private JButton createAddArmyButton() {
        JButton addArmyButton = new JButton("Add army");
        addArmyButton.setToolTipText("Add an army to the selected node");
        addArmyButton.addActionListener(e -> {
            int option = JOptionPane.showOptionDialog(this, "Select a faction", "Add army",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    Faction.values(), Faction.values()[0]);
            if (option != JOptionPane.CLOSED_OPTION) {
                graphController.addArmy(Faction.values()[option]);
            }
        });
        return addArmyButton;
    }

    private JButton createAddEventButton() {
        JButton addEventButton = new JButton("Add event");
        addEventButton.setToolTipText("Add an event to the selected node/edge");
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
                graphController.addEvent(selectedEventType);
            }
        });
        return addEventButton;
    }

    private void displayEdgeOptions() {
        Selectable selectedEdge = graphController.getSelected();
        String edgeName = selectedEdge == null ? "" : selectedEdge.getName();
        JTextField nameField = new JTextField(edgeName);
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, nameField.getPreferredSize().height));
        nameField.addActionListener(e -> {
            graphController.getSelected().setName(nameField.getText());
            graphController.notifyAll();
        });
        Edge edge = (Edge) graphController.getSelected();
        JLabel startNodeLabel = new JLabel("Start node: " + edge.getStartNode().getName());
        JLabel endNodeLabel = new JLabel("End node: " + edge.getEndNode().getName());
        add(nameField);
        add(startNodeLabel);
        add(endNodeLabel);
        add(createAddEventButton());
        createArmiesList();
        createEventsList();
    }

    private void displayMessage() {
        add(messageLabel);
        createPastEventsList();
    }

    /**
     * Update the panel based on the selected node or edge.
     */
    public void update() {
        removeAll();
        if (graphController.getSelected() instanceof Node) {
            displayNodeOptions();
        } else if (graphController.getSelected() instanceof Edge) {
            displayEdgeOptions();
        } else {
            displayMessage();
        }
        revalidate();
        repaint();
    }
}
