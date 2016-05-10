package pl.edu.agh.student.intersection_mas.utils;

import pl.edu.agh.student.intersection_mas.intersection.Edge;
import pl.edu.agh.student.intersection_mas.intersection.Intersection;
import pl.edu.agh.student.intersection_mas.intersection.Node;
import pl.edu.agh.student.intersection_mas.intersection.TrafficLight;

import java.util.HashSet;

/**
 * Created by bzurkowski on 19.04.16.
 */
public class IntersectionLoader {
    public static Intersection loadIntersection() {
        HashSet<Node> inputNodes = new HashSet<Node>();
        HashSet<Node> outputNodes = new HashSet<Node>();

        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node(7);
        Node node8 = new Node(8);

        Edge edge1 = new Edge(1, 100, node1, node2);
        Edge edge2 = new Edge(2, 100, node2, node3);
        Edge edge3 = new Edge(3, 100, node3, node4);
        Edge edge4 = new Edge(4, 100, node6, node2);
        Edge edge5 = new Edge(5, 100, node3, node7);
        Edge edge6 = new Edge(6, 100, node6, node5);
        Edge edge7 = new Edge(7, 100, node7, node6);
        Edge edge8 = new Edge(8, 100, node8, node7);

        TrafficLight light1 = new TrafficLight(edge1);
        TrafficLight light2 = new TrafficLight(edge8);

        node2.addTrafficLight(light1);
        node7.addTrafficLight(light2);

        node1.addOutgoingEdge(edge1);
        node2.addOutgoingEdge(edge2);
        node3.addOutgoingEdge(edge3);
        node3.addOutgoingEdge(edge5);
        node6.addOutgoingEdge(edge4);
        node6.addOutgoingEdge(edge6);
        node7.addOutgoingEdge(edge7);
        node8.addOutgoingEdge(edge8);

        node2.addIncomingEdge(edge1);
        node2.addIncomingEdge(edge4);
        node3.addIncomingEdge(edge2);
        node4.addIncomingEdge(edge3);
        node5.addIncomingEdge(edge6);
        node6.addIncomingEdge(edge7);
        node7.addIncomingEdge(edge5);
        node7.addIncomingEdge(edge8);

        inputNodes.add(node1);
        inputNodes.add(node8);
        outputNodes.add(node4);
        outputNodes.add(node5);

        Intersection intersection = new Intersection(inputNodes, outputNodes);

        intersection.addTraficLight(light1);
        intersection.addTraficLight(light2);

        return intersection;
    }
}
