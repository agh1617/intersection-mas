package pl.edu.agh.student.intersection_mas.agent;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import pl.edu.agh.student.intersection_mas.intersection.Intersection;
import pl.edu.agh.student.intersection_mas.intersection.TrafficLight;

import java.util.ArrayList;

/**
 * Created by maciek on 19.04.16.
 */
public class IntersectionSupervisor extends UntypedActor {
    private int driversNumber;
    private ArrayList<ActorRef> drivers = new ArrayList<ActorRef>();
    private ArrayList<ActorRef> trafficLightControllers = new ArrayList<ActorRef>();
    private int receivedStates = 0;
    private int simulationSteps;
    private Intersection intersection;

    public IntersectionSupervisor(Intersection intersection, int simulationSteps, int driversNumber) {
        this.intersection = intersection;
        this.simulationSteps = simulationSteps;
        this.driversNumber = driversNumber;
    }

    @Override
    public void preStart() {
        spawnTrafficLights(intersection.getTrafficLights());
        spawnDrivers();
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message == DriverMessage.DONE) {
            handleMovement();
        }
        else if (message == DriverMessage.FINISHED) {
            drivers.remove(getSender());
            handleMovement();
            driversNumber--;
        } else
            unhandled(message);
    }

    private void handleMovement() {
        receivedStates++;
        System.out.println("message received");
        if (receivedStates == driversNumber) {
            System.out.println("All message received");
            receivedStates = 0;
            askDriversForState();
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
            driver = getContext().actorOf(Props.create(Driver.class, this.intersection), "driver_" + i);
            drivers.add(driver);
            driver.tell(DriverMessage.COMPUTE_STATE, getSelf());
        }
    }

    private void spawnTrafficLights(ArrayList<TrafficLight> trafficLights) {
        ActorRef lightController;
        for (int i = 0; i < trafficLights.size(); i++) {
            lightController = getContext().actorOf(Props.create(TrafficLightController.class, trafficLights.get(i)), "traffic_light_" + i);
            trafficLightControllers.add(lightController);
        }
    }
}
