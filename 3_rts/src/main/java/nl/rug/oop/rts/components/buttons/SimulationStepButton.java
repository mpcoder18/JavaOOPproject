package nl.rug.oop.rts.components.buttons;

import nl.rug.oop.rts.graph.controller.GraphController;

/**
 * Button to step through the simulation.
 */
public class SimulationStepButton extends Button {
    /**
     * Create a new SimulationStepButton.
     *
     * @param controller - The graph controller.
     */
    public SimulationStepButton(GraphController controller) {
        super("▶ (Step 0)");
        setToolTipText("Step through the simulation");
        addActionListener(e -> controller.stepSimulation());
    }
}
