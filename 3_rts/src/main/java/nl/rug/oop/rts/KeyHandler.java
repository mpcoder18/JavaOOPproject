package nl.rug.oop.rts;

import nl.rug.oop.rts.graph.controller.GraphController;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Handles key input.
 */
public class KeyHandler {
    private final GraphController controller;

    /**
     * Create a new KeyHandler.
     *
     * @param controller GraphController to control the graph
     */
    public KeyHandler(GraphController controller) {
        this.controller = controller;
    }

    /**
     * Setup key bindings for the component.
     *
     * @param component Component to setup key bindings for
     */
    public void setupKeyBindings(JComponent component) {
        setupCreateNodeAction(component);
        setupCreateEdgeAction(component);
        setupDeleteAction(component);
        setupUndoAction(component);
        setupRedoAction(component);
        setupSaveAction(component);
        setupLoadAction(component);
        setupZoomAction(component);
        setupStepAction(component);
    }

    private void setupCreateNodeAction(JComponent component) {
        component.getInputMap().put(KeyStroke.getKeyStroke("Q"), "createNode");
        component.getActionMap().put("createNode", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.createNode();
            }
        });
    }

    private void setupCreateEdgeAction(JComponent component) {
        component.getInputMap().put(KeyStroke.getKeyStroke("E"), "createEdge");
        component.getActionMap().put("createEdge", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.createEdge();
            }
        });
    }

    private void setupDeleteAction(JComponent component) {
        component.getInputMap().put(KeyStroke.getKeyStroke("DELETE"), "delete");
        component.getActionMap().put("delete", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.removeSelected();
            }
        });
    }

    private void setupUndoAction(JComponent component) {
        component.getInputMap().put(KeyStroke.getKeyStroke("control Z"), "undo");
        component.getActionMap().put("undo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.undo();
            }
        });
    }

    private void setupRedoAction(JComponent component) {
        component.getInputMap().put(KeyStroke.getKeyStroke("control Y"), "redo");
        component.getActionMap().put("redo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.redo();
            }
        });
    }

    private void setupSaveAction(JComponent component) {
        component.getInputMap().put(KeyStroke.getKeyStroke("control S"), "save");
        component.getActionMap().put("save", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(component);
                controller.saveGameChooser(parentFrame);
            }
        });
    }

    private void setupLoadAction(JComponent component) {
        component.getInputMap().put(KeyStroke.getKeyStroke("control O"), "load");
        component.getActionMap().put("load", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(component);
                controller.loadGameChooser(parentFrame);
            }
        });
    }

    private void setupZoomAction(JComponent component) {
        component.getInputMap().put(KeyStroke.getKeyStroke("control EQUALS"), "zoomIn");
        component.getActionMap().put("zoomIn", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.zoomIn();
            }
        });

        component.getInputMap().put(KeyStroke.getKeyStroke("control MINUS"), "zoomOut");
        component.getActionMap().put("zoomOut", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.zoomOut();
            }
        });
    }

    private void setupStepAction(JComponent component) {
        component.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "step");
        component.getActionMap().put("step", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.stepSimulation();
            }
        });
    }
}
