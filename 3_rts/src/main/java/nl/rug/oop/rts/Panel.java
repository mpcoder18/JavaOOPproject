package nl.rug.oop.rts;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    GraphManager graphManager;

    public Panel(GraphManager graphManager) {
        super();
        setBackground(Color.RED);
        this.graphManager = graphManager;
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
            g.setColor(Color.BLACK);
            g.fillRect(node.getX(), node.getY(), 50, 50);
            g.setColor(Color.WHITE);
            g.drawString(node.getName(), node.getX(), node.getY());
        }
    }
}
