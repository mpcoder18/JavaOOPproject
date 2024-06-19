package nl.rug.oop.rts;

import nl.rug.oop.rts.graph.controller.GraphController;

/**
 * Main class of the application.
 */
public class MainSetup {
    /**
     * Main method of the application.
     */
    public MainSetup() {
        GraphController graphController = new GraphController(this);
        new GameFrame(graphController);
    }
}
