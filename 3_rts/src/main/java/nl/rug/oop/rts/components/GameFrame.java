package nl.rug.oop.rts.components;

import nl.rug.oop.rts.graph.controller.GraphController;

import javax.swing.*;
import java.awt.*;

/**
 * Main class of the application.
 */
public class GameFrame extends JFrame {
    /**
     * Create a new GameFrame.
     *
     * @param graphController GraphController to control the graph
     */
    public GameFrame(GraphController graphController) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("RTS");
        setSize(800, 600);
        setPreferredSize(getSize());
        setMinimumSize(new Dimension(750, 300));
        setLocationRelativeTo(null);
        add(graphController.getView());

        pack();
        setVisible(true);
    }
}
