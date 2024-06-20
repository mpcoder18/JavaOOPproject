package nl.rug.oop.rts.components.buttons;

import nl.rug.oop.rts.graph.Node;
import nl.rug.oop.rts.graph.controller.GraphController;

/**
 * Button to add an edge to the graph.
 */
public class AddEdgeButton extends Button {
    /**
     * Create a new AddEdgeButton.
     *
     * @param controller - The graph controller.
     */
    public AddEdgeButton(GraphController controller) {
        super("Add Edge");
        setToolTipText("Select a node to start the edge from\n\nShortcut: E");
        addActionListener(e -> {
            if (controller.getSelected() instanceof Node) {
                if (controller.getStartNode() == null) {
                    controller.setStartNode((Node) controller.getSelected());
                }
            }
        });
    }
}
