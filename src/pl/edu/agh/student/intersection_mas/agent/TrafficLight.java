package pl.edu.agh.student.intersection_mas.agent;

import pl.edu.agh.student.intersection_mas.intersection.Edge;
import pl.edu.agh.student.intersection_mas.simulation.SimulationProperties;

import java.util.Set;

/**
 * Created by maciek on 10.05.16.
 */
public class TrafficLight {
    private int minStepsToChangeGreenState;
    private int minStepsToChangeYellowState;
    private int minStepsToChangeRedState;
    private int greenDelaySteps;
    private int waitingDriversWeight;

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

        readConfig();
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
//        System.out.println(this.toString() + state);
        if (state != this.state) {
            if (this.state == TrafficLightState.YELLOW && this.stepsSinceStageChange >= minStepsToChangeYellowState) {
                this.state = this.nextState;
                this.stepsSinceStageChange = 0;
            }
            else if (allowsChange()) {
                this.greenDelayCounter++;
                if (this.state == TrafficLightState.GREEN || this.greenDelayCounter > greenDelaySteps) {
                    this.state = TrafficLightState.YELLOW;
                    this.nextState = state;
                    this.stepsSinceStageChange = 0;
                    this.greenDelayCounter = 0;
                }
            }
        }
    }

    public boolean allowsChange() {
        if (this.state == TrafficLightState.GREEN) return stepsSinceStageChange > minStepsToChangeGreenState;
        else if (this.state == TrafficLightState.RED) return stepsSinceStageChange > minStepsToChangeRedState;
        return false;
    }

    public boolean allowsTraffic() {
        return this.state == TrafficLightState.GREEN;
    }

    public int calculateCarsOverTimeFactor() {
        this.stepsSinceStageChange++;

        return stepsSinceStageChange + waitingDriversWeight * calculateApproachingDriversCount();
    }

    private int calculateApproachingDriversCount() {
        return  incomingEdge.getDrivers().size();
    }

    private void readConfig() {
        SimulationProperties properties = SimulationProperties.getInstance();
        minStepsToChangeGreenState = Integer.parseInt(properties.get("minStepsToChangeGreenState"));
        minStepsToChangeYellowState = Integer.parseInt(properties.get("minStepsToChangeYellowState"));
        minStepsToChangeRedState = Integer.parseInt(properties.get("minStepsToChangeRedState"));
        greenDelaySteps = Integer.parseInt(properties.get("greenDelaySteps"));
        waitingDriversWeight = Integer.parseInt(properties.get("waitingDriversWeight"));
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
