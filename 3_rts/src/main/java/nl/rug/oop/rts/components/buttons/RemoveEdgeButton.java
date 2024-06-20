package nl.rug.oop.rts.components.buttons;

import nl.rug.oop.rts.graph.Edge;
import nl.rug.oop.rts.graph.controller.GraphController;

/**
 * Button to remove an edge from the graph.
 */
public class RemoveEdgeButton extends Button {
    /**
     * Create a new RemoveEdgeButton.
     *
     * @param controller - The graph controller.
     */
    public RemoveEdgeButton(GraphController controller) {
        super("Remove Edge");
        setToolTipText("Select an edge to remove\n\nShortcut: Delete");
        addActionListener(e -> {
            if (controller.getSelected() instanceof Edge edge) {
                edge.getStartNode().removeEdge(edge);
                edge.getEndNode().removeEdge(edge);
                controller.deselect();
                controller.removeEdge(edge);
            }
        });
    }
}
