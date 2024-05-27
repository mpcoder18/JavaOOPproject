package nl.rug.oop.rts;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

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
            if (x >= n.getX()-padding && x <= n.getX() + buttonWidth+padding && y >= n.getY()-padding && y <= n.getY() + buttonWidth+padding) {
                node = n;
            }
        }
        return node;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Node node = findNode(e.getX(), e.getY());
        if (node != null) { // Node found, select it if not already selected
            if (graphManager.selectedNode != null) {
                graphManager.selectedNode.setSelected(false);
            }
            node.setSelected(true);
            graphManager.selectedNode = node;
            offsetX = e.getX() - node.getX();
            offsetY = e.getY() - node.getY();
            System.out.println("OffsetX: " + offsetX + " OffsetY: " + offsetY);
        } else { // Clicked on empty space, deselect node if one is selected
            if (graphManager.selectedNode != null) {
                graphManager.selectedNode.setSelected(false);
                graphManager.selectedNode = null;
            }
        }
        graphManager.notifyObservers();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (graphManager.selectedNode != null) {
            System.out.println("OffsetX: " + offsetX + " OffsetY: " + offsetY);
            graphManager.selectedNode.setX(e.getX() - offsetX);
            graphManager.selectedNode.setY(e.getY() - offsetY);
            graphManager.notifyObservers();
        }
    }
}
