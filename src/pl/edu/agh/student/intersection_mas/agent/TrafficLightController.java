package pl.edu.agh.student.intersection_mas.agent;

import akka.actor.UntypedActor;
import pl.edu.agh.student.intersection_mas.intersection.TrafficLight;

/**
 * Created by maciek on 10.05.16.
 */
public class TrafficLightController extends UntypedActor {
    private TrafficLight trafficLight;
    private int counter = 0;

    public TrafficLightController(TrafficLight trafficLight) {
        this.trafficLight = trafficLight;
    }

    @Override
    public void preStart() throws Exception {

    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message == TrafficLightMessage.COMPUTE_STATE) {
            counter++;
            if (counter == 25) {
                counter = 0;
                trafficLight.changeState();
            }

            System.out.println(this.toString());
        } else
            unhandled(message);
    }

    @Override
    public String toString() {
        return "TrafficLightController{" +
                "light=" + trafficLight.getState() +
                '}';
    }
}
