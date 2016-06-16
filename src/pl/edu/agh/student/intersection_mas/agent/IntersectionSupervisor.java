package pl.edu.agh.student.intersection_mas.agent;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import pl.edu.agh.student.intersection_mas.gui.IntersectionView;
import pl.edu.agh.student.intersection_mas.intersection.Intersection;
import pl.edu.agh.student.intersection_mas.utils.SimulationLogger;
import pl.edu.agh.student.intersection_mas.utils.StatisticsCollector;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by maciek on 19.04.16.
 */
public class IntersectionSupervisor extends UntypedActor {
    private int driversNumber;
    private ArrayList<ActorRef> drivers = new ArrayList<ActorRef>();
    private ActorRef trafficLightController;
    private int receivedStates = 0;
    private int simulationSteps;
    private int currentSimulationStep;
    private Intersection intersection;
    private IntersectionView intersectionView;
    private StatisticsCollector statisticsCollector;
    private SimulationLogger driversLogger;

    public IntersectionSupervisor(Intersection intersection, IntersectionView intersectionView, int simulationSteps, int driversNumber) {
        this.intersection = intersection;
        this.intersectionView = intersectionView;
        this.simulationSteps = simulationSteps;
        this.driversNumber = driversNumber;

        this.currentSimulationStep = 0;
        this.statisticsCollector = new StatisticsCollector(intersection);
        this.driversLogger = new SimulationLogger("drivers");
    }

    @Override
    public void preStart() {
        trafficLightController = getContext().actorOf(Props.create(TrafficLightController.class, intersection.getTrafficLights()), "traffic_light_controller");
        spawnDrivers();
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message == DriverMessage.DONE) {
            handleMovement();
        }
        else if (message == DriverMessage.FINISHED) {
            drivers.remove(getSender());
            ActorRef driver = getContext().actorOf(Props.create(Driver.class, this.intersection, this.driversLogger), "driver_" + UUID.randomUUID().toString());
            drivers.add(driver);
            handleMovement();
        } else
            unhandled(message);
    }

    private void handleMovement() {
        receivedStates++;

        if (receivedStates >= driversNumber) {
            currentSimulationStep++;
            receivedStates = 0;

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            intersectionView.updateView();
            statisticsCollector.update();
            askDriversForState();
            trafficLightController.tell(TrafficLightMessage.COMPUTE_STATE, getSelf());
        }
    }

    private void askDriversForState() {
        for (ActorRef driver : drivers) {
            driver.tell(DriverMessage.COMPUTE_STATE, getSelf());
        }
    }

    private void spawnDrivers() {
        ActorRef driver;
        for (int i = 0; i < driversNumber; i++) {
            driver = getContext().actorOf(Props.create(Driver.class, this.intersection, this.driversLogger), "driver_" + UUID.randomUUID().toString());
            drivers.add(driver);
            driver.tell(DriverMessage.COMPUTE_STATE, getSelf());
        }
    }
}
