package nl.rug.oop.rts.graph.view;

import nl.rug.oop.rts.graph.Edge;
import nl.rug.oop.rts.graph.Node;
import nl.rug.oop.rts.graph.controller.GraphController;
import nl.rug.oop.rts.objects.Army;
import nl.rug.oop.rts.objects.Team;
import nl.rug.oop.rts.observable.Observer;
import nl.rug.oop.rts.util.TextureLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * View for the graph.
 */
public class GraphView extends JPanel implements Observer {
    private final GraphController controller;
    private final Image backgroundImage;
    private final Image nodeImage;
    private final Image nodeImageSelected;

    /**
     * Constructor for the graph view.
     *
     * @param controller Graph controller
     */
    public GraphView(GraphController controller) {
        this.controller = controller;

        TextureLoader textureLoader = TextureLoader.getInstance();
        backgroundImage = textureLoader.getTexture("mapTexture", 800, 600);
        nodeImage = textureLoader.getTexture("node4", controller.getNodeSize(), controller.getNodeSize());
        nodeImageSelected = textureLoader.getTexture("node3", controller.getNodeSize(), controller.getNodeSize());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                controller.handleMousePressed(e.getX(), e.getY());
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                controller.handleMouseMoved(e.getX(), e.getY());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                controller.handleMouseDragged(e.getX(), e.getY());
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        drawEdges(g);
        drawNodes(g);
    }

    private void drawNodes(Graphics g) {
        for (Node node : controller.getNodes()) {
            drawNode(g, node);
            drawArmy(g, node);
        }
    }

    private void drawEdges(Graphics g) {
        ((Graphics2D) g).setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                10.0f, new float[]{10, 10}, 0));
        g.setFont(new Font("Dialog", Font.PLAIN, 15));

        for (Edge edge : controller.getEdges()) {
            drawEdge(g, edge);
            drawArmyEdge(g, edge);
        }

        drawEdgePreview(g, getMousePosition());
    }

    private void drawBackground(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    private void drawNode(Graphics g, Node node) {
        if (node.isSelected()) {
            g.drawImage(nodeImageSelected, node.getX(), node.getY(), this);
        } else {
            g.drawImage(nodeImage, node.getX(), node.getY(), this);
        }

        // Calculate the width of the string
        int stringWidth = g.getFontMetrics().stringWidth(node.getName());
        // Drop shadow
        g.setColor(Color.BLACK);
        g.drawString(node.getName(), node.getX() + controller.getNodeSize() / 2 - stringWidth / 2 + 1,
                node.getY() + controller.getNodeSize() / 2 + 1);

        g.setColor(Color.WHITE);
        g.drawString(node.getName(), node.getX() + controller.getNodeSize() / 2 - stringWidth / 2,
                node.getY() + controller.getNodeSize() / 2);
    }

    private void drawEdge(Graphics g, Edge edge) {
        if (edge.isSelected()) {
            g.setColor(Color.RED);
        } else {
            g.setColor(new Color(161, 100, 21));
        }
        g.drawLine(edge.getStartNode().getX() + controller.getNodeSize() / 2,
                edge.getStartNode().getY() + controller.getNodeSize() / 2,
                edge.getEndNode().getX() + controller.getNodeSize() / 2,
                edge.getEndNode().getY() + controller.getNodeSize() / 2);
        // Calculate the middle of the edge
        int x = (edge.getStartNode().getX() + edge.getEndNode().getX()) / 2 + controller.getNodeSize() / 2;
        int y = (edge.getStartNode().getY() + edge.getEndNode().getY()) / 2 + controller.getNodeSize() / 2;
        int stringWidth = g.getFontMetrics().stringWidth(edge.getName());

        // Drop shadow
        g.setColor(Color.BLACK);
        g.drawString(edge.getName(), x - stringWidth / 2 + 1, y + 1);

        g.setColor(Color.WHITE);
        g.drawString(edge.getName(), x - stringWidth / 2, y);
    }

    /**
     * Draw the armies on the node.
     *
     * @param g    Graphics object
     * @param node Node to draw the armies on
     */
    public void drawArmy(Graphics g, Node node) {
        int radius = controller.getNodeSize() / 2;
        int centerX = node.getX() + radius;
        int centerY = node.getY() + radius;

        java.util.List<Army> teamA = new ArrayList<>();
        java.util.List<Army> teamB = new ArrayList<>();

        for (Army army : node.getArmies()) {
            if (army.getFaction().getTeam() == Team.TEAM_A) {
                teamA.add(army);
            } else {
                teamB.add(army);
            }
        }

        // Draw Team A on the left side
        for (int i = 0; i < teamA.size(); i++) {
            g.setColor(teamA.get(i).getFaction().getColor());
            double angle = Math.PI / 2 + Math.PI * (double) i / teamA.size();
            int x = centerX + (int) (radius * Math.cos(angle));
            int y = centerY + (int) (radius * Math.sin(angle));
            g.fillOval(x - 10, y - 10, 20, 20); // Adjusted to center the circle on the node
        }

        // Draw Team B on the right side
        for (int i = 0; i < teamB.size(); i++) {
            g.setColor(teamB.get(i).getFaction().getColor());
            double angle = 3 * Math.PI / 2 + Math.PI * (double) i / teamB.size();
            int x = centerX + (int) (radius * Math.cos(angle));
            int y = centerY + (int) (radius * Math.sin(angle));
            g.fillOval(x - 10, y - 10, 20, 20); // Adjusted to center the circle on the node
        }
    }

    private void drawArmyEdge(Graphics g, Edge edge) {
        int radius = controller.getNodeSize() / 2;
        int centerX = (edge.getStartNode().getX() + edge.getEndNode().getX()) / 2 + radius;
        int centerY = (edge.getStartNode().getY() + edge.getEndNode().getY()) / 2 + radius;

        java.util.List<Army> teamA = new ArrayList<>();
        List<Army> teamB = new ArrayList<>();

        for (Army army : edge.getArmies()) {
            if (army.getFaction().getTeam() == Team.TEAM_A) {
                teamA.add(army);
            } else {
                teamB.add(army);
            }
        }

        // Draw Team A on the left side
        for (int i = 0; i < teamA.size(); i++) {
            g.setColor(teamA.get(i).getFaction().getColor());
            double angle = Math.PI / 2 + Math.PI * (double) i / teamA.size();
            int x = centerX + (int) (radius * Math.cos(angle));
            int y = centerY + (int) (radius * Math.sin(angle));
            g.fillOval(x - 10, y - 10, 20, 20); // Adjusted to center the circle on the node
        }

        // Draw Team B on the right side
        for (int i = 0; i < teamB.size(); i++) {
            g.setColor(teamB.get(i).getFaction().getColor());
            double angle = 3 * Math.PI / 2 + Math.PI * (double) i / teamB.size();
            int x = centerX + (int) (radius * Math.cos(angle));
            int y = centerY + (int) (radius * Math.sin(angle));
            g.fillOval(x - 10, y - 10, 20, 20); // Adjusted to center the circle on the node
        }
    }

    /**
     * Draw the preview of the edge.
     *
     * @param g            Graphics object
     * @param mousePosition Position of the mouse
     */
    public void drawEdgePreview(Graphics g, Point mousePosition) {
        ((Graphics2D) g).setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                10.0f, new float[]{10, 10}, 0));
        g.setFont(new Font("Dialog", Font.PLAIN, 15));

        if (controller.getStartNode() != null) {
            g.setColor(Color.GRAY);
            g.drawLine(controller.getStartNode().getX() + controller.getNodeSize() / 2,
                    controller.getStartNode().getY() + controller.getNodeSize() / 2,
                    mousePosition.x, mousePosition.y);
        }
    }

    @Override
    public void update() {
        controller.checkBounds(getWidth(), getHeight());
        repaint();
    }
}
