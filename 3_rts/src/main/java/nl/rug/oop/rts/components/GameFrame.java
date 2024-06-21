package nl.rug.oop.rts.components;

import nl.rug.oop.rts.graph.controller.GraphController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

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
        setLocationRelativeTo(null);
        add(graphController.getView());

        JToolBar toolBar = new JToolBar();
        ToolsTopbar toolsTopbar = new ToolsTopbar(graphController);
        toolsTopbar.addToToolbar(toolBar);
        add(toolBar, BorderLayout.NORTH);

        OptionsPanel optionsPanel = new OptionsPanel(graphController);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, optionsPanel, graphController.getView());
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(200);
        splitPane.setContinuousLayout(true);
        add(splitPane, BorderLayout.CENTER);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                graphController.getModel().notifyAllObservers();
            }
        });

        pack();
        setVisible(true);
    }
}
