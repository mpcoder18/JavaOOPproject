package nl.rug.oop.rts.graph.controller;

import lombok.Getter;
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

import java.awt.*;
import java.util.List;

/**
 * Controller for the graph.
 */
@Getter
public class GraphController {
    private GraphModel model;
    private GraphView view;

    /**
     * Create a new GraphController.
     */
    public GraphController() {
        this.model = new GraphModel();
        this.model.setSimulation(new Simulation(this));
        this.view = new GraphView(this);
        model.addObserver(view);
    }

    public void addNode(int ID, String name, int x, int y) {
        model.addNode(ID, name, x, y);
    }

    public void removeNode(Node node) {
        model.removeNode(node);
    }

    public List<Node> getNodes() {
        return model.getNodes();
    }

    public void addEdge(Edge edge) {
        model.addEdge(edge);
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

    public void removeArmy(Army army) {
        model.removeArmy(army);
    }

    /**
     * Handle the mouse being pressed.
     *
     * @param x - The x coordinate of the mouse.
     * @param y - The y coordinate of the mouse.
     */
    public void handleMousePressed(int x, int y) {
        int padding = 10;
        for (Node node : model.getNodes()) {
            int buttonWidth = getNodeSize();
            if (x >= node.getX() - padding && x <= node.getX() + buttonWidth + padding && y >= node.getY()
                    - padding && y <= node.getY() + buttonWidth + padding) {
                model.select(node);
                model.setOffsetX(x - node.getX());
                model.setOffsetY(y - node.getY());
                if (getStartNode() != null && node != getStartNode()) {
                    createStartNodeEdge(node);
                }
                return;
            }
        }
        for (Edge edge : model.getEdges()) {
            int x1 = edge.getStartNode().getX() + getNodeSize() / 2;
            int y1 = edge.getStartNode().getY() + getNodeSize() / 2;
            int x2 = edge.getEndNode().getX() + getNodeSize() / 2;
            int y2 = edge.getEndNode().getY() + getNodeSize() / 2;
            if (x >= Math.min(x1, x2) - padding && x <= Math.max(x1, x2) + padding &&
                    y >= Math.min(y1, y2) - padding && y <= Math.max(y1, y2) + padding) {
                model.select(edge);
                return;
            }
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

    public Simulation getSimulation() {
        return model.getSimulation();
    }

    public int getSimulationStep() {
        return model.getSimulationStep();
    }

    public void setSimulationStep(int step) {
        model.setSimulationStep(step);
    }

    public void createStartNodeEdge(Node endNode) {
        model.createStartNodeEdge(endNode);
    }

    public Point getMousePosition() {
        return model.getMousePosition();
    }

    public void handleMouseMoved(int x, int y) {
        model.setMousePosition(new Point(x, y));
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
}
