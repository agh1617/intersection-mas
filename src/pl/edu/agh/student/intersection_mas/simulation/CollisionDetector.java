package pl.edu.agh.student.intersection_mas.simulation;

import pl.edu.agh.student.intersection_mas.agent.Driver;
import pl.edu.agh.student.intersection_mas.intersection.Edge;
import pl.edu.agh.student.intersection_mas.intersection.Intersection;
import pl.edu.agh.student.intersection_mas.intersection.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by bzurkowski on 17.06.16.
 */
public class CollisionDetector {
    private Intersection intersection;

    public CollisionDetector(Intersection intersection) {
        this.intersection = intersection;
    }

    public Set<Set<Driver>> detectCollisions() {
        Set<Node> nodes = intersection.getNodes();

        Set<Set<Driver>> collisions = new HashSet<Set<Driver>>();

        for (Node startNode : nodes) {
            for (Edge edge : startNode.getOutgoingEdges()) {
                collisions.addAll(detectCollisionsOnEdge(edge));
            }
        }

        return collisions;
    }

    private Set<Set<Driver>> detectCollisionsOnEdge(Edge edge) {
        List<Driver> drivers = new ArrayList<Driver>(edge.getDrivers());
        Set<Set<Driver>> collisions = new HashSet<Set<Driver>>();

        for (int i = 0; i < drivers.size(); i++) {
            Driver currentDriver = drivers.get(i);

            for (int j = i + 1; j < drivers.size(); j++) {
                Driver otherDriver = drivers.get(j);

                if (currentDriver.collidesWith(otherDriver)) {
                    Set<Driver> collision = new HashSet<Driver>();

                    collision.add(currentDriver);
                    collision.add(otherDriver);

                    collisions.add(collision);
                }
            }
        }

        return collisions;
    }
}
