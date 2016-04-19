package pl.edu.agh.student.intersection_mas.agent;

import akka.actor.UntypedActor;
import pl.edu.agh.student.intersection_mas.intersection.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by maciek on 19.04.16.
 */
public class Driver extends UntypedActor {
    private Intersection intersection;

    private IntersectionState intersectionState;

    private Route route;

    private int speed;

    private Iterator<Node> routeIt;

    public Driver(Intersection intersection) {
        this.intersection = intersection;
        this.intersectionState = this.intersection.getIntersectionState();

        this.speed = 10;
    }

    @Override
    public void preStart() throws Exception {
        Node startNode = intersection.getInputNodes().iterator().next();
        Node endNode = intersection.getOutputNodes().iterator().next();

        this.route = new Route(startNode, endNode);
        this.routeIt = this.route.getNodes().iterator();

        Edge startEdge = startNode.getEdgeTo(this.routeIt.next());
        DriverPosition startPosition = new DriverPosition(startEdge, 0);

        this.intersectionState.addDriverPosition(this, startPosition);
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message == DriverMessage.COMPUTE_STATE) {
            System.out.println("pl.edu.agh.student.intersection_mas.agent.Driver: message received");

            this.move();
            Thread.sleep(1000);

            getSender().tell(DriverMessage.DONE, getSelf());
        } else
            unhandled(message);
    }

    private void move() {
        DriverPosition driverPosition = this.intersectionState.getDriverPosition(this);

        Node currentNode, nextNode;
        Edge currentEdge, nextEdge;

        currentEdge = driverPosition.getEdge();
        currentNode = currentEdge.getEnd();

        int nextPosition;

        if (driverPosition.getPosition() + speed > currentEdge.getLength()) {
            nextNode = this.routeIt.next();
            nextEdge = currentNode.getEdgeTo(nextNode);

            nextPosition = driverPosition.getPosition() + speed - currentEdge.getLength();;
        } else {
            nextEdge = currentEdge;
            nextPosition = driverPosition.getPosition() + speed;
        }

        driverPosition.set(nextEdge, nextPosition);
        this.intersectionState.setDriverPosition(this, driverPosition);
    }
}
