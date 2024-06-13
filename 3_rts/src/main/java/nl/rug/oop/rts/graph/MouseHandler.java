package nl.rug.oop.rts.graph;

import lombok.AllArgsConstructor;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Class to handle mouse events in the graph.
 */
@AllArgsConstructor
public class MouseHandler extends MouseAdapter {
    private NodeSelector nodeSelector;
    private GraphManager graphManager;

    @Override
    public void mousePressed(MouseEvent e) {
        Selectable selectable = nodeSelector.getSelectable(e.getX(), e.getY());
        if (selectable instanceof Node node) {
            nodeSelector.handleNodeClick(node, e);
        } else if (selectable instanceof Edge edge) {
            nodeSelector.handleEdgeClick(edge);
        } else {
            nodeSelector.handleEmptySpaceClick();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        nodeSelector.setCurrentMousePosition(e.getPoint());
        graphManager.modified();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (graphManager.getSelected() instanceof Node node && node.isSelected()) {
            node.setX(e.getX() - nodeSelector.getOffsetX());
            node.setY(e.getY() - nodeSelector.getOffsetY());
            graphManager.modified();
        }
    }
}
