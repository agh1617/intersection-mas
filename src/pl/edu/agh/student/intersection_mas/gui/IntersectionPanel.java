package pl.edu.agh.student.intersection_mas.gui;

import pl.edu.agh.student.intersection_mas.agent.Driver;
import pl.edu.agh.student.intersection_mas.intersection.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by maciek on 21.05.16.
 */
public class IntersectionPanel extends JPanel {
    private static final int NODE_RADIUS = 10;
    private static final int DRIVER_RADIUS = 5;
    private static final float MARGIN = 0.1f;

    private static final Color NODE_COLOR = Color.GRAY;
    private static final Color EDGE_COLOR = Color.GRAY;
    private static final Color DRIVER_COLOR = Color.BLUE;

    private Intersection intersection;

    private Dimension intersectionDimension;

    private float scalingFactor;

    private static final Map<TrafficLightState, Color> trafficLightColors = new HashMap<TrafficLightState, Color>() {{
        put(TrafficLightState.GREEN, Color.GREEN);
        put(TrafficLightState.YELLOW, Color.YELLOW);
        put(TrafficLightState.RED, Color.RED);
    }};

    public IntersectionPanel(Intersection intersection) {
        this.intersection = intersection;
        this.intersectionDimension = intersection.getDimension();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        updateScalingFactor();
        drawIntersection((Graphics2D) g);
    }

    private void drawIntersection(Graphics2D g) {
        int nodeX, nodeY, endNodeX, endNodeY, scaledNodeX, scaledNodeY, driverX, driverY, edgeEndX, edgeEndY;
        float edgeLength;
        int driverStartX, driverStartY, driverEndX, driverEndY;
        float edgeDirX, edgeDirY;
        float normalizedDriverPosition;
        int driverLength;
        Node endNode;
        TrafficLight trafficLight;

        g.scale(1, -1);
        g.translate(0, -this.getHeight());

        Stroke edgeStroke = new BasicStroke(1);
        Stroke driverStroke = new BasicStroke(5);

        for (Node node : this.intersection.getNodes()) {
            nodeX = node.getX();
            nodeY = node.getY();
            scaledNodeX = scaledX(nodeX);
            scaledNodeY = scaledY(nodeY);

            g.setColor(NODE_COLOR);
            g.fillRect(scaledNodeX - NODE_RADIUS, scaledNodeY - NODE_RADIUS, 2 * NODE_RADIUS, 2 * NODE_RADIUS);

            for (Edge edge : node.getOutgoingEdges()) {
                g.setStroke(edgeStroke);

                endNode = edge.getEnd();
                endNodeX = endNode.getX();
                endNodeY = endNode.getY();

                edgeLength = edge.getLength();

                edgeDirX = (float) (endNodeX - nodeX) / edgeLength;
                edgeDirY = (float) (endNodeY - nodeY) / edgeLength;

                g.setColor(EDGE_COLOR);
                g.drawLine(scaledNodeX, scaledNodeY, scaledX(endNodeX), scaledY(endNodeY));

                edgeEndX = (int) (endNodeX - (float) (endNodeX - nodeX) / edge.getLength()  * NODE_RADIUS);
                edgeEndY = (int) (endNodeY - (float) (endNodeY - nodeY) / edge.getLength()  * NODE_RADIUS);

                trafficLight = endNode.getTrafficLight(edge);

                if (trafficLight != null) {
                    g.setColor(trafficLightColors.get(trafficLight.getState()));
                }

                g.fillOval(scaledX(edgeEndX) - NODE_RADIUS, scaledY(edgeEndY) - NODE_RADIUS, 2 * NODE_RADIUS, 2 * NODE_RADIUS);

                g.setColor(DRIVER_COLOR);
                for (Driver driver : edge.getDrivers()) {
                    normalizedDriverPosition = (float) driver.getPosition().getPosition() / edge.getLength();

                    driverX = (int) (nodeX + (endNodeX - nodeX) * normalizedDriverPosition);
                    driverY = (int) (nodeY + (endNodeY - nodeY) * normalizedDriverPosition);

                    driverLength = driver.getLength();

                    driverStartX = (int) (driverX - edgeDirX * (float) driverLength / 2);
                    driverStartY = (int) (driverY - edgeDirY * (float) driverLength / 2);

                    driverEndX = (int) (driverX + edgeDirX * (float) driverLength / 2);
                    driverEndY = (int) (driverY + edgeDirY * (float) driverLength / 2);

                    g.setStroke(driverStroke);
                    g.drawLine(scaledX(driverStartX), scaledY(driverStartY), scaledX(driverEndX), scaledY(driverEndY));
                }
            }

        }
    }

    private int scaledX(int x) {
        return (int) (x * scalingFactor + MARGIN * getWidth());
    }

    private int scaledY(int y) {
        return (int) (y * scalingFactor + MARGIN * getHeight());
    }

    private void updateScalingFactor() {
        float xFactor = (1 - 2 * MARGIN) / intersectionDimension.width * getWidth();
        float yFactor = (1 - 2 * MARGIN) / intersectionDimension.height * getHeight();

        this.scalingFactor = Math.min(xFactor, yFactor);
    }
}
