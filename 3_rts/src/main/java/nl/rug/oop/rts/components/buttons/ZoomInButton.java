package nl.rug.oop.rts.components.buttons;

import nl.rug.oop.rts.graph.controller.GraphController;

/**
 * Button to zoom in on the graph.
 */
public class ZoomInButton extends Button {
    /**
     * Create a new ZoomInButton.
     *
     * @param controller - The graph controller.
     */
    public ZoomInButton(GraphController controller) {
        super("+");
        setToolTipText("Zoom in on the graph\n\nShortcut: Ctrl + +");
        addActionListener(e -> controller.zoomIn());
    }
}
