package nl.rug.oop.rts.graph.view;

import nl.rug.oop.rts.KeyHandler;
import nl.rug.oop.rts.MouseHandler;
import nl.rug.oop.rts.components.OptionsPanel;
import nl.rug.oop.rts.components.ToolsTopbar;
import nl.rug.oop.rts.graph.Edge;
import nl.rug.oop.rts.graph.Node;
import nl.rug.oop.rts.graph.Selectable;
import nl.rug.oop.rts.graph.controller.GraphController;
import nl.rug.oop.rts.objects.Army;
import nl.rug.oop.rts.objects.Team;
import nl.rug.oop.rts.observable.Observer;
import nl.rug.oop.rts.util.TextureLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * View for the graph.
 */
public class GraphView extends JPanel implements Observer {
    private final GraphController controller;
    private Image backgroundImage;
    private Image nodeImage;
    private Image nodeImageSelected;
    private JPanel graphPanel;

    /**
     * Constructor for the graph view.
     *
     * @param controller Graph controller
     */
    public GraphView(GraphController controller) {
        this.controller = controller;

        setupComponents();
        setupImages();
        setupHandlers();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                update();
            }
        });
    }

    private void setupComponents() {
        ToolsTopbar toolsTopbar = new ToolsTopbar(controller);
        JToolBar toolBar = new JToolBar();
        toolsTopbar.addToToolbar(toolBar);
        OptionsPanel optionsPanel = new OptionsPanel(controller);

        this.graphPanel = createGraphPanel();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, optionsPanel, graphPanel);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(200);
        splitPane.setContinuousLayout(true);

        this.setLayout(new BorderLayout());
        this.add(toolBar, BorderLayout.NORTH);
        this.add(splitPane, BorderLayout.CENTER);
    }

    private JPanel createGraphPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBackground(g);
                drawEdges(g);
                drawNodes(g);
            }
        };
    }

    private void setupImages() {
        TextureLoader textureLoader = TextureLoader.getInstance();
        backgroundImage = textureLoader.getTexture("mapTexture", 800, 600);
        nodeImage = textureLoader.getTexture("node4", controller.getNodeSize(), controller.getNodeSize());
        nodeImageSelected = textureLoader.getTexture("node3", controller.getNodeSize(), controller.getNodeSize());
    }

    private void setupHandlers() {
        MouseHandler mouseHandler = new MouseHandler(controller);
        graphPanel.addMouseListener(mouseHandler.getMouseAdapter());
        graphPanel.addMouseMotionListener(mouseHandler.getMouseMotionAdapter());

        KeyHandler keyHandler = new KeyHandler(controller);
        keyHandler.setupKeyBindings(this);
    }

    private void drawNodes(Graphics g) {
        for (Node node : controller.getNodes()) {
            drawNode(g, node);
            drawArmy(g, node);
        }
    }

    private void drawEdges(Graphics g) {
        ((Graphics2D) g).setStroke(new BasicStroke((float) (controller.getNodeSize() * 3) / 80,
                BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, (float) (controller.getNodeSize() * 10) / 80,
                new float[]{(float) (controller.getNodeSize() * 10) / 80,
                            (float) (controller.getNodeSize() * 10) / 80}, 0));
        g.setFont(new Font("Dialog", Font.PLAIN, controller.getNodeSize() * 15 / 80));

        for (Edge edge : controller.getEdges()) {
            drawEdge(g, edge);
            drawArmy(g, edge);
        }

        drawEdgePreview(g, graphPanel.getMousePosition());
    }

    private void drawBackground(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, graphPanel.getWidth(), graphPanel.getHeight(), this);
    }

    private void drawNode(Graphics g, Node node) {
        if (node.isSelected()) {
            g.drawImage(nodeImageSelected, node.getX(), node.getY(), controller.getNodeSize(), controller.getNodeSize(),
                    this);
        } else {
            g.drawImage(nodeImage, node.getX(), node.getY(), controller.getNodeSize(), controller.getNodeSize(),
                    this);
        }

        g.setFont(new Font("Dialog", Font.PLAIN, controller.getNodeSize() * 15 / 80));
        // Calculate the width of the string for centering
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
     * Draw the armies on the node or edge.
     *
     * @param g          Graphics object
     * @param selectable Selectable object
     */
    private void drawArmy(Graphics g, Selectable selectable) {
        int radius = controller.getNodeSize() / 2;
        int centerX;
        int centerY;
        if (selectable instanceof Node node) {
            centerX = node.getX() + radius;
            centerY = node.getY() + radius;
        } else if (selectable instanceof Edge edge) {
            centerX = (edge.getStartNode().getX() + edge.getEndNode().getX()) / 2 + radius;
            centerY = (edge.getStartNode().getY() + edge.getEndNode().getY()) / 2 + radius;
        } else {
            return;
        }

        java.util.List<Army> teamA = new ArrayList<>();
        java.util.List<Army> teamB = new ArrayList<>();

        for (Army army : selectable.getArmies()) {
            if (army.getFaction().getTeam() == Team.TEAM_A) {
                teamA.add(army);
            } else {
                teamB.add(army);
            }
        }

        drawTeam(g, teamA, centerX, centerY, radius, 0);
        drawTeam(g, teamB, centerX, centerY, radius, Math.PI);
    }

    private void drawTeam(Graphics g, List<Army> team, int centerX, int centerY, int radius, double startAngle) {
        for (int i = 0; i < team.size(); i++) {
            g.setColor(team.get(i).getFaction().getColor());
            double angle = Math.PI / 2 + Math.PI * (double) i / team.size() + startAngle;
            int x = centerX + (int) (radius * Math.cos(angle));
            int y = centerY + (int) (radius * Math.sin(angle));
            g.fillOval(x - 10, y - 10, 20, 20); // Adjusted to center the circle on the node
        }
    }

    /**
     * Draw the preview of the edge.
     *
     * @param g             Graphics object
     * @param mousePosition Position of the mouse
     */
    public void drawEdgePreview(Graphics g, Point mousePosition) {
        ((Graphics2D) g).setStroke(new BasicStroke((float) (controller.getNodeSize() * 3) / 80,
                BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, (float) (controller.getNodeSize() * 10) / 80,
                new float[]{(float) (controller.getNodeSize() * 10) / 80,
                            (float) (controller.getNodeSize() * 10) / 80}, 0));

        if (controller.getStartNode() != null && mousePosition != null) {
            g.setColor(Color.GRAY);
            g.drawLine(controller.getStartNode().getX() + controller.getNodeSize() / 2,
                    controller.getStartNode().getY() + controller.getNodeSize() / 2,
                    mousePosition.x, mousePosition.y);
        }
    }

    @Override
    public void update() {
        controller.checkBounds(graphPanel.getWidth(), graphPanel.getHeight());
        repaint();
    }
}
