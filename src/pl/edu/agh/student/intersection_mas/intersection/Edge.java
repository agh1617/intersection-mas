package pl.edu.agh.student.intersection_mas.intersection;

import pl.edu.agh.student.intersection_mas.agent.Driver;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by bzurkowski on 19.04.16.
 */
public class Edge {
    private int id;

    private Node start;

    private Node end;

    private float length;

    private Set<Driver> drivers;

    public Edge(int id, Node start, Node end) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.length = start.distanceTo(end);

        this.drivers = new HashSet<Driver>();

        start.addOutgoingEdge(this);
        end.addIncomingEdge(this);
    }

    public synchronized void addDriver(Driver driver) {
        this.drivers.add(driver);
    }

    public synchronized void removeDriver(Driver driver) {
        this.drivers.remove(driver);
    }

    public Set<Driver> getDrivers() {
        return drivers;
    }

    public synchronized Set<Driver> getDriversInSegment(int start, int length) {
        // TODO: Improve concurrency

        Set<Driver> driversInSegment = new HashSet<Driver>();
        int driverPosition;

        int end = (int) Math.min(start + length, this.length);

        Iterator<Driver> driversIterator = drivers.iterator();
        Driver driver;

        while (driversIterator.hasNext()) {
            driver = driversIterator.next();
            driverPosition = driver.getPosition().getPosition();

            if (driverPosition > start && driverPosition <= end) {
                driversInSegment.add(driver);
            }
        }

        return driversInSegment;
    }

    public Node getEnd() {
        return end;
    }

    public float getLength() {
        return length;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "pl.edu.agh.student.intersection_mas.intersection.Edge{" +
                "id=" + id +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
