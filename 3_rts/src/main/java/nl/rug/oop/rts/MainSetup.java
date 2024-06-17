package nl.rug.oop.rts;

import nl.rug.oop.rts.components.OptionsPanel;
import nl.rug.oop.rts.components.ToolsTopbar;
import nl.rug.oop.rts.graph.controller.GraphController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Main class of the application.
 */
public class MainSetup {
    /**
     * Main method of the application.
     */
    public MainSetup() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("RTS");
        frame.setSize(800, 600);
        frame.setPreferredSize(frame.getSize());
        frame.setLocationRelativeTo(null);

        GraphController graphController = new GraphController();
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

        frame.pack();
        frame.setVisible(true);
    }
}
