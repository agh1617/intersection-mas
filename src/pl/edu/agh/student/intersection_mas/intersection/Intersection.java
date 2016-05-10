package pl.edu.agh.student.intersection_mas.intersection;


import java.util.Random;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by bzurkowski on 19.04.16.
 */
public class Intersection {
    private Set<Node> inputNodes;

    private Set<Node> outputNodes;

    private IntersectionState intersectionState;

    private ArrayList<TrafficLight> trafficLights;

    public Intersection(Set<Node> inputNodes, Set<Node> outputNodes) {
        this.inputNodes = inputNodes;
        this.outputNodes = outputNodes;

        this.intersectionState = new IntersectionState();
        this.trafficLights = new ArrayList<TrafficLight>();
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

    public IntersectionState getIntersectionState() {
        return intersectionState;
    }

    public ArrayList<TrafficLight> getTrafficLights() {
        return trafficLights;
    }

    public void addTraficLight(TrafficLight light) {

        this.trafficLights.add(light);
    }

    @Override
    public String toString() {
        return "Intersection{" +
                "inputNodes=" + inputNodes +
                '}';
    }
}
