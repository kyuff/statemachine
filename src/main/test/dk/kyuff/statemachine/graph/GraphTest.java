package dk.kyuff.statemachine.graph;

import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class GraphTest {

    Graph<State, String> graph;

    @Before
    public void setUp() throws Exception {
        graph = new Graph<>(State.class);
    }

    @Test
    public void testHasPath() {
        // setup
        graph.addEdge(State.INITIAL, State.WORKING, "e1");
        graph.addEdge(State.WORKING, State.TESTING, "e2");
        graph.addEdge(State.TESTING, State.INITIAL, "loop");
        graph.addEdge(State.TESTING, State.DONE, "e3");

        // execute
        boolean hasPath = graph.hasPath(State.INITIAL, State.DONE);

        // verify
        assertTrue(hasPath);
    }


    @Test
    public void testHasPathNo() {
        // setup
        graph.addEdge(State.INITIAL, State.WORKING, "e1");
        graph.addEdge(State.WORKING, State.TESTING, "e2");
        graph.addEdge(State.TESTING, State.WORKING, "loop");
        graph.addEdge(State.TESTING, State.DONE, "e3");
        graph.addEdge(State.WORKING, State.DONE, "e4");

        // execute
        boolean hasPath = graph.hasPath(State.WORKING, State.INITIAL);

        // verify
        assertFalse(hasPath);
    }

    @Test
    public void testGetEdge() throws Exception {
        // setup
        graph.addEdge(State.INITIAL, State.WORKING, "edge value");

        // execute
        String edge = graph.getEdge(State.INITIAL, State.WORKING);

        // verify
        assertEquals("edge value", edge);
    }

    @Test
    public void testGetEdgeNull() throws Exception {
        // setup
        graph.addEdge(State.INITIAL, State.WORKING, "edge value");

        // execute
        String edge = graph.getEdge(State.DONE, State.WORKING);

        // verify
        assertNull(edge);
    }

    @Test
    public void testGetEdges() throws Exception {
        // setup
        graph.addEdge(State.INITIAL, State.IMPEDED, "initial to impeded");
        graph.addEdge(State.INITIAL, State.WORKING, "initial to working");
        graph.addEdge(State.IMPEDED, State.WORKING, "impeded to working");

        // execute
        Set<String> edges = graph.getEdges(State.INITIAL);

        // verify
        assertEquals(2, edges.size());
        assertTrue(edges.contains("initial to impeded"));
        assertTrue(edges.contains("initial to working"));
    }


    @Test
    public void testGetEdgesNull() throws Exception {
        // setup
        graph.addEdge(State.INITIAL, State.IMPEDED, "initial to impeded");
        graph.addEdge(State.INITIAL, State.WORKING, "initial to working");
        graph.addEdge(State.IMPEDED, State.WORKING, "impeded to working");

        // execute
        Set<String> edges = graph.getEdges(State.DONE);

        // verify
        assertNotNull(edges);
        assertEquals(0, edges.size());
    }

    @Test
    public void testGetEdgesNullValue() throws Exception {
        // setup
        graph.addEdge(State.INITIAL, State.IMPEDED, null);
        graph.addEdge(State.INITIAL, State.WORKING, null);
        graph.addEdge(State.IMPEDED, State.WORKING, "impeded to working");

        // execute
        Set<String> edges = graph.getEdges(State.INITIAL);

        // verify
        assertNotNull(edges);
        assertEquals(1, edges.size()); // Sets only holds one null value
    }

    @Test
    public void testGetAdjacent() throws Exception {
        // setup
        graph.addEdge(State.INITIAL, State.IMPEDED, "initial to impeded");
        graph.addEdge(State.INITIAL, State.WORKING, "initial to working");
        graph.addEdge(State.IMPEDED, State.DONE, "impeded to done");

        // execute
        Set<State> adjacent = graph.getAdjacent(State.INITIAL);

        // verify
        assertEquals(2, adjacent.size());
        assertTrue(adjacent.contains(State.IMPEDED));
        assertTrue(adjacent.contains(State.WORKING));
    }

    @Test
    public void testGetAdjacentNull() throws Exception {
        // setup
        graph.addEdge(State.INITIAL, State.IMPEDED, "initial to impeded");
        graph.addEdge(State.INITIAL, State.WORKING, "initial to working");
        graph.addEdge(State.IMPEDED, State.DONE, "impeded to done");

        // execute
        Set<State> adjacent = graph.getAdjacent(State.DONE);

        // verify
        assertNotNull(adjacent);
        assertEquals(0, adjacent.size());
    }

    @Test
    public void testHasEdge() throws Exception {
        // setup
        graph.addEdge(State.INITIAL, State.IMPEDED, "initial to impeded");
        graph.addEdge(State.INITIAL, State.WORKING, "initial to working");
        graph.addEdge(State.IMPEDED, State.DONE, "impeded to done");

        // execute and verify
        assertTrue(graph.hasEdge(State.INITIAL, State.IMPEDED));
        assertTrue(graph.hasEdge(State.INITIAL, State.WORKING));
        assertTrue(graph.hasEdge(State.IMPEDED, State.DONE));

        assertFalse(graph.hasEdge(State.IMPEDED, State.INITIAL));
        assertFalse(graph.hasEdge(State.IMPEDED, State.WORKING));
        assertFalse(graph.hasEdge(State.DONE, State.IMPEDED));
        assertFalse(graph.hasEdge(State.INITIAL, State.INITIAL));

    }
}