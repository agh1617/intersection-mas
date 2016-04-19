package pl.edu.agh.student.intersection_mas.intersection;

import java.util.Set;

/**
 * Created by bzurkowski on 19.04.16.
 */
public class Intersection {
    private Set<Node> inputNodes;

    public Intersection(Set<Node> inputNodes) {
        this.inputNodes = inputNodes;
    }

    public Set<Node> getInputNodes() {
        return inputNodes;
    }

    public void setInputNodes(Set<Node> inputNodes) {
        this.inputNodes = inputNodes;
    }

    @Override
    public String toString() {
        return "Intersection{" +
                "inputNodes=" + inputNodes +
                '}';
    }
}
