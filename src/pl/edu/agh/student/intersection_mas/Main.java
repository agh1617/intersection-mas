package pl.edu.agh.student.intersection_mas;

import akka.actor.ActorSystem;
import akka.actor.Props;
import pl.edu.agh.student.intersection_mas.agent.IntersectionSupervisor;
import pl.edu.agh.student.intersection_mas.intersection.Intersection;
import pl.edu.agh.student.intersection_mas.intersection.Node;
import pl.edu.agh.student.intersection_mas.utils.IntersectionLoader;

import java.util.Set;

/**
 * Created by maciek on 19.04.16.
 */
public class Main {
    public static void main(String[] args) {
        Set<Node> inputNodes = IntersectionLoader.loadIntersection();
        Intersection intersection = new Intersection(inputNodes);

        ActorSystem system = ActorSystem.create("IntersectionSimulation");
        system.actorOf(Props.create(IntersectionSupervisor.class, intersection, 2, 5), "intersectionSupervisor");
    }
}
