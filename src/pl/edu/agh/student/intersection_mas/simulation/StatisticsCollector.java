package pl.edu.agh.student.intersection_mas.simulation;

import pl.edu.agh.student.intersection_mas.agent.Driver;
import pl.edu.agh.student.intersection_mas.intersection.Edge;
import pl.edu.agh.student.intersection_mas.intersection.Intersection;
import pl.edu.agh.student.intersection_mas.intersection.Node;

import java.util.HashSet;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * Created by maciek on 16.06.16.
 */
public class StatisticsCollector {
    private Intersection intersection;
    private HashSet<Node> nodes;
    private int simulationStep;
    private int simulationNumber;
    private int driversNumber;
    private float globalSpeedAverage;
    private Logger logger;
    private Logger globalLogger;

    public StatisticsCollector(Intersection intersection) {
        this.intersection = intersection;
        this.nodes = new HashSet<Node>(intersection.getNodes());

        this.logger = Logger.getLogger("statistics");
        logger.info("simulation_number,drivers_number,simulation_step,global_speed_avg,step_speed_avg");
        this.globalLogger = Logger.getLogger("global_statistics");

        this.simulationStep = 0;
        this.globalSpeedAverage = 0;

        SimulationProperties simulationProperties = SimulationProperties.getInstance();
        simulationNumber = Integer.parseInt(simulationProperties.get("simulationNumber"));
        driversNumber = Integer.parseInt(simulationProperties.get("driversNumber"));
    }

    public void update() {
        this.simulationStep++;

        float stepSpeedAverage = computeStepSpeedAverage();

        this.globalSpeedAverage = (this.globalSpeedAverage * (this.simulationStep - 1) + stepSpeedAverage) / this.simulationStep;

        logger.info(String.format(Locale.US, "0,%d,%d,%f,%f", driversNumber, simulationStep, this.globalSpeedAverage, stepSpeedAverage));
        globalLogger.info(String.format(Locale.US, "%d,%d,%d,%f,%f", simulationNumber, driversNumber, simulationStep, this.globalSpeedAverage, stepSpeedAverage));
        System.out.println("Avg speed in current step:\t" + stepSpeedAverage);
        System.out.println("Avg speed so far:\t" + this.globalSpeedAverage);
    }

    private float computeStepSpeedAverage() {
        int driversSpeed = 0;
        int driversCount = 0;

        for (Node startNode : nodes)
            for (Edge edge : startNode.getOutgoingEdges())
                for (Driver driver : edge.getDrivers())
                    if (driver.isDriving()) {
                        driversSpeed += driver.getSpeed();
                        driversCount++;
                    }
        if (driversCount == 0) return 0;
        return (float) driversSpeed / driversCount;
    }
}
