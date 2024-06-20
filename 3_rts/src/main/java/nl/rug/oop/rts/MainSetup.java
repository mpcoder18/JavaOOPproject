package nl.rug.oop.rts;

import nl.rug.oop.rts.components.GameFrame;
import nl.rug.oop.rts.graph.controller.GraphController;

/**
 * Main class of the application.
 */
public class MainSetup {
    /**
     * Main method of the application.
     */
    public MainSetup() {
        new GameFrame(new GraphController());
    }
}
