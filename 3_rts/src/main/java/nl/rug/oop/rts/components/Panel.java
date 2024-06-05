package nl.rug.oop.rts.components;

import nl.rug.oop.rts.graph.Edge;
import nl.rug.oop.rts.graph.GraphManager;
import nl.rug.oop.rts.graph.Node;
import nl.rug.oop.rts.graph.NodeSelector;
import nl.rug.oop.rts.observable.Observer;
import nl.rug.oop.rts.util.TextureLoader;

import javax.swing.*;
import java.awt.*;

/**
 * Panel for the game. View of the MVC pattern.
 */
public class Panel extends JPanel implements Observer {
    private final GraphManager graphManager;
    private final Image backgroundImage;
    private final Image nodeImage;
    private final Image nodeImageSelected;

    /**
     * Create a new panel.
     *
     * @param graphManager GraphManager to observe
     */
    public Panel(GraphManager graphManager) {
        this.graphManager = graphManager;

        TextureLoader textureLoader = TextureLoader.getInstance();
        backgroundImage = textureLoader.getTexture("mapTexture", 800, 600);
        nodeImage = textureLoader.getTexture("node4", graphManager.getNodeSize(), graphManager.getNodeSize());
        nodeImageSelected = textureLoader.getTexture("node3", graphManager.getNodeSize(), graphManager.getNodeSize());

        NodeSelector nodeSelector = new NodeSelector(graphManager);
        addMouseListener(nodeSelector);
        addMouseMotionListener(nodeSelector);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        drawEdges(g);
        drawNodes(g);
    }

    private void drawBackground(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    private void drawEdges(Graphics g) {
        ((Graphics2D) g).setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                10.0f, new float[]{10, 10}, 0));
        // Set font size
        g.setFont(new Font("Dialog", Font.PLAIN, 15));

        for (Edge edge : graphManager.getEdges()) {
            if (edge.isSelected()) {
                g.setColor(Color.RED);
            } else {
                g.setColor(new Color(161, 100, 21));
            }
            g.drawLine(edge.getStartNode().getX() + graphManager.getNodeSize() / 2,
                    edge.getStartNode().getY() + graphManager.getNodeSize() / 2,
                    edge.getEndNode().getX() + graphManager.getNodeSize() / 2,
                    edge.getEndNode().getY() + graphManager.getNodeSize() / 2);
            // Calculate the middle of the edge
            int x = (edge.getStartNode().getX() + edge.getEndNode().getX()) / 2 + graphManager.getNodeSize() / 2;
            int y = (edge.getStartNode().getY() + edge.getEndNode().getY()) / 2 + graphManager.getNodeSize() / 2;
            int stringWidth = g.getFontMetrics().stringWidth(edge.getName());

            // Drop shadow
            g.setColor(Color.BLACK);
            g.drawString(edge.getName(), x - stringWidth / 2 + 2, y + 2);

            g.setColor(Color.WHITE);
            g.drawString(edge.getName(), x - stringWidth / 2, y);
        }

        // Preview edge
        NodeSelector nodeSelector = (NodeSelector) getMouseListeners()[0];
        if (graphManager.getStartNode() != null && nodeSelector.getCurrentMousePosition() != null) {
            g.setColor(Color.GRAY);
            g.drawLine(graphManager.getStartNode().getX() + graphManager.getNodeSize() / 2,
                    graphManager.getStartNode().getY() + graphManager.getNodeSize() / 2,
                    nodeSelector.getCurrentMousePosition().x,
                    nodeSelector.getCurrentMousePosition().y);
        }
    }

    private void drawNodes(Graphics g) {
        for (Node node : graphManager.getNodes()) {
            if (node.isSelected()) {
                g.drawImage(nodeImageSelected, node.getX(), node.getY(), this);
            } else {
                g.drawImage(nodeImage, node.getX(), node.getY(), this);
            }

            // Calculate the width of the string
            int stringWidth = g.getFontMetrics().stringWidth(node.getName());
            // Drop shadow
            g.setColor(Color.BLACK);
            g.drawString(node.getName(), node.getX() + graphManager.getNodeSize() / 2 - stringWidth / 2 + 2,
                    node.getY() + graphManager.getNodeSize() / 2 + 2);

            g.setColor(Color.WHITE);
            g.drawString(node.getName(), node.getX() + graphManager.getNodeSize() / 2 - stringWidth / 2,
                    node.getY() + graphManager.getNodeSize() / 2);
        }
    }

    @Override
    public void update() {
        // Make sure nodes are within the bounds of the panel
        for (Node node : graphManager.getNodes()) {
            if (node.getX() < 0) {
                node.setX(0);
            } else if (node.getX() > getWidth() - graphManager.getNodeSize()) {
                node.setX(getWidth() - graphManager.getNodeSize());
            }
            if (node.getY() < 0) {
                node.setY(0);
            } else if (node.getY() > getHeight() - graphManager.getNodeSize()) {
                node.setY(getHeight() - graphManager.getNodeSize());
            }
        }
        repaint();
    }
}
