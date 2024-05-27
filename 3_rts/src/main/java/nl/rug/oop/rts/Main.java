package nl.rug.oop.rts;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;

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

        Panel panel = new Panel();
        frame.add(panel);

        JToolBar toolBar = new JToolBar();
        JButton addNodeButton = new JButton("Add Node");
        JButton removeNodeButton = new JButton("Remove Node");
        JButton addEdgeButton = new JButton("Add Edge");
        JButton removeEdgeButton = new JButton("Remove Edge");
        toolBar.add(addNodeButton);
        toolBar.add(removeNodeButton);
        toolBar.add(addEdgeButton);
        toolBar.add(removeEdgeButton);
        frame.add(toolBar, BorderLayout.NORTH);

        frame.pack();
        frame.setVisible(true);
    }
}