package pl.edu.agh.student.intersection_mas.agent;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import pl.edu.agh.student.intersection_mas.gui.IntersectionView;
import pl.edu.agh.student.intersection_mas.intersection.Intersection;
import pl.edu.agh.student.intersection_mas.simulation.CollisionDetector;
import pl.edu.agh.student.intersection_mas.simulation.SpawnManager;
import pl.edu.agh.student.intersection_mas.simulation.SimulationProperties;
import pl.edu.agh.student.intersection_mas.simulation.StatisticsCollector;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * Created by maciek on 19.04.16.
 */
public class IntersectionSupervisor extends UntypedActor {
    private int maxDriversCount;
    private AtomicInteger currentDriversCount;
    private ArrayList<ActorRef> drivers = new ArrayList<ActorRef>();
    private ActorRef trafficLightController;
    private int receivedStates = 0;
    private int simulationStepsLimit;
    private int currentSimulationStep;
    private int stepDuration;
    private int simulationNumber;
    private Intersection intersection;
    private IntersectionView intersectionView;
    private StatisticsCollector statisticsCollector;
    private CollisionDetector collisionDetector;
    private SpawnManager spawnManager;
    private Logger collisionsLogger;

    public IntersectionSupervisor(Intersection intersection, IntersectionView intersectionView) {
        this.intersection = intersection;
        this.intersectionView = intersectionView;

        SimulationProperties properties = SimulationProperties.getInstance();
        this.simulationStepsLimit = Integer.parseInt(properties.get("simulationStepsLimit"));
        this.maxDriversCount = Integer.parseInt(properties.get("driversNumber"));
        this.stepDuration = Integer.parseInt(properties.get("stepDuration"));
        this.simulationNumber = Integer.parseInt(properties.get("simulationNumber"));
        this.currentDriversCount = new AtomicInteger(0);
        this.currentSimulationStep = 0;
        this.statisticsCollector = new StatisticsCollector(intersection);
        this.collisionDetector = new CollisionDetector(intersection);
        this.spawnManager = new SpawnManager(intersection, getContext());

        this.collisionsLogger = Logger.getLogger("collisions");
    }

    @Override
    public void preStart() {
        trafficLightController = getContext().actorOf(Props.create(TrafficLightController.class, intersection.getTrafficLights()), "traffic_light_controller");
        spawnDrivers();
        askDriversForState();
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message == DriverMessage.DONE) {
            handleMovement();
        }
        else if (message == DriverMessage.FINISHED) {
            drivers.remove(getSender());

            currentDriversCount.decrementAndGet();

            handleMovement();
        } else
            unhandled(message);
    }

    private void handleMovement() {
        receivedStates++;

        if (receivedStates >= currentDriversCount.get()) {
            currentSimulationStep++;
            receivedStates = 0;

            try {
                Thread.sleep(stepDuration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            intersectionView.updateView();
            statisticsCollector.update();
            detectCollisions();
            spawnDrivers();
            askDriversForState();
            trafficLightController.tell(TrafficLightMessage.COMPUTE_STATE, getSelf());

            if (currentSimulationStep >= simulationStepsLimit) {
                getContext().system().shutdown();
                System.exit(0);
            }
        }
    }

    private void detectCollisions() {
        Set<Set<Driver>> collisions = collisionDetector.detectCollisions();
        int numCollisions = collisions.size();

        collisionsLogger.info(String.format("%d,%d,%d", simulationNumber, currentSimulationStep, numCollisions));
    }

    private void askDriversForState() {
        for (ActorRef driver : drivers) {
            driver.tell(DriverMessage.COMPUTE_STATE, getSelf());
        }
    }

    private void spawnDrivers() {
        int numDriversToSpawn = maxDriversCount - currentDriversCount.get();

        ArrayList<ActorRef> spawnedAgents = spawnManager.spawn(numDriversToSpawn);
        drivers.addAll(spawnedAgents);

        currentDriversCount.set(drivers.size());
    }
}
