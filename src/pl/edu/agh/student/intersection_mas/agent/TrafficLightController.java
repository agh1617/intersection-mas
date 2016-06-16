package pl.edu.agh.student.intersection_mas.agent;

import akka.actor.UntypedActor;
import pl.edu.agh.student.intersection_mas.intersection.TrafficLight;
import pl.edu.agh.student.intersection_mas.intersection.TrafficLightState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by maciek on 10.05.16.
 */
public class TrafficLightController extends UntypedActor {
    private ArrayList<TrafficLight> trafficLights;
    private int counter = 0;
    private boolean[] remainingTrafficLights;
    private boolean[] greenTrafficLights;
    private ArrayList<Integer> trafficLightFactors;


    public TrafficLightController(ArrayList<TrafficLight> trafficLights) {
        this.trafficLights = trafficLights;
        remainingTrafficLights = new boolean[trafficLights.size()];
        greenTrafficLights = new boolean[trafficLights.size()];
        trafficLightFactors = new ArrayList<Integer>();
    }

    @Override
    public void preStart() throws Exception {

    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message == IntersectionSupervisorMessage.COMPUTE_STATE) {
            calculateState();
        } else
            unhandled(message);
    }

    private void calculateState() {
        int lightIndex, maxFactor;

        trafficLightFactors.clear();
        for (TrafficLight light : trafficLights) trafficLightFactors.add(light.calculateCarsOverTimeFactor());
        Arrays.fill(remainingTrafficLights, Boolean.TRUE);
        Arrays.fill(greenTrafficLights, Boolean.FALSE);

        for (lightIndex = 0; lightIndex < trafficLights.size(); lightIndex++) {
            TrafficLight light = trafficLights.get(lightIndex);

            if (remainingTrafficLights[lightIndex] && !light.allowsChange() && light.getState() != TrafficLightState.RED) {
                calculateTrafficLightState(lightIndex);
            }
        }

        while (true) {
            maxFactor = Collections.max(trafficLightFactors);
            if (maxFactor == 0) break;

            lightIndex = trafficLightFactors.indexOf(maxFactor);
            calculateTrafficLightState(lightIndex);
        }

        for (int i = 0; i < trafficLights.size(); i++) {
            TrafficLight light = trafficLights.get(i);
            if (greenTrafficLights[i]) light.changeState(TrafficLightState.GREEN);
            else light.changeState(TrafficLightState.RED);
        }
    }

    private void calculateTrafficLightState(int trafficLightIndex) {
        int dependentLightIndex;

        remainingTrafficLights[trafficLightIndex] = false;
        greenTrafficLights[trafficLightIndex] = true;
        trafficLightFactors.set(trafficLightIndex, 0);
        for (TrafficLight dependentLight : trafficLights.get(trafficLightIndex).getDependentTrafficLights()) {
            dependentLightIndex = trafficLights.indexOf(dependentLight);
            remainingTrafficLights[dependentLightIndex] = false;
            trafficLightFactors.set(dependentLightIndex, 0);
        }
    }
}
