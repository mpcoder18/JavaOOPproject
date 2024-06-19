package nl.rug.oop.rts.components.buttons;

import nl.rug.oop.rts.graph.controller.GraphController;

public class AddNodeButton extends Button {
    public AddNodeButton(GraphController controller) {
        super("Add Node (Q)");
        addActionListener(e -> controller.addNode(controller.getNodes().size() + 1,
                "Node " + (controller.getNodes().size() + 1),
                0, 0));
    }
}
