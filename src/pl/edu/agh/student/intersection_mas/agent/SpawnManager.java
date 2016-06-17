package pl.edu.agh.student.intersection_mas.agent;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.Props;
import pl.edu.agh.student.intersection_mas.intersection.Edge;
import pl.edu.agh.student.intersection_mas.intersection.Intersection;
import pl.edu.agh.student.intersection_mas.intersection.Node;

import java.util.*;

/**
 * Created by bzurkowski on 17.06.16.
 */
public class SpawnManager {
    private Intersection intersection;

    private ActorContext spawnContext;

    public SpawnManager(Intersection intersection, ActorContext spawnContext) {
        this.intersection = intersection;
        this.spawnContext = spawnContext;
    }

    public ArrayList<ActorRef> spawn(int amount) {
        ArrayList<ActorRef> spawnedActors = new ArrayList<ActorRef>();
        ArrayList<Edge> spawnEdges = findAvailableSpawnEdges();

        for (int i = 0; i < Math.min(amount, spawnEdges.size()); i++) {
            ActorRef spawnedActor = spawnContext.actorOf(
                    Props.create(Driver.class, intersection, spawnEdges.get(i)),
                    "driver_" + UUID.randomUUID().toString()
            );
            spawnedActors.add(spawnedActor);
        }

        return spawnedActors;
    }

    private ArrayList<Edge> findAvailableSpawnEdges() {
        ArrayList<Edge> availableEdges = new ArrayList<Edge>();

        for (Node startNode : intersection.getInputNodes()) {
            for (Edge edge : startNode.getOutgoingEdges()) {
                if (edge.getDriversInSegment(0, 10).isEmpty()) {
                    availableEdges.add(edge);
                }
            }
        }

        return availableEdges;
    }
}
