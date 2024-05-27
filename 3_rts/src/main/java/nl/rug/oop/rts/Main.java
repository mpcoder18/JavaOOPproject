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

        GraphManager graphManager = new GraphManager();
        graphManager.addNode(new Node(1, "Node 1", 100, 100));
        graphManager.addNode(new Node(2, "Node 2", 200, 200));
        graphManager.addNode(new Node(3, "Node 3", 100, 300));
        graphManager.addEdge(new Edge(1, "Edge 1", graphManager.getNodes().get(0), graphManager.getNodes().get(1)));
        graphManager.addEdge(new Edge(2, "Edge 2", graphManager.getNodes().get(1), graphManager.getNodes().get(2)));
        graphManager.addEdge(new Edge(3, "Edge 3", graphManager.getNodes().get(2), graphManager.getNodes().get(0)));

        Panel panel = new Panel(graphManager);
        graphManager.addObserver(panel);
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