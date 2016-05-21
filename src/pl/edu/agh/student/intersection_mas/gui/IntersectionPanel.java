package pl.edu.agh.student.intersection_mas.gui;

import pl.edu.agh.student.intersection_mas.agent.Driver;
import pl.edu.agh.student.intersection_mas.intersection.Edge;
import pl.edu.agh.student.intersection_mas.intersection.Intersection;
import pl.edu.agh.student.intersection_mas.intersection.Node;

import javax.swing.*;
import java.awt.*;

/**
 * Created by maciek on 21.05.16.
 */
public class IntersectionPanel extends JPanel {
    private Intersection intersection;
    private Dimension intersectionDimension;

    private static final int NODE_RADIUS = 10;
    private static final int DRIVER_RADIUS = 5;
    private static final int MARGIN = 20;

    private static final Color NODE_COLOR = Color.GRAY;
    private static final Color EDGE_COLOR = Color.GRAY;
    private static final Color DRIVER_COLOR = Color.BLUE;

    public IntersectionPanel(Intersection intersection) {
        this.intersection = intersection;
        this.intersectionDimension = intersection.getDimension();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawIntersection((Graphics2D) g);
    }

    private void drawIntersection(Graphics2D g) {
        int nodeX, nodeY, endNodeX, endNodeY, scaledNodeX, scaledNodeY, driverX, driverY;
        float normalizedDriverPosition;
        Node endNode;

        g.scale(1, -1);
        g.translate(0, -this.getHeight());

        for (Node node : this.intersection.getNodes()) {
            nodeX = node.getX();
            nodeY = node.getY();
            scaledNodeX = scaledX(nodeX);
            scaledNodeY = scaledY(nodeY);

            g.setColor(NODE_COLOR);
            g.fillOval(scaledNodeX - NODE_RADIUS, scaledNodeY - NODE_RADIUS, 2 * NODE_RADIUS, 2 * NODE_RADIUS);

            for (Edge edge : node.getOutgoingEdges()) {
                endNode = edge.getEnd();
                endNodeX = endNode.getX();
                endNodeY = endNode.getY();

                g.setColor(EDGE_COLOR);
                g.drawLine(scaledNodeX, scaledNodeY, scaledX(endNodeX), scaledY(endNodeY));

                g.setColor(DRIVER_COLOR);
                for (Driver driver : edge.getDrivers()) {
                    normalizedDriverPosition = (float) driver.getPosition().getPosition() / edge.getLength();

                    driverX = (int) (nodeX + (endNodeX - nodeX) * normalizedDriverPosition);
                    driverY = (int) (nodeY + (endNodeY - nodeY) * normalizedDriverPosition);

                    g.fillOval(scaledX(driverX) - DRIVER_RADIUS, scaledY(driverY) - DRIVER_RADIUS, 2 * DRIVER_RADIUS, 2 * DRIVER_RADIUS);
                }
            }

        }
    }

    private int scaledX(int x) {
        return (int) ((float) x / this.intersectionDimension.width * (this.getWidth() - 2 * NODE_RADIUS - 2 * MARGIN)) + NODE_RADIUS + MARGIN;
    }

    private int scaledY(int y) {
        return (int) ((float) y / this.intersectionDimension.height * (this.getHeight() - 2 * NODE_RADIUS - 2 * MARGIN)) + NODE_RADIUS + MARGIN;
    }

}
