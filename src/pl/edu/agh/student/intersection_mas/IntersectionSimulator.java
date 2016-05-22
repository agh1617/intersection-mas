package pl.edu.agh.student.intersection_mas;

import akka.actor.ActorSystem;
import akka.actor.Props;
import pl.edu.agh.student.intersection_mas.agent.IntersectionSupervisor;
import pl.edu.agh.student.intersection_mas.gui.IntersectionView;
import pl.edu.agh.student.intersection_mas.intersection.Intersection;
import pl.edu.agh.student.intersection_mas.utils.IntersectionLoader;

import java.awt.*;

/**
 * Created by maciek on 19.04.16.
 */
public class IntersectionSimulator {
    public IntersectionSimulator() {}

    private void run() {
        ActorSystem system = ActorSystem.create("IntersectionSimulation");
        Intersection intersection = IntersectionLoader.loadIntersection();
        final IntersectionView intersectionView = new IntersectionView(intersection);

        system.actorOf(Props.create(IntersectionSupervisor.class, intersection, intersectionView, 2, 15), "intersectionSupervisor");

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                intersectionView.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        IntersectionSimulator intersectionSimulator = new IntersectionSimulator();
        intersectionSimulator.run();
    }
}
