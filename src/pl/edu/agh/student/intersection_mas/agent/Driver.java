package pl.edu.agh.student.intersection_mas.agent;

import akka.actor.UntypedActor;
import pl.edu.agh.student.intersection_mas.intersection.Edge;
import pl.edu.agh.student.intersection_mas.intersection.Intersection;
import pl.edu.agh.student.intersection_mas.intersection.Node;
import pl.edu.agh.student.intersection_mas.intersection.TrafficLight;

import java.awt.*;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by maciek on 19.04.16.
 */
public class Driver extends UntypedActor {
    private static final int MAX_LENGTH = 10;
    private static final int MIN_DISTANCE = 10;

    private Intersection intersection;
    private Logger logger;
    private DriverState state;

    private DriverPosition position;

    private int speed;
    private int maxSpeed;
    private int acceleration;
    private int deceleration;
    private int length;
    private int simulationStep;
    private int distanceCovered;

    private Color color;

    public Driver(Intersection intersection) {
        this.intersection = intersection;
        this.logger = Logger.getLogger("drivers");

        this.simulationStep = 0;
        this.speed = 0;
        this.distanceCovered = 0;
        this.state = DriverState.IDLE;

        this.maxSpeed = 15 + new Random().nextInt(10);

        this.acceleration = 3 + new Random().nextInt(2);
        this.deceleration = 3 + new Random().nextInt(3);

        this.length = 3 + new Random().nextInt(2);

        this.color = calculateRandomColor();
    }

    @Override
    public void preStart() throws Exception {
        Edge startEdge = this.calculateStartEdge();
        this.position = new DriverPosition(startEdge, 0);
        startEdge.addDriver(this);
    }

    private Edge calculateStartEdge() {
        Set<Node> startNodes = intersection.getInputNodes();
        Node startNode = (Node) startNodes.toArray()[new Random().nextInt(startNodes.size())];

        Set<Edge> outgoingEdges = startNode.getOutgoingEdges();

        return (Edge) outgoingEdges.toArray()[new Random().nextInt(outgoingEdges.size())];
    }

    @Override
    public void onReceive(Object message) {
        if (message == DriverMessage.COMPUTE_STATE) {
            if (this.moveForward()) {
                this.simulationStep++;
                this.distanceCovered += this.speed;
                this.calculateState();
                this.updateSpeed();
//                System.out.println(this.toString());

                getSender().tell(DriverMessage.DONE, getSelf());
            }
            else {
                logger.info(String.valueOf(this.simulationStep));
                getSender().tell(DriverMessage.FINISHED, getSelf());
            }
        } else
            unhandled(message);
    }

    private void updateSpeed() {
        switch (this.state) {
            case ACCELERATION:
                this.speed = Math.min(this.maxSpeed, this.speed + this.acceleration);
                break;
            case DECELERATION:
                this.speed = Math.max(0, this.speed - this.deceleration);
                break;
            case IDLE:
                break;
        }
    }

    private void calculateState() {
        int distance = this.speed + this.acceleration + this.calculateBrakingDistance();

        Edge currentEdge = this.position.getEdge();
        Node currentNode = currentEdge.getEnd();

        int currentPosition = this.position.getPosition();
        int currentHeadPosition = (int) (currentPosition + (float) length / 2);

        Set<Driver> driversAhead;
        TrafficLight trafficLightAhead;

        do {
            driversAhead = currentEdge.getDriversInSegment(currentHeadPosition, distance);

            if (driversAhead.isEmpty()) {
                if (this.state == DriverState.DECELERATION) this.state = DriverState.IDLE;
                else this.state = DriverState.ACCELERATION;
            } else {
                this.state = DriverState.DECELERATION;
                break;
            }

            distance -= currentEdge.getLength() - currentPosition;

            if (distance >= 0) {
                trafficLightAhead = currentNode.getTrafficLight(currentEdge);

                if (trafficLightAhead != null && !trafficLightAhead.allowsTraffic()) {
                    this.state = DriverState.DECELERATION;
                    break;
                }

                currentEdge = this.calculateNextEdge(currentEdge);

                if (currentEdge == null) return;
            }

            currentPosition = 0;
        } while (distance > 0);
    }

    private boolean moveForward() {
        int distance = this.speed;

        Edge currentEdge = this.position.getEdge();

        int currentPosition = this.position.getPosition();

        while (distance > 0) {
            if (distance >= currentEdge.getLength() - currentPosition) {
                distance -= currentEdge.getLength() - currentPosition;

                currentEdge.removeDriver(this);
                currentEdge = this.calculateNextEdge(currentEdge);

                if (currentEdge == null) return false;

                currentEdge.addDriver(this);
                this.position.set(currentEdge, 0);
            } else {
                this.position.set(currentEdge, currentPosition + distance);
                distance = 0;
            }
            currentPosition = 0;
        }
        return true;
    }

    private Edge calculateNextEdge(Edge currentEdge) {
        Node endNode = currentEdge.getEnd();
        Set<Edge> outgoingEdges = endNode.getAllowedOutgoingEdges(currentEdge);

        int randomEdgeIdx;

        if (outgoingEdges.size() > 0) {
            randomEdgeIdx = new Random().nextInt(outgoingEdges.size());
            return (Edge) outgoingEdges.toArray()[randomEdgeIdx];
        }

        return null;
    }

    private int calculateBrakingDistance() {
        int featureSpeed = this.speed + this.acceleration;
        return (int) Math.ceil((double) (featureSpeed * featureSpeed) / (2 * this.deceleration));
    }

    public DriverPosition getPosition() {
        return position;
    }

    public int getLength() {
        return length;
    }

    public Color getColor() {
        return color;
    }

    public int getSpeed() {
        return this.speed;
    }

    public boolean isDriving() {
        return this.distanceCovered > MIN_DISTANCE;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + System.identityHashCode(this) +
                ", state=" + state +
                ", edge=" + position.getEdge().getId() +
                ", position=" + position.getPosition() +
                ", speed=" + speed +
                ", acceleration=" + acceleration +
                ", deceleration=" + deceleration +
                '}';
    }

    private Color calculateRandomColor() {
        Random rand = new Random();

        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();

        return new Color(r, g, b);
    }
}
