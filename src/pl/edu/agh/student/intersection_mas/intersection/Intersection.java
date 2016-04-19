package pl.edu.agh.student.intersection_mas.intersection;

import pl.edu.agh.student.intersection_mas.agent.Driver;

import java.util.Set;

/**
 * Created by bzurkowski on 19.04.16.
 */
public class Intersection {
    private Set<Node> inputNodes;

    private Set<Node> outputNodes;

    private IntersectionState intersectionState;

    public Intersection(Set<Node> inputNodes, Set<Node> outputNodes) {
        this.inputNodes = inputNodes;
        this.outputNodes = outputNodes;

        this.intersectionState = new IntersectionState();
    }

    public Set<Node> getInputNodes() {
        return inputNodes;
    }

    public Set<Node> getOutputNodes() {
        return outputNodes;
    }

    public void setInputNodes(Set<Node> inputNodes) {
        this.inputNodes = inputNodes;
    }

    public IntersectionState getIntersectionState() {
        return intersectionState;
    }

    @Override
    public String toString() {
        return "Intersection{" +
                "inputNodes=" + inputNodes +
                '}';
    }
}
