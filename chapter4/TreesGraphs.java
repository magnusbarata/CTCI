import java.util.ArrayList;

class Graph<T> {
    private ArrayList<GraphNode<T>> vertices = new ArrayList<>();

    public void addVertex(GraphNode<T> v) { vertices.add(v); }
    public ArrayList<GraphNode<T>> getVertices() { return vertices; }

    /* 4-1 */
    private void setStateUnvisited() {
        for (GraphNode<T> n: vertices) n.state = NodeState.unvisited;
    }

    public boolean DFS(GraphNode<T> start, GraphNode<T> target) {
        setStateUnvisited();
        ArrayList<GraphNode<T>> stack = new ArrayList<>(); // Use array list as stack
        stack.add(start);
        while (!stack.isEmpty()) {
            GraphNode<T> current = stack.remove(stack.size() - 1);
            if (current != null && current.state == NodeState.unvisited) {
                if (current == target) return true;
                stack.addAll(current.getAdjacent());
                current.state = NodeState.visited;
            }
        }
        return false;
    }
    
    public boolean BFS(GraphNode<T> start, GraphNode<T> target) {
        setStateUnvisited();
        ArrayList<GraphNode<T>> queue = new ArrayList<>(); // Use array list as queue
        queue.add(start);
        while (!queue.isEmpty()) {
            GraphNode<T> current = queue.remove(0);
            if (current != null && current.state == NodeState.unvisited) {
                if (current == target) return true;
                queue.addAll(current.getAdjacent());
                current.state = NodeState.visited;
            }
        }
        return false;
    }
}

public class TreesGraphs {
    static final String ANSI_RESET = "\u001B[0m";
    static final String ANSI_GREEN = "\u001B[32m";
    
    @SuppressWarnings("unchecked")
    private static void check1() {
        GraphNode<String>[] nodes = new GraphNode[]{
            new GraphNode<>("a"),
            new GraphNode<>("b"),
            new GraphNode<>("c"),
            new GraphNode<>("d"),
            new GraphNode<>("e"),
            new GraphNode<>("f"),
        };
        nodes[0].addAdjacent(nodes[1]);
        nodes[0].addAdjacent(nodes[2]);
        nodes[0].addAdjacent(nodes[3]);
        nodes[3].addAdjacent(nodes[4]);
        nodes[4].addAdjacent(nodes[5]);
        nodes[5].addAdjacent(nodes[0]);

        Graph<String> g = new Graph<>();
        for (int i = 0; i < 6; i++) g.addVertex(nodes[i]);
        assert g.BFS(nodes[3], nodes[5]) : "(BFS) There is a route between node 3 and 5";
        assert g.BFS(nodes[5], nodes[1]) : "(BFS) There is a route between node 5 and 1";
        assert !g.BFS(nodes[1], nodes[5]) : "(BFS) There is no route between node 1 and 5";
        assert g.DFS(nodes[3], nodes[5]) : "(DFS) There is a route between node 3 and 5";
        assert g.DFS(nodes[5], nodes[1]) : "(DFS) There is a route between node 5 and 1";
        assert !g.DFS(nodes[1], nodes[5]) : "(DFS) There is no route between node 1 and 5";
    }

    private static void check2() {
        Integer[] arr = {12, 15, 31, 45, 52, 61, 67, 68, 79, 80, 81, 92};
        BST<Integer> minHeightBST = BST.createMinHeightBST(arr);
        assert minHeightBST.data == 61 : minHeightBST.data + " minHeightBST root must be mid value 61";
        assert BST.checkBST(minHeightBST) : "minHeightBST must be a binary search tree";
        assert minHeightBST.height() == 4 : minHeightBST.height() + " (Minimum height check) minHeightBST height must be 4";
        arr = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        minHeightBST = BST.createMinHeightBST(arr);
        assert minHeightBST.data == 6 : minHeightBST.data + " minHeightBST root must be mid value 6";
        assert BST.checkBST(minHeightBST) : "minHeightBST must be a binary search tree";
        assert minHeightBST.height() == 4 : minHeightBST.height() + " (Minimum height check) minHeightBST height must be 4";

    }
 
    public static void main(String[] args) {
        System.out.print("4-1: ");
        check1();
        System.out.println(ANSI_GREEN + "OK!" + ANSI_RESET);

        System.out.print("4-2: ");
        check2();
        System.out.println(ANSI_GREEN + "OK!" + ANSI_RESET);
    }
}
