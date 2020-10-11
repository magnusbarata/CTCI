import java.util.ArrayList;

public class GraphNode<T> {
    private ArrayList<GraphNode<T>> adjacent = new ArrayList<>();
    private T data;
    public NodeState state;

    public GraphNode(T d) { data = d; }
    public void addAdjacent(GraphNode<T> v) { adjacent.add(v); }
    public ArrayList<GraphNode<T>> getAdjacent() { return adjacent; }
    public T getData() { return data; }
}
