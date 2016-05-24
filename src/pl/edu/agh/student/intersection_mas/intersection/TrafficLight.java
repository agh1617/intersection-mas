package pl.edu.agh.student.intersection_mas.intersection;

import java.util.Set;

/**
 * Created by maciek on 10.05.16.
 */
public class TrafficLight {
    private static final int MIN_STEPS_TO_CHANGE_GREEN_STATE = 20;
    private static final int MIN_STEPS_TO_CHANGE_YELLOW_STATE = 5;
    private static final int MIN_STEPS_TO_CHANGE_RED_STATE = 10;
    private static final int GREEN_DELAY_STEPS = 5;

    private int id;
    private Edge incomingEdge;
    private TrafficLightState state;
    private TrafficLightState nextState;
    private int stepsSinceStageChange;
    private int greenDelayCounter;
    private Set<TrafficLight> dependentTrafficLights;

    public TrafficLight(int id, Edge incomingEdge) {
        this.id = id;
        this.incomingEdge = incomingEdge;
        this.state = TrafficLightState.RED;
        this.stepsSinceStageChange = 100;
        this.greenDelayCounter = 0;
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
        System.out.println(this.toString() + state);
        if (state != this.state) {
            if (this.state == TrafficLightState.YELLOW && this.stepsSinceStageChange >= MIN_STEPS_TO_CHANGE_YELLOW_STATE) {
                this.state = this.nextState;
                this.stepsSinceStageChange = 0;
            }
            else if (allowsChange()) {
                this.greenDelayCounter++;
                if (this.state == TrafficLightState.GREEN || this.greenDelayCounter > GREEN_DELAY_STEPS) {
                    this.state = TrafficLightState.YELLOW;
                    this.nextState = state;
                    this.stepsSinceStageChange = 0;
                    this.greenDelayCounter = 0;
                }
            }
        }
    }

    public boolean allowsChange() {
        if (this.state == TrafficLightState.GREEN) return stepsSinceStageChange > MIN_STEPS_TO_CHANGE_GREEN_STATE;
        else if (this.state == TrafficLightState.RED) return stepsSinceStageChange > MIN_STEPS_TO_CHANGE_RED_STATE;
        return false;
    }

    public boolean allowsTraffic() {
        return this.state == TrafficLightState.GREEN;
    }

    public int calculateCarsOverTimeFactor() {
        this.stepsSinceStageChange++;

        return stepsSinceStageChange + 10 * calculateApproachingDriversCount();
    }

    private int calculateApproachingDriversCount() {
        return  incomingEdge.getDrivers().size();
    }

    @Override
    public String toString() {
        return "TrafficLight{" +
                id +
                ", state=" + state +
                ", nextState=" + nextState +
                ", stepsSinceStageChange=" + stepsSinceStageChange +
                '}';
    }
}
