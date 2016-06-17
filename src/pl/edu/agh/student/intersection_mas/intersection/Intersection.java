package pl.edu.agh.student.intersection_mas.intersection;


import pl.edu.agh.student.intersection_mas.agent.TrafficLight;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

/**
 * Created by bzurkowski on 19.04.16.
 */
public class Intersection {
    private Set<Node> nodes;
    private Set<Node> inputNodes;
    private Set<Node> outputNodes;

    private ArrayList<TrafficLight> trafficLights;

    public Intersection(Set<Node> nodes) {
        this.nodes = nodes;

        this.trafficLights = new ArrayList<TrafficLight>();
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    public Set<Node> getInputNodes() {
        return inputNodes;
    }

    public Set<Node> getOutputNodes() {
        return outputNodes;
    }

    public Node getRandomInputNode() {
        return (Node) inputNodes.toArray()[new Random().nextInt(inputNodes.size())];
    }

    public void setInputNodes(Set<Node> inputNodes) {
        this.inputNodes = inputNodes;
    }

    public void setOutputNodes(Set<Node> outputNodes) {
        this.outputNodes = outputNodes;
    }

    public ArrayList<TrafficLight> getTrafficLights() {
        return trafficLights;
    }

    public void addTraficLight(TrafficLight light) {

        this.trafficLights.add(light);
    }

    public Dimension getDimension() {
        float maxX = 0, minX = Float.MAX_VALUE, maxY = 0, minY = Float.MAX_VALUE;
        float x, y;

        for (Node node : nodes) {
            x = node.getX();
            y = node.getY();

            if (x > maxX) maxX = x;
            if (x < minX) minX = x;
            if (y > maxY) maxY = y;
            if (y < minY) minY = y;
        }

        return new Dimension((int) Math.ceil(maxX - minX), (int) Math.ceil(maxY - minY));
    }

    @Override
    public String toString() {
        return "Intersection{" +
                "inputNodes=" + inputNodes +
                '}';
    }
}
