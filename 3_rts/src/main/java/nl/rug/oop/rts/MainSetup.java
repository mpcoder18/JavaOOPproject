package nl.rug.oop.rts;

import nl.rug.oop.rts.components.OptionsPanel;
import nl.rug.oop.rts.components.ToolsTopbar;
import nl.rug.oop.rts.graph.controller.GraphController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class of the application.
 */
public class MainSetup {
    List<JFrame> frames = new ArrayList<>();
    /**
     * Main method of the application.
     */
    public MainSetup() {
        GraphController graphController = new GraphController(this);
        createComponents(graphController);
    }

    public void createComponents(GraphController graphController) { // TODO: Avoid recreating new windows, instead just replace model
        System.out.println(graphController.getModel().getSimulationStep());
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("RTS");
        frame.setSize(800, 600);
        frame.setPreferredSize(frame.getSize());
        frame.setLocationRelativeTo(null);

        frame.add(graphController.getView());

        JToolBar toolBar = new JToolBar();
        ToolsTopbar toolsTopbar = new ToolsTopbar(graphController);
        toolsTopbar.addToToolbar(toolBar);
        frame.add(toolBar, BorderLayout.NORTH);

        OptionsPanel optionsPanel = new OptionsPanel(graphController);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, optionsPanel, graphController.getView());
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(200);

        frame.add(splitPane, BorderLayout.CENTER);

        // Resize listener
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                graphController.getModel().notifyAllObservers();
            }
        });

        // Close all other frames
        for (JFrame f : frames) {
            f.dispose();
        }
        frames.add(frame);

        frame.pack();
        frame.setVisible(true);
    }
}
