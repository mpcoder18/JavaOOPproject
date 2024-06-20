package nl.rug.oop.rts.components.buttons;

import nl.rug.oop.rts.graph.controller.GraphController;

/**
 * Button to redo the last action.
 */
public class RedoButton extends Button {
    /**
     * Create a new RedoButton.
     *
     * @param controller - The graph controller.
     */
    public RedoButton(GraphController controller) {
        super("↪");
        setToolTipText("Redo the last action\n\nShortcut: Ctrl + Y");
        addActionListener(e -> controller.redo());
    }
}
