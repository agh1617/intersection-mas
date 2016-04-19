package pl.edu.agh.student.intersection_mas.utils;

import pl.edu.agh.student.intersection_mas.intersection.Edge;
import pl.edu.agh.student.intersection_mas.intersection.Node;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by bzurkowski on 19.04.16.
 */
public class IntersectionLoader {
    public static Set<Node> loadIntersection() {
        HashSet<Node> inputNodes = new HashSet<Node>();

        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        Edge edge1 = new Edge(1, node1, node2);
        Edge edge2 = new Edge(2, node2, node2);

        node1.addOutgoingEdge(edge1);
        node2.addIncomingEdge(edge1);
        node2.addOutgoingEdge(edge2);
        node3.addIncomingEdge(edge2);

        inputNodes.add(node1);

        return inputNodes;
    }
}