package pl.edu.agh.student.intersection_mas.agent;

import pl.edu.agh.student.intersection_mas.intersection.Edge;
import pl.edu.agh.student.intersection_mas.intersection.Node;

import java.util.*;

public class Route implements Iterator {
    private List<Edge> edges;

    private Iterator<Edge> edgesIt;

    private Node startNode;

    public Route(Node startNode) {
        this.startNode = startNode;
        this.edges = calculateEdges();
        this.edgesIt = edges.iterator();
    }

    private List<Edge> calculateEdges() {
        List<Edge> route = new LinkedList<Edge>();

        Set<Edge> outgoingEdges;

        Node currentNode;

        int randomEdgeIdx;

        Edge nextEdge;

        currentNode = startNode;

        outgoingEdges = currentNode.getOutgoingEdges();

        Random random = new Random();

        while (!outgoingEdges.isEmpty()) {
            randomEdgeIdx = random.nextInt(outgoingEdges.size());
            nextEdge = (Edge) outgoingEdges.toArray()[randomEdgeIdx];

            route.add(nextEdge);
            outgoingEdges = nextEdge.getEnd().getOutgoingEdges();
        }

        return route;
    }

    public boolean hasNext() {
        return edgesIt.hasNext();
    }

    public Edge next() {
        return edgesIt.next();
    }

    public void remove() {
        edgesIt.remove();
    }
}
