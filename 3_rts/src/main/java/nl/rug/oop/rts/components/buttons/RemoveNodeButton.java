package nl.rug.oop.rts.components.buttons;

import nl.rug.oop.rts.graph.Node;
import nl.rug.oop.rts.graph.controller.GraphController;

/**
 * Button to remove a node from the graph.
 */
public class RemoveNodeButton extends Button {
    /**
     * Create a new RemoveNodeButton.
     *
     * @param controller - The graph controller.
     */
    public RemoveNodeButton(GraphController controller) {
        super("Remove Node");
        setToolTipText("Remove the selected node\n\nShortcut: Delete");
        addActionListener(e -> {
            if (controller.getSelected() instanceof Node) {
                controller.removeNode((Node) controller.getSelected());
                controller.deselect();
            }
        });
    }
}
