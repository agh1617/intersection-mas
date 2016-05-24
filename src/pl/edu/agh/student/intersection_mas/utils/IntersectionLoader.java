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
        HashSet<Node> nodes = new HashSet<Node>();
        HashSet<Node> inputNodes = new HashSet<Node>();
        HashSet<Node> outputNodes = new HashSet<Node>();

        Node node1 = new Node(1, 0, 130);
        Node node2 = new Node(2, 100, 130);
        Node node3 = new Node(3, 200, 130);
        Node node4 = new Node(4, 120, 120);
        Node node5 = new Node(5, 200, 120);
        Node node6 = new Node(6, 0, 110);
        Node node7 = new Node(7, 100, 110);
        Node node8 = new Node(8, 200, 110);
        Node node9 = new Node(9, 110, 100);
        Node node10 = new Node(10, 120, 100);
        Node node11 = new Node(11, 200, 100);
        Node node12 = new Node(12, 100, 0);
        Node node13 = new Node(13, 110, 0);
        Node node14 = new Node(14, 120, 0);

        Edge edge1 = new Edge(1, node2, node1);
        Edge edge2 = new Edge(2, node3, node2);
        Edge edge3 = new Edge(3, node7, node2);
        Edge edge4 = new Edge(4, node5, node4);
        Edge edge5 = new Edge(5, node4, node7);
        Edge edge6 = new Edge(6, node6, node7);
        Edge edge7 = new Edge(7, node7, node8);
        Edge edge8 = new Edge(8, node7, node12);
        Edge edge9 = new Edge(9, node9, node7);
        Edge edge10 = new Edge(10, node13, node9);
        Edge edge11 = new Edge(11, node14, node10);
        Edge edge12 = new Edge(12, node10, node11);

        HashSet<Edge> forbiddenEdges1 = new HashSet<Edge>();
        forbiddenEdges1.add(edge3);
        node7.addForbiddenEdges(edge5, forbiddenEdges1);

        HashSet<Edge> forbiddenEdges2 = new HashSet<Edge>();
        forbiddenEdges2.add(edge7);
        node7.addForbiddenEdges(edge9, forbiddenEdges2);

        TrafficLight light1 = new TrafficLight(1, edge2);
        TrafficLight light2 = new TrafficLight(2, edge4);
        TrafficLight light3 = new TrafficLight(3, edge6);
        TrafficLight light4 = new TrafficLight(4, edge10);

        HashSet<TrafficLight> dependentTrafficLights1 = new HashSet<TrafficLight>();
        dependentTrafficLights1.add(light4);
        light1.setDependentTrafficLights(dependentTrafficLights1);

        HashSet<TrafficLight> dependentTrafficLights2 = new HashSet<TrafficLight>();
        dependentTrafficLights2.add(light3);
        dependentTrafficLights2.add(light4);
        light2.setDependentTrafficLights(dependentTrafficLights2);

        HashSet<TrafficLight> dependentTrafficLights3 = new HashSet<TrafficLight>();
        dependentTrafficLights3.add(light2);
        dependentTrafficLights3.add(light4);
        light3.setDependentTrafficLights(dependentTrafficLights3);

        HashSet<TrafficLight> dependentTrafficLights4 = new HashSet<TrafficLight>();
        dependentTrafficLights4.add(light1);
        dependentTrafficLights4.add(light2);
        dependentTrafficLights4.add(light3);
        dependentTrafficLights4.add(light4);
        light4.setDependentTrafficLights(dependentTrafficLights4);

        node2.addTrafficLight(light1);
        node4.addTrafficLight(light2);
        node7.addTrafficLight(light3);
        node9.addTrafficLight(light4);

        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);
        nodes.add(node4);
        nodes.add(node5);
        nodes.add(node6);
        nodes.add(node7);
        nodes.add(node8);
        nodes.add(node9);
        nodes.add(node10);
        nodes.add(node11);
        nodes.add(node12);
        nodes.add(node13);
        nodes.add(node14);
        inputNodes.add(node3);
        inputNodes.add(node5);
        inputNodes.add(node6);
        inputNodes.add(node13);
        inputNodes.add(node14);
        outputNodes.add(node1);
        outputNodes.add(node8);
        outputNodes.add(node11);
        outputNodes.add(node12);

        Intersection intersection = new Intersection(nodes);
        intersection.setInputNodes(inputNodes);
        intersection.setOutputNodes(outputNodes);

        intersection.addTraficLight(light1);
        intersection.addTraficLight(light2);
        intersection.addTraficLight(light3);
        intersection.addTraficLight(light4);

        return intersection;
    }
}
