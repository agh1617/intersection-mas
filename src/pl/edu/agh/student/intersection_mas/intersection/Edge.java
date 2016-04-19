package pl.edu.agh.student.intersection_mas.intersection;

/**
 * Created by bzurkowski on 19.04.16.
 */
public class Edge {
    private int id;

    private int length;

    private Node start;

    private Node end;

    public Edge(int id, int length, Node start, Node end) {
        this.id = id;
        this.length = length;
        this.start = start;
        this.end = end;
    }

    public int getId() {
        return id;
    }

    public int getLength() {
        return length;
    }

    public Node getStart() {
        return start;
    }

    public Node getEnd() {
        return end;
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
