package pl.edu.agh.student.intersection_mas.intersection;

import pl.edu.agh.student.intersection_mas.intersection.Edge;
import pl.edu.agh.student.intersection_mas.intersection.Node;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by bzurkowski on 19.04.16.
 */
public class Route {
    private List<Node> nodes;

    private Node start;

    private Node end;

    public Route(Node start, Node end) {
        this.start = start;
        this.end = end;

        this.nodes = new LinkedList<Node>();

        this.calculate();
    }

    public void calculate() {
        Node currentNode = this.start;
        this.nodes.add(currentNode);

        while (currentNode != null && currentNode != this.end) {
            Iterator<Edge> outgoingEdgeIt = currentNode.getOutgoingEdges().iterator();

            if (outgoingEdgeIt.hasNext()) {
                currentNode = outgoingEdgeIt.next().getEnd();
            } else {
                currentNode = null;
            }

            this.nodes.add(currentNode);
        }
    }

    public List<Node> getNodes() {
        return nodes;
    }
}
