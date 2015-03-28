package dk.kyuff.statemachine.graph;

import java.util.*;

/**
 * An Adjacent List Directed Graph implementation
 *
 * @param <V> data type stored in a Vertex
 * @param <E> data type stored in an Edge
 */
public class Graph<V, E> {

    private final Map<V, Set<V>> vertices;

    public Graph() {
        vertices = new HashMap<>();
    }

    public int getVertexCount() {
        return vertices.keySet().size();
    }

    public int getEdgeCount(V vertex) {
        return vertices.get(vertex).size();
    }

    public E getEdge(V v, V w) {
        return null;
    }

    public boolean hasEdge(V v, V w) {
        return vertices.get(v).contains(w);
    }

    public void addEdge(V v, V w, E edge) {
        vertices.get(v).add(w);
    }

    public boolean isVertex(V vertex) {
        return vertices.containsKey(vertex);
    }

    public void addVertex(V vertex) {
        if (!isVertex(vertex)) {
            vertices.put(vertex, new TreeSet<>());
        }
    }
}
