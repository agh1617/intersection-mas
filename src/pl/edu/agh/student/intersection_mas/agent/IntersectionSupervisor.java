package pl.edu.agh.student.intersection_mas.agent;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

import java.util.ArrayList;

/**
 * Created by maciek on 19.04.16.
 */
public class IntersectionSupervisor extends UntypedActor {
    private int driversNumber;
    private ArrayList<ActorRef> drivers = new ArrayList<ActorRef>();
    private int receivedStates = 0;
    private int simulationSteps;

    public IntersectionSupervisor(int simulationSteps, int driversNumber) {
        this.simulationSteps = simulationSteps;
        this.driversNumber = driversNumber;
    }

    @Override
    public void preStart() {
        ActorRef driver;
        for (int i = 0; i < driversNumber; i++) {
            driver = getContext().actorOf(Props.create(Driver.class), "driver_" + i);
            drivers.add(driver);
            driver.tell(DriverMessage.COMPUTE_STATE, getSelf());
        }
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message == DriverMessage.DONE) {
            receivedStates++;
            System.out.println("message received");
            if (receivedStates == driversNumber) {
                System.out.println("All message received");
                receivedStates = 0;
                askDriversForState();
            }
        } else
            unhandled(message);
    }

    private void askDriversForState() {
        for (ActorRef driver : drivers) {
            driver.tell(DriverMessage.COMPUTE_STATE, getSelf());
        }
    }
}
