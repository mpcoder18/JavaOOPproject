package nl.rug.oop.rts.components.buttons;

import nl.rug.oop.rts.graph.controller.GraphController;

import javax.swing.*;

/**
 * Button to save the current graph.
 */
public class SaveButton extends Button {
    /**
     * Create a new SaveButton.
     *
     * @param controller - The graph controller.
     */
    public SaveButton(GraphController controller) {
        super("Save");
        setToolTipText("Save the current graph\n\nShortcut: Ctrl + S");
        addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            controller.getSaveManager().saveGameChooser(controller.getModel(), parentFrame);
        });
    }
}
