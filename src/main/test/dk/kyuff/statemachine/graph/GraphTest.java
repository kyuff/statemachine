package dk.kyuff.statemachine.graph;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GraphTest {

    Graph<String, String> graph;

    @Before
    public void setUp() throws Exception {
        graph = new Graph<>();
    }

    @Test
    public void testAddVertex() throws Exception {
        // setup

        // execute
        graph.addVertex("vertex");

        // verify
        assertTrue(graph.isVertex("vertex"));
        assertFalse(graph.isVertex("other"));
        assertEquals(1, graph.getVertexCount());
    }

    @Test
    public void testAddEdge() throws Exception {
        // setup
        graph.addVertex("v1");
        graph.addVertex("v2");
        graph.addVertex("v3");

        // execute
        graph.addEdge("v1", "v2", "Edge");

        // verify
        assertTrue(graph.hasEdge("v1", "v2"));
        assertFalse(graph.hasEdge("v1", "v3"));
        assertFalse(graph.hasEdge("v2", "v3"));

    }

    @Test
    public void testGetEdge() throws Exception {
        // setup
        graph.addVertex("v1");
        graph.addVertex("v2");
        graph.addVertex("v3");
        graph.addEdge("v1", "v2", "edge");

        // execute
        String edge = graph.getEdge("v1", "v2");

        // verify
        assertEquals("edge", edge);
    }

}