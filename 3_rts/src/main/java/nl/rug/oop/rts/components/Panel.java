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
 * Panel for the game.
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
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        ((Graphics2D) g).setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                10.0f, new float[]{10, 10}, 0));
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
            g.setColor(Color.WHITE);
            g.drawString(edge.getName(), (edge.getStartNode().getX() + edge.getEndNode().getX()) / 2,
                    (edge.getStartNode().getY() + edge.getEndNode().getY()) / 2);
        }
        for (Node node : graphManager.getNodes()) {
            if (node.isSelected()) {
                g.drawImage(nodeImageSelected, node.getX(), node.getY(), this);
            } else {
                g.drawImage(nodeImage, node.getX(), node.getY(), this);
            }
            g.setColor(Color.WHITE);
            g.drawString(node.getName(), node.getX(), node.getY());
        }
        NodeSelector nodeSelector = (NodeSelector) getMouseMotionListeners()[0];
        if (graphManager.getStartNode() != null && nodeSelector.getCurrentMousePosition() != null) {
            g.setColor(Color.GRAY);
            g.drawLine(graphManager.getStartNode().getX() + graphManager.getNodeSize() / 2,
                    graphManager.getStartNode().getY() + graphManager.getNodeSize() / 2,
                    nodeSelector.getCurrentMousePosition().x,
                    nodeSelector.getCurrentMousePosition().y);
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
