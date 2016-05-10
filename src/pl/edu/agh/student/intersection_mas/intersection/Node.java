package pl.edu.agh.student.intersection_mas.intersection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by bzurkowski on 19.04.16.
 */
public class Node {
    private int id;

    private Set<Edge> incomingEdges;

    private Set<Edge> outgoingEdges;

    private Map<Edge, TrafficLight> trafficLights;

    public Node(int id) {
        this.id = id;

        this.incomingEdges = new HashSet<Edge>();
        this.outgoingEdges = new HashSet<Edge>();
        this.trafficLights = new HashMap<Edge, TrafficLight>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addIncomingEdge(Edge edge) {
        this.incomingEdges.add(edge);
    }

    public void addOutgoingEdge(Edge edge) {
        this.outgoingEdges.add(edge);
    }

    public Set<Edge> getIncomingEdges() {
        return incomingEdges;
    }

    public void setIncomingEdges(Set<Edge> incomingEdges) {
        this.incomingEdges = incomingEdges;
    }

    public Set<Edge> getOutgoingEdges() {
        return outgoingEdges;
    }

    public void setOutgoingEdges(Set<Edge> outgoingEdges) {
        this.outgoingEdges = outgoingEdges;
    }

    public Node randomNextNode() {
        return this.outgoingEdges.iterator().next().getEnd();
    }

    public Edge getEdgeTo(Node endNode) {
        for (Edge edge : this.getOutgoingEdges()) {
            if (edge.getEnd() == endNode) {
                return edge;
            }
        }
        return null;
    }

    public void addTrafficLight(TrafficLight trafficLight) {
        trafficLights.put(trafficLight.getIncomingEdge(), trafficLight);
    }

    public TrafficLight getTrafficLight(Edge edge) {
        return trafficLights.get(edge);
    }

    @Override
    public String toString() {
        return "pl.edu.agh.student.intersection_mas.intersection.Node{" +
                "id=" + id +
                '}';
    }
}
