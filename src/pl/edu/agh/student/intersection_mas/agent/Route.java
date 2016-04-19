package pl.edu.agh.student.intersection_mas.agent;

import pl.edu.agh.student.intersection_mas.intersection.Node;

import java.util.List;


/**
 * Created by bzurkowski on 19.04.16.
 */
public class Route {
    private List nodes;

    private Node start;

    private Node end;

    public Route(List nodes) {
        this.nodes = nodes;
    }

    public Route(Node start, Node end) {
        this.start = start;
        this.end = end;
    }

    public void calculate() {

    }
}
