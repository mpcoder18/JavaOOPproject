package nl.rug.oop.rts;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Main class of the application. Add more details here.
 */
public class Main {

    /**
     * Main function. Add more details here.
     *
     * @param args Commandline arguments.
     */
    public static void main(String[] args) {
        FlatDarculaLaf.setup(); // Dark mode

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("RTS");
        frame.setSize(800, 600);
        frame.setPreferredSize(frame.getSize());
        frame.setLocationRelativeTo(null);

        GraphManager graphManager = new GraphManager();

        Panel panel = new Panel(graphManager);
        graphManager.addObserver(panel);
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
                graphManager.notifyObservers();
            }
        });

        frame.pack();
        frame.setVisible(true);
    }
}