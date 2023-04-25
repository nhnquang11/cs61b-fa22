package ngordnet.main;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Graph<N> {
    private HashMap<N, HashSet<N>> adjList;
    public Graph() {
        adjList = new HashMap<>();
    }

    /** Connect a directed edge from source to dest. */
    public void addEdge(N source, N dest) {
        if (!adjList.containsKey(source)) {
            createNode(source);
        }

        if (!adjList.containsKey(dest)) {
            createNode(dest);
        }

        adjList.get(source).add(dest);
    }

    /** Create a new node in the graph and connect to nothing. */
    public void createNode(N node) {
        adjList.put(node, new HashSet<>());
    }

    /** Return all the neighbors that the given node directs to. */
    public HashSet<N> neighbors (N node) {
        return adjList.get(node);
    }

    /** Return the set of all the nodes in the graph */
    public Set<N> getNodes() {
        return adjList.keySet();
    }
}
