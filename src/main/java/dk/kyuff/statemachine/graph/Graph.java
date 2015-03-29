package dk.kyuff.statemachine.graph;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An Adjacent List Directed Graph implementation
 *
 * @param <V> data type stored in a Vertex
 * @param <E> data type stored in an Edge
 */
@SuppressWarnings("unchecked")
public class Graph<V extends Enum<V>, E> {

    private final Class<V> enumClass;

    private static class Edge<E> {

        E value;

        public E getValue() {
            return value;
        }

    }

    /**
     * Double list where each coordinate indicates an Edge.
     * (3,1) != null indicates an Edge from Enum.ordinal() = 3 to Enum.ordinal() = 1.
     */
    private final List<List<Edge<E>>> matrix;

    public Graph(Class<V> enumClass) {
        this.enumClass = enumClass;
        matrix = new ArrayList<>();
        int size = enumClass.getEnumConstants().length;
        for (int i = 0; i < size; i++) {
            List<Edge<E>> edges = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                edges.add(null);
            }
            matrix.add(edges);
        }
    }

    public E getEdge(V v, V w) {
        Edge<E> edge = matrix.get(v.ordinal()).get(w.ordinal());
        if (edge != null) {
            return edge.value;
        } else {
            return null;
        }
    }


    public Set<E> getEdges(V v) {
        return matrix.get(v.ordinal()).stream()
                .filter(Objects::nonNull)
                .map(Edge::getValue)
                .collect(Collectors.toSet());
    }

    public void addEdge(V v, V w, E edgeValue) {
        Edge edge = new Edge();
        edge.value = edgeValue;
        matrix.get(v.ordinal()).set(w.ordinal(), edge);
    }

    public Set<V> getAdjacent(V v) {
        EnumSet<V> result = EnumSet.noneOf(enumClass);
        List<Edge<E>> edges = matrix.get(v.ordinal());
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i) != null) {
                result.add(enumClass.getEnumConstants()[i]);
            }
        }
        return result;
    }


}
