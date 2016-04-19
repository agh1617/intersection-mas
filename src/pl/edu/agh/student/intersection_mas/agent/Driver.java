package pl.edu.agh.student.intersection_mas.agent;

import akka.actor.UntypedActor;

/**
 * Created by maciek on 19.04.16.
 */
public class Driver extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {
        if (message == DriverMessage.COMPUTE_STATE) {
            System.out.println("pl.edu.agh.student.intersection_mas.agent.Driver: message received");
            Thread.sleep(1000);
            getSender().tell(DriverMessage.DONE, getSelf());
        } else
            unhandled(message);
    }
}
