package nl.rug.oop.rts;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NodeSelector extends MouseAdapter {
    private final GraphManager graphManager;
    private int offsetX;
    private int offsetY;

    public NodeSelector(GraphManager graphManager) {
        this.graphManager = graphManager;
    }

    private Node findNode(int x, int y) {
        Node node = null;
        int padding = 10;
        int buttonWidth = 50;
        for (Node n : graphManager.getNodes()) {
            if (x >= n.getX() - padding && x <= n.getX() + buttonWidth + padding && y >= n.getY() - padding && y <= n.getY() + buttonWidth + padding) {
                node = n;
            }
        }
        return node;
    }

    private Edge findEdge(int x, int y) {
        Edge edge = null;
        int padding = 10;
        for (Edge e : graphManager.getEdges()) {
            // Apply offset to the coordinates of the nodes
            int x1 = e.getStartNode().getX() + 25;
            int y1 = e.getStartNode().getY() + 25;
            int x2 = e.getEndNode().getX() + 25;
            int y2 = e.getEndNode().getY() + 25;
            // Calculate the distance between the point and the line, but not beyond the edge
            double distance = Math.abs((y2 - y1) * x - (x2 - x1) * y + x2 * y1 - y2 * x1) / Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));
            if (distance <= padding && x >= Math.min(x1, x2) - padding && x <= Math.max(x1, x2) + padding && y >= Math.min(y1, y2) - padding && y <= Math.max(y1, y2) + padding) {
                edge = e;
            }
        }
        return edge;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Node node = findNode(e.getX(), e.getY());
        Edge edge2 = findEdge(e.getX(), e.getY());
        if (node != null) { // Node found, select it if not already selected
            if (graphManager.selectedNode != null) {
                graphManager.selectedNode.setSelected(false);
            }
            node.setSelected(true);
            graphManager.selectedNode = node;
            if(graphManager.startNode != null && node != graphManager.startNode) {
                Edge edge = new Edge(graphManager.getEdges().size(), "Edge " + graphManager.getEdges().size(), graphManager.startNode, node);
                graphManager.addEdge(edge);
                graphManager.startNode = null;
                node.setSelected(false);
                graphManager.selectedNode = null;
            }
            if(graphManager.selectedEdge != null) {
                graphManager.selectedEdge.setSelected(false);
                graphManager.selectedEdge = null;
            }
            offsetX = e.getX() - node.getX();
            offsetY = e.getY() - node.getY();
        } else if (edge2 != null) { // Edge found, select it if not already selected
            if (graphManager.selectedEdge != null) {
                graphManager.selectedEdge.setSelected(false);
            }
            edge2.setSelected(true);
            graphManager.selectedEdge = edge2;
            if (graphManager.selectedNode != null) {
                graphManager.selectedNode.setSelected(false);
                graphManager.selectedNode = null;
            }
        } else { // Clicked on empty space, deselect node if one is selected
            if (graphManager.selectedNode != null) {
                graphManager.selectedNode.setSelected(false);
                graphManager.selectedNode = null;
            }
            if (graphManager.selectedEdge != null) {
                graphManager.selectedEdge.setSelected(false);
                graphManager.selectedEdge = null;
            }
        }
        graphManager.notifyObservers();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (graphManager.selectedNode != null) {
            graphManager.selectedNode.setX(e.getX() - offsetX);
            graphManager.selectedNode.setY(e.getY() - offsetY);
            graphManager.notifyObservers();
        }
    }
}
