package pl.edu.agh.student.intersection_mas.intersection;

import java.util.Set;

/**
 * Created by maciek on 10.05.16.
 */
public class TrafficLight {
    private static final int MIN_STEPS_TO_CHANGE_STATE = 20;
    private static final int CARS_OVER_TIME_THRESHOLD = 5;

    private Edge incomingEdge;
    private TrafficLightState state;
    private int stepsSinceStageChange;
    private Set<TrafficLight> dependentTrafficLights;

    public TrafficLight(Edge incommingEdge) {
        this.incomingEdge = incommingEdge;
        this.state = TrafficLightState.RED;
    }

    public Edge getIncomingEdge() {
        return incomingEdge;
    }

    public void setDependentTrafficLights(Set<TrafficLight> dependentTrafficLights) {
        this.dependentTrafficLights = dependentTrafficLights;
    }

    public Set<TrafficLight> getDependentTrafficLights() {
        return dependentTrafficLights;
    }

    public TrafficLightState getState() {
        return state;
    }

    public void changeState(TrafficLightState state) {
        if (this.state != state) {
            this.state = state;
            this.stepsSinceStageChange = 0;
        }
    }

    public boolean allowChange() {
        return stepsSinceStageChange > MIN_STEPS_TO_CHANGE_STATE;
    }

    public boolean allowsTraffic() {
        return this.state == TrafficLightState.GREEN;
    }

    public int calculateCarsOverTimeFactor() {
        this.stepsSinceStageChange++;

        return stepsSinceStageChange * calculateApproachingDriversCount();
    }

    private int calculateApproachingDriversCount() {
        return  incomingEdge.getDrivers().size();
    }
}
