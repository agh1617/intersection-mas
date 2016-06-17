package pl.edu.agh.student.intersection_mas.gui;

import pl.edu.agh.student.intersection_mas.agent.Driver;
import pl.edu.agh.student.intersection_mas.agent.TrafficLight;
import pl.edu.agh.student.intersection_mas.agent.TrafficLightState;
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

    private static final Color NODE_COLOR = Color.BLACK;
    private static final Color EDGE_COLOR = Color.LIGHT_GRAY;
    private static final Color DRIVER_COLOR = Color.BLUE;

    private static final Stroke DRIVER_STROKE = new BasicStroke(8);
    private static final Stroke EDGE_STROKE = new BasicStroke(10);

    private Intersection intersection;

    private Dimension intersectionDimension;

    private float scalingFactor;

    private static final Map<TrafficLightState, Color> TRAFFIC_LIGHT_COLORS = new HashMap<TrafficLightState, Color>() {{
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

        setScalingFactor();
        drawIntersection((Graphics2D) g);
    }

    private void drawIntersection(Graphics2D g) {
        setPanelOrientation(g);
        drawEdges(g);
        drawNodes(g);
        drawTrafficLights(g);
        drawDrivers(g);
    }

    private void drawNodes(Graphics2D g) {
        for (Node node : intersection.getNodes()) {
            g.setColor(NODE_COLOR);
            g.fillRect(
                    scaledX(node.getX()) - NODE_RADIUS,
                    scaledY(node.getY()) - NODE_RADIUS,
                    2 * NODE_RADIUS,
                    2 * NODE_RADIUS
            );
        }
    }

    private void drawEdges(Graphics2D g) {
        Node endNode;

        g.setStroke(EDGE_STROKE);

        for (Node startNode : intersection.getNodes()) {
            for (Edge edge : startNode.getOutgoingEdges()) {
                endNode = edge.getEnd();

                g.setColor(EDGE_COLOR);
                g.drawLine(
                        scaledX(startNode.getX()),
                        scaledY(startNode.getY()),
                        scaledX(endNode.getX()),
                        scaledY(endNode.getY())
                );
            }
        }
    }

    private void drawTrafficLights(Graphics2D g) {
        Node endNode;

        float edgeLength, edgeRatio;
        int startNodeX, startNodeY, endNodeX, endNodeY;
        int edgeEndX, edgeEndY;

        TrafficLight trafficLight;

        for (Node startNode : intersection.getNodes()) {
            startNodeX = startNode.getX();
            startNodeY = startNode.getY();

            for (Edge edge : startNode.getOutgoingEdges()) {
                endNode = edge.getEnd();

                endNodeX = endNode.getX();
                endNodeY = endNode.getY();

                edgeLength = edge.getLength() * scalingFactor;
                edgeRatio = (edgeLength - 3 * NODE_RADIUS) / edgeLength;

                edgeEndX = (int) (startNodeX + (endNodeX - startNodeX) * edgeRatio);
                edgeEndY = (int) (startNodeY + (endNodeY - startNodeY) * edgeRatio);

                trafficLight = endNode.getTrafficLight(edge);

                g.setColor(NODE_COLOR);
                if (trafficLight != null) {
                    g.setColor((TRAFFIC_LIGHT_COLORS.get(trafficLight.getState())));
                }

                g.fillOval(
                        scaledX(edgeEndX) - NODE_RADIUS,
                        scaledY(edgeEndY) - NODE_RADIUS,
                        2 * NODE_RADIUS,
                        2 * NODE_RADIUS
                );
            }
        }
    }

    private void drawDrivers(Graphics2D g) {
        Node endNode;

        float edgePositionRatio;

        int startNodeX, startNodeY, endNodeX, endNodeY;

        int driverX, driverY, driverStartX, driverStartY, driverEndX, driverEndY;

        float edgeLength, edgeDirX, edgeDirY;

        float driverRadius;

        for (Node startNode : intersection.getNodes()) {
            startNodeX = startNode.getX();
            startNodeY = startNode.getY();

            for (Edge edge : startNode.getOutgoingEdges()) {
                endNode = edge.getEnd();

                endNodeX = endNode.getX();
                endNodeY = endNode.getY();

                edgeLength = edge.getLength();

                edgeDirX = (endNodeX - startNodeX) / edgeLength;
                edgeDirY = (endNodeY - startNodeY) / edgeLength;

                for (Driver driver : edge.getDrivers()) {
                    edgePositionRatio = (float) driver.getPosition().getPosition() / edge.getLength();

                    driverX = (int) (startNodeX + (endNodeX - startNodeX) * edgePositionRatio);
                    driverY = (int) (startNodeY + (endNodeY - startNodeY) * edgePositionRatio);

                    driverRadius = (float) driver.getLength() / 2;

                    driverStartX = (int) (driverX - edgeDirX * driverRadius);
                    driverStartY = (int) (driverY - edgeDirY * driverRadius);

                    driverEndX = (int) (driverX + edgeDirX * driverRadius);
                    driverEndY = (int) (driverY + edgeDirY * driverRadius);

                    g.setStroke(DRIVER_STROKE);
                    g.setColor(driver.getColor());

                    g.drawLine(
                            scaledX(driverStartX),
                            scaledY(driverStartY),
                            scaledX(driverEndX),
                            scaledY(driverEndY)
                    );
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

    private void setScalingFactor() {
        float xFactor = (1 - 2 * MARGIN) / intersectionDimension.width * getWidth();
        float yFactor = (1 - 2 * MARGIN) / intersectionDimension.height * getHeight();

        this.scalingFactor = Math.min(xFactor, yFactor);
    }

    private void setPanelOrientation(Graphics2D g) {
        g.scale(1, -1);
        g.translate(0, -this.getHeight());
    }
}
