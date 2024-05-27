package nl.rug.oop.rts;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class NodeSelector extends MouseAdapter {
    private GraphManager graphManager;

    public NodeSelector(GraphManager graphManager) {
        this.graphManager = graphManager;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (Node node : graphManager.getNodes()) {
            if (node.getX() < e.getX() && e.getX() < node.getX() + 50 && node.getY() < e.getY() && e.getY() < node.getY() + 50) {
                if (!node.isSelected()) {
                    node.setSelected(true);
                    graphManager.selectedNodes.add(node);
                } else {
                    node.setSelected(false);
                    graphManager.selectedNodes.remove(node);
                }
            } else {
                node.setSelected(false);
                graphManager.selectedNodes.remove(node);
            }
        }
        graphManager.notifyObservers();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        graphManager.notifyObservers();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        graphManager.notifyObservers();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (Node node : graphManager.selectedNodes) {
            node.setX(e.getX()-25);
            node.setY(e.getY()-25);
            node.setSelected(false);
        }
        graphManager.selectedNodes.clear();
        graphManager.notifyObservers();
    }
}
