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
        int nodeX, nodeY, endNodeX, endNodeY, scaledNodeX, scaledNodeY, scaledEndNodeX, scaledEndNodeY, driverX, driverY, lightX, lightY;
        int driverStartX, driverStartY, driverEndX, driverEndY;
        float edgeDirX, edgeDirY;
        float normalizedDriverPosition;
        double edgeLength, lightEdgeRatio;
        int driverLength;
        Node endNode;
        TrafficLight trafficLight;

        g.scale(1, -1);
        g.translate(0, -this.getHeight());

        Stroke edgeStroke = new BasicStroke(1);
        Stroke driverStroke = new BasicStroke(8);

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
                scaledEndNodeX = scaledX(endNodeX);
                scaledEndNodeY = scaledY(endNodeY);

                edgeLength = edge.getLength();
                edgeDirX = (float) ((float) (endNodeX - nodeX) / edgeLength);
                edgeDirY = (float) ((float) (endNodeY - nodeY) / edgeLength);

                g.setColor(EDGE_COLOR);
                g.drawLine(scaledNodeX, scaledNodeY, scaledEndNodeX, scaledEndNodeY);

                // compute traffic light position
                edgeLength = Math.sqrt(Math.pow(scaledEndNodeX - scaledNodeX, 2) + Math.pow(scaledEndNodeY - scaledNodeY, 2));
                lightEdgeRatio = (edgeLength - 3 * NODE_RADIUS) / edgeLength;

                lightX = (int) (nodeX + lightEdgeRatio * (endNodeX - nodeX));
                lightY = (int) (nodeY + lightEdgeRatio * (endNodeY - nodeY));

                // draw traffic light
                trafficLight = endNode.getTrafficLight(edge);
                if (trafficLight != null) g.setColor(trafficLightColors.get(trafficLight.getState()));
                g.fillOval(scaledX(lightX) - NODE_RADIUS, scaledY(lightY) - NODE_RADIUS, 2 * NODE_RADIUS, 2 * NODE_RADIUS);

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
                    g.setColor(driver.getColor());
                    g.drawLine(scaledX(driverStartX), scaledY(driverStartY), scaledX(driverEndX), scaledY(driverEndY));
//                    g.fillOval(scaledX(driverX) - DRIVER_RADIUS, scaledY(driverY) - DRIVER_RADIUS, 2 * DRIVER_RADIUS, 2 * DRIVER_RADIUS);
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
