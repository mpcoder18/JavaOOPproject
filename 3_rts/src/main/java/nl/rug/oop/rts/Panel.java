package nl.rug.oop.rts;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel implements Observer {
    GraphManager graphManager;

    public Panel(GraphManager graphManager) {
        super();
        setBackground(Color.RED);
        this.graphManager = graphManager;
        NodeSelector nodeSelector = new NodeSelector(graphManager);
        addMouseListener(nodeSelector);
        addMouseMotionListener(nodeSelector);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Edge edge : graphManager.getEdges()) {
            g.setColor(Color.BLACK);
            g.drawLine(edge.getStartNode().getX()+25, edge.getStartNode().getY()+25, edge.getEndNode().getX()+25, edge.getEndNode().getY()+25);
            g.setColor(Color.WHITE);
            g.drawString(edge.getName(), (edge.getStartNode().getX() + edge.getEndNode().getX())/2, (edge.getStartNode().getY() + edge.getEndNode().getY())/2);
        }
        for (Node node : graphManager.getNodes()) {
            if(node.isSelected()) {
                g.setColor(Color.BLUE);
                g.fillRect(node.getX(), node.getY(), 50, 50);
            } else {
                g.setColor(Color.BLACK);
                g.fillRect(node.getX(), node.getY(), 50, 50);
            }
            g.setColor(Color.WHITE);
            g.drawString(node.getName(), node.getX(), node.getY());
        }
    }

    @Override
    public void update() {
        repaint();
    }
}
