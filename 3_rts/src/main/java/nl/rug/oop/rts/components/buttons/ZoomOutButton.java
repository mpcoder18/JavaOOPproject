package nl.rug.oop.rts.components.buttons;

import nl.rug.oop.rts.graph.controller.GraphController;

/**
 * Button to zoom out on the graph.
 */
public class ZoomOutButton extends Button {
    /**
     * Create a new ZoomOutButton.
     *
     * @param controller - The graph controller.
     */
    public ZoomOutButton(GraphController controller) {
        super("-");
        setToolTipText("Zoom out on the graph\n\nShortcut: Ctrl + -");
        addActionListener(e -> controller.zoomOut());
    }
}
