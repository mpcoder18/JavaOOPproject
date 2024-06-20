package nl.rug.oop.rts.components.buttons;

import nl.rug.oop.rts.graph.controller.GraphController;

/**
 * Button to add a node to the graph.
 */
public class AddNodeButton extends Button {
    /**
     * Create a new AddNodeButton.
     *
     * @param controller - The graph controller.
     */
    public AddNodeButton(GraphController controller) {
        super("Add Node");
        setToolTipText("Add a new node to the graph\n\nShortcut: Q");
        addActionListener(e -> controller.addNode(controller.getNodes().size() + 1,
                "Node " + (controller.getNodes().size() + 1),
                0, 0));
    }
}
