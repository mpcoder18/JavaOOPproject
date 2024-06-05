package nl.rug.oop.rts;

import nl.rug.oop.rts.components.OptionsPanel;
import nl.rug.oop.rts.components.Panel;
import nl.rug.oop.rts.components.ToolsTopbar;
import nl.rug.oop.rts.graph.GraphManager;

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

        GraphManager graphManager = new GraphManager();

        nl.rug.oop.rts.components.Panel panel = new Panel(graphManager);
        panel.observe(graphManager);
        frame.add(panel);

        JToolBar toolBar = new JToolBar();
        ToolsTopbar toolsTopbar = new ToolsTopbar(graphManager);
        toolsTopbar.addToToolbar(toolBar);
        frame.add(toolBar, BorderLayout.NORTH);

        OptionsPanel optionsPanel = new OptionsPanel(graphManager);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, optionsPanel, panel);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(200);

        frame.add(splitPane, BorderLayout.CENTER);

        // Resize listener
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                graphManager.modified();
            }
        });

        frame.pack();
        frame.setVisible(true);
    }
}
