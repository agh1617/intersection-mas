package pl.edu.agh.student.intersection_mas.intersection;

import pl.edu.agh.student.intersection_mas.agent.Driver;
import pl.edu.agh.student.intersection_mas.agent.DriverPosition;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bzurkowski on 19.04.16.
 */
public class IntersectionState {
    private Map<Driver, DriverPosition> driverPositions;

    public IntersectionState() {
        this.driverPositions = new HashMap<Driver, DriverPosition>();
    }

    public void addDriverPosition(Driver driver, DriverPosition driverPosition) {
        this.driverPositions.put(driver, driverPosition);
    }

    public void removeDriverPosition(Driver driver) {
        this.driverPositions.remove(driver);
    }

    public void setDriverPosition(Driver driver, DriverPosition driverPosition) {
        this.driverPositions.put(driver ,driverPosition);
    }

    public DriverPosition getDriverPosition(Driver driver) {
        return this.driverPositions.get(driver);
    }

    public Map<Driver, DriverPosition> getDriverPositions() {
        return driverPositions;
    }
}