package pl.edu.agh.student.intersection_mas.agent;

/**
 * Created by maciek on 19.04.16.
 */

public class DriverMessage {
    DriverMessageType type;
    int speed;

    public DriverMessage(DriverMessageType type, int speed) {
        this.type = type;
        this.speed = speed;
    }

    public DriverMessageType getType() {
        return type;
    }

    public int getSpeed() {
        return speed;
    }
}