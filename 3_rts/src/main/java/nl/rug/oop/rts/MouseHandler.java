package nl.rug.oop.rts;

import nl.rug.oop.rts.graph.controller.GraphController;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * Handles mouse input.
 */
public class MouseHandler {
    private final GraphController controller;

    public MouseHandler(GraphController controller) {
        this.controller = controller;
    }

    public MouseAdapter getMouseAdapter() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                controller.handleMousePressed(e.getX(), e.getY());
            }
        };
    }

    public MouseMotionAdapter getMouseMotionAdapter() {
        return new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                controller.handleMouseMoved(e.getX(), e.getY());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                controller.handleMouseDragged(e.getX(), e.getY());
            }
        };
    }
}