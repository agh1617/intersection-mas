package pl.edu.agh.student.intersection_mas.agent;

import akka.actor.UntypedActor;
import pl.edu.agh.student.intersection_mas.intersection.Intersection;
import pl.edu.agh.student.intersection_mas.intersection.Node;
import pl.edu.agh.student.intersection_mas.intersection.NodeIterator;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by maciek on 19.04.16.
 */
public class Driver extends UntypedActor {
    private Intersection intersection;

    private Route route;

    public Driver(Intersection intersection) {
        this.intersection = intersection;
    }

    @Override
    public void preStart() throws Exception {
        Node startNode = intersection.getInputNodes().iterator().next();
        List routeNodes = new LinkedList<Node>();

        NodeIterator nodeIt = new NodeIterator(startNode);

        while (nodeIt.hasNext()) {
            routeNodes.add(nodeIt.next());
        }

        this.route = new Route(routeNodes);
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message == DriverMessage.COMPUTE_STATE) {
            System.out.println("pl.edu.agh.student.intersection_mas.agent.Driver: message received");

            Thread.sleep(1000);
            getSender().tell(DriverMessage.DONE, getSelf());
        } else
            unhandled(message);
    }
}
