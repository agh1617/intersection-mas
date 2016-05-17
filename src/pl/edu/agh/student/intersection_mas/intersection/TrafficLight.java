package pl.edu.agh.student.intersection_mas.intersection;

/**
 * Created by maciek on 10.05.16.
 */
public class TrafficLight {
    private Edge incomingEdge;
    private TrafficLightState state;

    public TrafficLight(Edge incommingEdge) {
        this.incomingEdge = incommingEdge;
        this.state = TrafficLightState.RED;
    }

    public Edge getIncomingEdge() {
        return incomingEdge;
    }

    public TrafficLightState getState() {
        return state;
    }

    public void changeState() {
        if (state == TrafficLightState.GREEN)
            state = TrafficLightState.RED;
        else
            state = TrafficLightState.GREEN;
    }
}
