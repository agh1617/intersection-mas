package pl.edu.agh.student.intersection_mas.agent;

import akka.actor.UntypedActor;
import pl.edu.agh.student.intersection_mas.intersection.*;

import java.util.Random;
import java.util.Set;

/**
 * Created by maciek on 19.04.16.
 */
public class Driver extends UntypedActor {
    private Intersection intersection;

    private IntersectionState intersectionState;

    private int speed;

    public Driver(Intersection intersection) {
        this.intersection = intersection;
        this.intersectionState = this.intersection.getIntersectionState();

        this.speed = 60;
    }

    @Override
    public void preStart() throws Exception {
        Set<Node> startNodes = intersection.getInputNodes();
        Node startNode = (Node) startNodes.toArray()[new Random().nextInt(startNodes.size())];

        Set<Edge> outgoingEdges = startNode.getOutgoingEdges();
        Edge startEdge = (Edge) outgoingEdges.toArray()[new Random().nextInt(outgoingEdges.size())];
        DriverPosition startPosition = new DriverPosition(startEdge, 0);

        this.intersectionState.addDriverPosition(this, startPosition);
    }

    @Override
    public void onReceive(Object message) {
        if (message == DriverMessage.COMPUTE_STATE) {
            if (this.moveForward()) {

                System.out.println(
                        this.intersectionState.getDriverPosition(this).toString()
                );

                getSender().tell(DriverMessage.DONE, getSelf());
            }
            else {
                getSender().tell(DriverMessage.FINISHED, getSelf());
            }
        } else
            unhandled(message);
    }

    private boolean moveForward() {
        DriverPosition driverPosition = this.intersectionState.getDriverPosition(this);

        Node currentNode;
        Edge currentEdge, nextEdge;

        currentEdge = driverPosition.getEdge();
        currentNode = currentEdge.getEnd();

        int nextPosition;

        if (driverPosition.getPosition() + speed > currentEdge.getLength()) {
            TrafficLight light = currentNode.getTrafficLight(currentEdge);
            if (light != null && light.getState() == TrafficLightState.RED) {
                nextEdge = currentEdge;
                nextPosition = currentEdge.getLength();
            }
            else {
                Set<Edge> outgoingEdges = currentNode.getOutgoingEdges();
                if (outgoingEdges.size() > 0) {
                    nextEdge = (Edge) outgoingEdges.toArray()[new Random().nextInt(outgoingEdges.size())];

                    nextPosition = driverPosition.getPosition() + speed - currentEdge.getLength();
                } else {
                    return false;
                }
            }
        } else {
            nextEdge = currentEdge;
            nextPosition = driverPosition.getPosition() + speed;
        }

        driverPosition.set(nextEdge, nextPosition);
        this.intersectionState.setDriverPosition(this, driverPosition);

        return true;
    }
}
