package nl.rug.oop.rts.components;

import nl.rug.oop.rts.graph.Edge;
import nl.rug.oop.rts.graph.GraphManager;
import nl.rug.oop.rts.graph.Node;
import nl.rug.oop.rts.graph.NodeSelector;
import nl.rug.oop.rts.observable.Observer;
import nl.rug.oop.rts.util.TextureLoader;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel implements Observer {
    GraphManager graphManager;
    private final Image backgroundImage;
    private final Image nodeImage;
    private final Image nodeImageSelected;

    public Panel(GraphManager graphManager) {
        super();
        TextureLoader textureLoader = TextureLoader.getInstance();
        backgroundImage = textureLoader.getTexture("mapTexture", 800, 600);
        nodeImage = textureLoader.getTexture("node4", 50, 50);
        nodeImageSelected = textureLoader.getTexture("node3", 50, 50);

        this.graphManager = graphManager;
        NodeSelector nodeSelector = new NodeSelector(graphManager);
        addMouseListener(nodeSelector);
        addMouseMotionListener(nodeSelector);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        for (Edge edge : graphManager.getEdges()) {
            ((Graphics2D) g).setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{10, 10}, 0));
            if (edge.isSelected()) {
                g.setColor(Color.RED);
            } else {
                g.setColor(new Color(161, 100, 21));
            }
            g.drawLine(edge.getStartNode().getX()+25, edge.getStartNode().getY()+25, edge.getEndNode().getX()+25, edge.getEndNode().getY()+25);
            g.setColor(Color.WHITE);
            g.drawString(edge.getName(), (edge.getStartNode().getX() + edge.getEndNode().getX())/2, (edge.getStartNode().getY() + edge.getEndNode().getY())/2);
        }
        for (Node node : graphManager.getNodes()) {
            if(node.isSelected()) {
                g.drawImage(nodeImageSelected, node.getX(), node.getY(), this);
            } else {
                g.drawImage(nodeImage, node.getX(), node.getY(), this);
            }
            g.setColor(Color.WHITE);
            g.drawString(node.getName(), node.getX(), node.getY());
        }
    }

    @Override
    public void update() {
        // Make sure nodes are within the bounds of the panel
        for (Node node : graphManager.getNodes()) {
            if (node.getX() < 0) {
                node.setX(0);
            } else if (node.getX() > getWidth() - 50) {
                node.setX(getWidth() - 50);
            }
            if (node.getY() < 0) {
                node.setY(0);
            } else if (node.getY() > getHeight() - 50) {
                node.setY(getHeight() - 50);
            }
        }
        repaint();
    }
}
