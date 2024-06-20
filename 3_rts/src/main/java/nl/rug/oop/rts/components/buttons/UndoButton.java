package nl.rug.oop.rts.components.buttons;

import nl.rug.oop.rts.graph.controller.GraphController;

/**
 * Button to undo the last action.
 */
public class UndoButton extends Button {
    /**
     * Create a new UndoButton.
     *
     * @param controller - The graph controller.
     */
    public UndoButton(GraphController controller) {
        super("↩");
        setToolTipText("Undo the last action\n\nShortcut: Ctrl + Z");
        addActionListener(e -> controller.undo());
    }
}
