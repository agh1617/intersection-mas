package pl.edu.agh.student.intersection_mas.intersection;

/**
 * Created by bzurkowski on 19.04.16.
 */
public class Edge {
    private int id;

    private Node start;

    private Node end;

    public Edge(int id, Node start, Node end) {
        this.id = id;
        this.start = start;
        this.end = end;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Node getStart() {
        return start;
    }

    public void setStart(Node start) {
        this.start = start;
    }

    public Node getEnd() {
        return end;
    }

    public void setEnd(Node end) {
        this.end = end;
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
