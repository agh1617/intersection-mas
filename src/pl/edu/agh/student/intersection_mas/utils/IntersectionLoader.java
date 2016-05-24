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

        Node node1 = new Node(1, 75, 225);
        Node node2 = new Node(2, 50, 125);
        Node node3 = new Node(3, 75, 125);
        Node node4 = new Node(4, 0, 75);
        Node node5 = new Node(5, 75, 75);
        Node node6 = new Node(6, 125, 75);
        Node node7 = new Node(7, 225, 75);
        Node node8 = new Node(8, 125, 50);
        Node node9 = new Node(9, 75, 0);

        Edge edge1 = new Edge(1, 200, node1, node2);
        Edge edge2 = new Edge(2, 200, node1, node3);
        Edge edge3 = new Edge(3, 100, node2, node4);
        Edge edge4 = new Edge(4, 100, node3, node5);
        Edge edge5 = new Edge(5, 100, node5, node4);
        Edge edge6 = new Edge(6, 100, node6, node5);
        Edge edge7 = new Edge(7, 200, node7, node6);
        Edge edge8 = new Edge(8, 100, node5, node9);
        Edge edge9 = new Edge(9, 200, node7, node8);
        Edge edge10 = new Edge(10, 100, node8, node9);

        TrafficLight light1 = new TrafficLight(edge1);
        TrafficLight light2 = new TrafficLight(edge2);
        TrafficLight light3 = new TrafficLight(edge7);
        TrafficLight light4 = new TrafficLight(edge9);

        HashSet<TrafficLight> dependentTrafficLights1 = new HashSet<TrafficLight>();
        dependentTrafficLights1.add(light3);
        light1.setDependentTrafficLights(dependentTrafficLights1);

        HashSet<TrafficLight> dependentTrafficLights2 = new HashSet<TrafficLight>();
        dependentTrafficLights2.add(light3);
        dependentTrafficLights2.add(light4);
        light2.setDependentTrafficLights(dependentTrafficLights2);

        HashSet<TrafficLight> dependentTrafficLights3 = new HashSet<TrafficLight>();
        dependentTrafficLights3.add(light1);
        dependentTrafficLights3.add(light2);
        light3.setDependentTrafficLights(dependentTrafficLights3);

        HashSet<TrafficLight> dependentTrafficLights4 = new HashSet<TrafficLight>();
        dependentTrafficLights4.add(light2);
        light4.setDependentTrafficLights(dependentTrafficLights4);

        node2.addTrafficLight(light1);
        node3.addTrafficLight(light2);
        node6.addTrafficLight(light3);
        node8.addTrafficLight(light4);

        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);
        nodes.add(node4);
        nodes.add(node5);
        nodes.add(node6);
        nodes.add(node7);
        nodes.add(node8);
        nodes.add(node9);
        inputNodes.add(node1);
        inputNodes.add(node7);
        outputNodes.add(node4);
        outputNodes.add(node9);

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
