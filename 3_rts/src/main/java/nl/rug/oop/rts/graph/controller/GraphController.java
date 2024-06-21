package nl.rug.oop.rts.graph.controller;

import lombok.Getter;
import nl.rug.oop.rts.util.SaveManager;
import nl.rug.oop.rts.graph.Edge;
import nl.rug.oop.rts.graph.Node;
import nl.rug.oop.rts.graph.Selectable;
import nl.rug.oop.rts.graph.Simulation;
import nl.rug.oop.rts.graph.events.Event;
import nl.rug.oop.rts.graph.events.EventRecord;
import nl.rug.oop.rts.graph.events.EventType;
import nl.rug.oop.rts.graph.model.GraphModel;
import nl.rug.oop.rts.graph.view.GraphView;
import nl.rug.oop.rts.objects.Army;
import nl.rug.oop.rts.objects.Faction;
import nl.rug.oop.rts.observable.Observer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for the graph.
 */
@Getter
public class GraphController {
    private GraphModel model;
    private final GraphView view;
    private final Map<String, Runnable> inputMappings = new HashMap<>();

    /**
     * Create a new GraphController.
     */
    public GraphController() {
        this.model = new GraphModel();
        this.model.setSimulation(new Simulation(this));
        this.view = new GraphView(this);
        model.addObserver(view);
    }

    public void createNode() {
        model.createNode();
    }

    public void removeNode(Node node) {
        model.removeNode(node);
    }

    public List<Node> getNodes() {
        return model.getNodes();
    }

    public void createEdge() {
        model.createEdge();
    }

    public void removeEdge(Edge edge) {
        model.removeEdge(edge);
    }

    public List<Edge> getEdges() {
        return model.getEdges();
    }

    public int getNodeSize() {
        return model.getNodeSize();
    }

    public void addArmy(Faction faction) {
        model.addArmy(faction);
    }

    public void removeArmy(Army army, Selectable selectable) {
        model.removeArmy(army, selectable);
    }

    /**
     * Handle the mouse being pressed.
     *
     * @param x - The x coordinate of the mouse.
     * @param y - The y coordinate of the mouse.
     */
    public void handleMousePressed(int x, int y) {
        Node node = model.findNodeAt(new Point(x, y));
        Edge edge = model.findEdgeAt(new Point(x, y));
        if (node != null) {
            model.select(node);
            model.setOffsetX(x - node.getX());
            model.setOffsetY(y - node.getY());
            if (getStartNode() != null && node != getStartNode()) {
                createStartNodeEdge(node);
            }
            return;
        }
        if (edge != null) {
            model.select(edge);
            model.setOffsetX(x - edge.getStartNode().getX());
            model.setOffsetY(y - edge.getStartNode().getY());
            return;
        }
        if (getStartNode() != null) {
            setStartNode(null);
        }
        model.deselect();
    }

    /**
     * Handle the mouse being dragged.
     *
     * @param x - The x coordinate of the mouse.
     * @param y - The y coordinate of the mouse.
     */
    public void handleMouseDragged(int x, int y) {
        if (getSelected() instanceof Node node && node.isSelected()) {
            node.setX(x - model.getOffsetX());
            node.setY(y - model.getOffsetY());
        }
        model.notifyAllObservers();
    }

    public Selectable getSelected() {
        return model.getSelected();
    }

    public void setSelectedName(String name) {
        model.setSelectedName(name);
    }

    public void addObserver(Observer observer) {
        model.addObserver(observer);
    }

    public void deselect() {
        model.deselect();
    }

    public Node getStartNode() {
        return model.getStartNode();
    }

    public void setStartNode(Node node) {
        model.setStartNode(node);
    }

    public void stepSimulation() {
        model.stepSimulation();
    }

    public int getSimulationStep() {
        return model.getSimulationStep();
    }

    public void createStartNodeEdge(Node endNode) {
        model.createStartNodeEdge(endNode);
    }

    public void handleMouseMoved(int x, int y) {
        if (model.getStartNode() != null || model.getSelected() == null) {
            model.setMousePosition(new Point(x, y));
        }
    }

    /**
     * Check if the nodes are within the bounds of the Panel.
     *
     * @param width  - The width of the screen.
     * @param height - The height of the screen.
     */
    public void checkBounds(int width, int height) {
        for (Node node : model.getNodes()) {
            if (node.getX() < 0) {
                node.setX(0);
            } else if (node.getX() > width - getNodeSize()) {
                node.setX(width - getNodeSize());
            }
            if (node.getY() < 0) {
                node.setY(0);
            } else if (node.getY() > height - getNodeSize()) {
                node.setY(height - getNodeSize());
            }
        }
    }

    public void addEvent(EventType eventType) {
        model.addEvent(eventType);
    }

    public void removeEvent(Event event) {
        model.removeEvent(event);
    }

    public List<EventRecord> getEventRecords() {
        return model.getEventRecords();
    }

    public int getStep() {
        return model.getSimulationStep();
    }

    public SaveManager getSaveManager() {
        return model.getSaveManager();
    }

    public void zoomIn() {
        model.zoomIn();
    }

    public void zoomOut() {
        model.zoomOut();
    }

    public void undo() {
        model.undo();
    }

    public void redo() {
        model.redo();
    }

    public boolean canUndo() {
        return model.canUndo();
    }

    public boolean canRedo() {
        return model.canRedo();
    }

    /**
     * Replace the model with a new model.
     *
     * @param model - The new model.
     */
    public void replaceModel(GraphModel model) {
        List<Observer> oldObservers = new ArrayList<>(this.model.getObservers());
        for (Observer observer : oldObservers) {
            this.model.removeObserver(observer);
        }
        this.model = model;
        this.model.setSimulation(new Simulation(this));
        for (Observer observer : oldObservers) {
            this.model.addObserver(observer);
        }
        this.model.notifyAllObservers();
    }

    public int getZoom() {
        return model.getZoom();
    }

    public void removeSelected() {
        model.removeSelected();
    }

    public void saveGameChooser(JFrame parentFrame) {
        model.getSaveManager().saveGameChooser(model, parentFrame);
    }

    /**
     * Load a game from a file.
     *
     * @param parentFrame the parent frame
     */
    public void loadGameChooser(JFrame parentFrame) {
        GraphModel newModel = model.getSaveManager().loadGameChooser(parentFrame);
        if (newModel != null) {
            replaceModel(newModel);
        }
    }
}
