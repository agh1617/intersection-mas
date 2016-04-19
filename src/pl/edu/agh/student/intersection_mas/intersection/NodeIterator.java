package pl.edu.agh.student.intersection_mas.intersection;

/**
 * Created by bzurkowski on 19.04.16.
 */
public class NodeIterator {
    private Node startNode;

    private Node currentNode;

    public NodeIterator(Node startNode) {
        this.startNode = startNode;
        this.currentNode = startNode;
    }

    public boolean hasNext() {
        return !this.currentNode.getOutgoingEdges().isEmpty();
    }

    public Node next() {
        Node node = this.currentNode.getOutgoingEdges().iterator().next().getEnd();
        this.currentNode = node;
        return node;
    }
}
