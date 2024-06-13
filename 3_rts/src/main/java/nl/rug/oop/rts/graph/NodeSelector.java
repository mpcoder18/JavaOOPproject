package nl.rug.oop.rts.graph;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Class to select nodes and edges in the graph. Controller of the MVC pattern.
 */
public class NodeSelector extends MouseAdapter {
    private final GraphManager graphManager;
    @Getter
    private int offsetX;
    @Getter
    private int offsetY;
    @Getter
    @Setter
    private Point currentMousePosition;

    public NodeSelector(GraphManager graphManager) {
        this.graphManager = graphManager;
    }

    /**
     * Find the node at a certain position.
     *
     * @param x - The x position.
     * @param y - The y position.
     * @return - The node at the position.
     */
    protected Node findNode(int x, int y) {
        Node node = null;
        int padding = 10;
        int buttonWidth = graphManager.getNodeSize();
        for (Node n : graphManager.getNodes()) {
            if (x >= n.getX() - padding && x <= n.getX() + buttonWidth + padding && y >= n.getY()
                    - padding && y <= n.getY() + buttonWidth + padding) {
                node = n;
            }
        }
        return node;
    }

    /**
     * Find the edge at a certain position.
     *
     * @param x - The x position.
     * @param y - The y position.
     * @return - The edge at the position.
     */
    protected Edge findEdge(int x, int y) {
        Edge edge = null;
        int padding = 10;
        for (Edge e : graphManager.getEdges()) {
            // Apply offset to the coordinates of the nodes
            int x1 = e.getStartNode().getX() + graphManager.getNodeSize() / 2;
            int y1 = e.getStartNode().getY() + graphManager.getNodeSize() / 2;
            int x2 = e.getEndNode().getX() + graphManager.getNodeSize() / 2;
            int y2 = e.getEndNode().getY() + graphManager.getNodeSize() / 2;
            // Calculate the distance between the point and the line, but not beyond the edge
            double distance = Math.abs((y2 - y1) * x - (x2 - x1) * y + x2 * y1 - y2 * x1)
                    / Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));
            if (distance <= padding && x >= Math.min(x1, x2) - padding && x <= Math.max(x1, x2)
                    + padding && y >= Math.min(y1, y2) - padding && y <= Math.max(y1, y2) + padding) {
                edge = e;
            }
        }
        return edge;
    }

    /**
     * Handle the click on a node.
     *
     * @param node - The node that was clicked.
     * @param e    - The mouse event.
     */
    protected void handleNodeClick(Node node, MouseEvent e) {
        graphManager.select(node);
        if (graphManager.getStartNode() != null && node != graphManager.getStartNode()) {
            createEdge(node);
        }
        offsetX = e.getX() - node.getX();
        offsetY = e.getY() - node.getY();
    }

    /**
     * Create an edge between the start node and the given node.
     *
     * @param node - The node to connect to the start node.
     */
    protected void createEdge(Node node) {
        for (Edge edge : graphManager.getEdges()) {
            if ((edge.getStartNode() == graphManager.getStartNode() && edge.getEndNode() == node)
                    || (edge.getStartNode() == node && edge.getEndNode() == graphManager.getStartNode())) {
                graphManager.setStartNode(null);
                graphManager.deselect();
                return;
            }
        }
        Edge newEdge = new Edge(graphManager.getEdges().size(), "Edge " + graphManager.getEdges().size(),
                graphManager.getStartNode(), node);
        graphManager.addEdge(newEdge);
        graphManager.setStartNode(null);
        graphManager.deselect();
    }

    /**
     * Handle the click on an edge.
     *
     * @param edge - The edge that was clicked.
     */
    protected void handleEdgeClick(Edge edge) {
        graphManager.deselect();
        graphManager.select(edge);
    }

    /**
     * Handle the click on an empty space.
     */
    protected void handleEmptySpaceClick() {
        graphManager.deselect();
        if (graphManager.getStartNode() != null) {
            graphManager.setStartNode(null);
        }
    }

    /**
     * Get the selectable object at a certain position.
     *
     * @param x - The x position.
     * @param y - The y position.
     * @return - The selectable object at the position.
     */
    protected Selectable getSelectable(int x, int y) {
        Selectable selectable = null;
        Node node = findNode(x, y);
        Edge edge = findEdge(x, y);
        if (node != null) {
            selectable = node;
        } else if (edge != null) {
            selectable = edge;
        }
        return selectable;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Selectable selectable = getSelectable(e.getX(), e.getY());
        if (selectable instanceof Node node) {
            handleNodeClick(node, e);
        } else if (selectable instanceof Edge edge) {
            handleEdgeClick(edge);
        } else {
            handleEmptySpaceClick();
        }
        graphManager.modified();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        currentMousePosition = e.getPoint();
        graphManager.modified();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (graphManager.getSelected() instanceof Node node && node.isSelected()) {
            node.setX(e.getX() - offsetX);
            node.setY(e.getY() - offsetY);
            graphManager.modified();
        }
    }
}
