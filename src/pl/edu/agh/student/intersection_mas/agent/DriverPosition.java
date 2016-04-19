package pl.edu.agh.student.intersection_mas.agent;

import pl.edu.agh.student.intersection_mas.intersection.Edge;

/**
 * Created by bzurkowski on 19.04.16.
 */
public class DriverPosition {
    private Edge edge;

    private int position;

    public DriverPosition(Edge edge, int position) {
        this.edge = edge;
        this.position = position;
    }

    public Edge getEdge() {
        return edge;
    }

    public int getPosition() {
        return position;
    }

    public void set(Edge edge, int position) {
        this.edge = edge;
        this.position = position;
    }

    @Override
    public String toString() {
        return "DriverPosition{" +
                "edge=" + edge +
                ", position=" + position +
                '}';
    }
}
