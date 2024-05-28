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
        Node node1 = new Node(1, "Node 1", 100, 100);
        Node node2 = new Node(2, "Node 2", 200, 200);
        Node node3 = new Node(3, "Node 3", 100, 300);
        Edge edge1 = new Edge(1, "Edge 1", node1, node2);
        Edge edge2 = new Edge(2, "Edge 2", node2, node3);
        Edge edge3 = new Edge(3, "Edge 3", node3, node1);
        graphManager.addNode(node1);
        graphManager.addNode(node2);
        graphManager.addNode(node3);
        graphManager.addEdge(edge1);
        graphManager.addEdge(edge2);
        graphManager.addEdge(edge3);

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
        ButtonObserver buttonObserver = new ButtonObserver(graphManager, addNodeButton, removeNodeButton, addEdgeButton, removeEdgeButton);
        graphManager.addObserver(buttonObserver);
        graphManager.notifyObservers();
        frame.add(toolBar, BorderLayout.NORTH);

        addNodeButton.addActionListener(e -> {
            Node node = new Node(graphManager.getNodes().size() + 1, "Node " + (graphManager.getNodes().size() + 1), 0, 0);
            graphManager.addNode(node);
            graphManager.notifyObservers();
        });

        removeNodeButton.addActionListener(e -> {
            if (graphManager.getSelectedNode() != null) {
                graphManager.removeNode(graphManager.getSelectedNode());
                graphManager.selectedNode = null;
            }
            graphManager.notifyObservers();
        });

        addEdgeButton.addActionListener(e -> {
            if (graphManager.getSelectedNode() != null) {
                if (graphManager.startNode == null) {
                    graphManager.startNode = graphManager.getSelectedNode();
                }
            }
            graphManager.notifyObservers();
        });

        removeEdgeButton.addActionListener(e -> {
            if (graphManager.getSelectedEdge() != null) {
                graphManager.removeEdge(graphManager.getSelectedEdge());
                graphManager.selectedEdge = null;
            }
            graphManager.notifyObservers();
        });

        JPanel optionsPanel = new JPanel();
        JLabel messageLabel = new JLabel("Nothing selected");
        optionsPanel.add(messageLabel);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panel, optionsPanel);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(600);

        frame.add(splitPane, BorderLayout.CENTER);

        graphManager.addObserver(new Observer() {
            @Override
            public void update() {
                optionsPanel.removeAll();
                if (graphManager.getSelectedNode() != null) {
                    JLabel nameLabel = new JLabel("Name:");
                    JTextField nameField = new JTextField(graphManager.getSelectedNode().getName());
                    nameField.addActionListener(e -> {
                        graphManager.getSelectedNode().setName(nameField.getText());
                        graphManager.notifyObservers();
                    });
                    optionsPanel.add(nameLabel);
                    optionsPanel.add(nameField);
                } else if (graphManager.getSelectedEdge() != null) {
                    JLabel nameLabel = new JLabel("Name:");
                    JTextField nameField = new JTextField(graphManager.getSelectedEdge().getName());
                    nameField.addActionListener(e -> {
                        graphManager.getSelectedEdge().setName(nameField.getText());
                        graphManager.notifyObservers();
                    });
                    optionsPanel.add(nameLabel);
                    optionsPanel.add(nameField);
                } else {
                    optionsPanel.add(messageLabel);
                }
                optionsPanel.revalidate();
                optionsPanel.repaint();
            }
        });


        frame.pack();
        frame.setVisible(true);
    }
}