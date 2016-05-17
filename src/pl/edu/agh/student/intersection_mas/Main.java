package pl.edu.agh.student.intersection_mas;

import akka.actor.ActorSystem;
import akka.actor.Props;
import pl.edu.agh.student.intersection_mas.agent.IntersectionSupervisor;
import pl.edu.agh.student.intersection_mas.intersection.Intersection;
import pl.edu.agh.student.intersection_mas.utils.IntersectionLoader;

/**
 * Created by maciek on 19.04.16.
 */
public class Main {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("IntersectionSimulation");


        Intersection intersection = IntersectionLoader.loadIntersection();

        system.actorOf(Props.create(IntersectionSupervisor.class, intersection, 2, 1), "intersectionSupervisor");
    }
}
