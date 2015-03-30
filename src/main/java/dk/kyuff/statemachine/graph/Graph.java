package dk.kyuff.statemachine.graph;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
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

    /**
     * Double list where each coordinate indicates an Edge.
     * (3,1) != null indicates an Edge from Enum.ordinal() = 3 to Enum.ordinal() = 1.
     */
    private final List<List<Edge<E>>> matrix;

    public Graph(Class<V> enumClass) {
        this.enumClass = enumClass;
        int size = enumClass.getEnumConstants().length;
        matrix = list(size, () -> list(size, Edge::new));
    }

    private <T> List<T> list(int size, Supplier<T> supplier) {
        List<T> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            result.add(supplier.get());
        }
        return result;
    }


    public E getEdge(V v, V w) {
        Edge<E> edge = getEdgeHolder(v, w);
        if (edge.isDefined()) {
            return edge.getValue();
        } else {
            return null;
        }
    }

    public boolean hasEdge(V v, V w) {
        return getEdgeHolder(v, w).isDefined();
    }

    private Edge<E> getEdgeHolder(V v, V w) {
        return matrix.get(v.ordinal()).get(w.ordinal());
    }


    public Set<E> getEdges(V v) {
        return matrix.get(v.ordinal()).stream()
                .filter(Edge::isDefined)
                .map(Edge::getValue)
                .collect(Collectors.toSet());
    }

    public void addEdge(V v, V w, E edgeValue) {
        Edge edge = getEdgeHolder(v, w);
        edge.setValue(edgeValue);
    }

    public Set<V> getAdjacent(V v) {
        EnumSet<V> result = EnumSet.noneOf(enumClass);
        List<Edge<E>> edges = matrix.get(v.ordinal());
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).isDefined()) {
                result.add(enumClass.getEnumConstants()[i]);
            }
        }
        return result;
    }

    public boolean hasPath(V v, V w) {
        V current = v;
        Set<V> visited = EnumSet.noneOf(enumClass);
        Set<V> work = EnumSet.of(current);
        while (!work.isEmpty()) {
            if (!visited.contains(current)) {
                Set<V> adjacent = getAdjacent(current);
                if (current == w) {
                    return true;
                }
                work.addAll(adjacent);
            }
            work.remove(current);
            visited.add(current);
            if (!work.isEmpty()) {
                current = work.iterator().next();
            }
        }
        return false;
    }
}
