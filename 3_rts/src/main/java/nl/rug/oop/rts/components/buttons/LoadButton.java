package nl.rug.oop.rts.components.buttons;

import nl.rug.oop.rts.graph.controller.GraphController;
import nl.rug.oop.rts.graph.model.GraphModel;

import javax.swing.*;

/**
 * Button to load a saved graph.
 */
public class LoadButton extends Button {
    /**
     * Create a new LoadButton.
     *
     * @param controller - The graph controller.
     */
    public LoadButton(GraphController controller) {
        super("Load");
        setToolTipText("Load a saved graph\n\nShortcut: Ctrl + O");
        addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            GraphModel model = controller.getSaveManager().loadGameChooser(parentFrame);
            if (model != null) {
                controller.replaceModel(model);
            }
        });
    }
}
