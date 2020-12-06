package com.putoet.graph;

import com.putoet.search.GenericSearch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.putoet.search.GenericSearch.Node;

import static org.junit.jupiter.api.Assertions.*;

class UnweightedGraphTest {
    public static final List<String> CITIES = List.of(
            "Seattle",
            "San Francisco",
            "Los Angeles",
            "Riverside",
            "Phoenix",
            "Chicago",
            "Boston",
            "New York",
            "Atlanta",
            "Miami",
            "Dallas",
            "Houston",
            "Detroit",
            "Philadelphia",
            "Washington"
    );
    private UnweightedGraph<String> graph;

    @BeforeEach
    void setup() {
        graph = new UnweightedGraph<>(CITIES);
        graph.addEdge("Seattle", "Chicago");
        graph.addEdge("Seattle", "San Francisco");
        graph.addEdge("San Francisco", "Riverside");
        graph.addEdge("San Francisco", "Los Angeles");
        graph.addEdge("Los Angeles", "Riverside");
        graph.addEdge("Los Angeles", "Phoenix");
        graph.addEdge("Riverside", "Phoenix");
        graph.addEdge("Riverside", "Chicago");
        graph.addEdge("Phoenix", "Dallas");
        graph.addEdge("Phoenix", "Houston");
        graph.addEdge("Dallas", "Chicago");
        graph.addEdge("Dallas", "Atlanta");
        graph.addEdge("Dallas", "Houston");
        graph.addEdge("Houston", "Atlanta");
        graph.addEdge("Houston", "Miami");
        graph.addEdge("Atlanta", "Chicago");
        graph.addEdge("Atlanta", "Washington");
        graph.addEdge("Atlanta", "Miami");
        graph.addEdge("Miami", "Washington");
        graph.addEdge("Chicago", "Detroit");
        graph.addEdge("Detroit", "Boston");
        graph.addEdge("Detroit", "Washington");
        graph.addEdge("Detroit", "New York");
        graph.addEdge("Boston", "New York");
        graph.addEdge("New York", "Philadelphia");
        graph.addEdge("Philadelphia", "Washington");

        // Doubles must not create additional edges
        graph.addEdge("Dallas", "Chicago");
        graph.addEdge("Detroit", "Boston");
        graph.addEdge("Philadelphia", "Washington");
    }

    @Test
    void vertexCount() {
        assertEquals(CITIES.size(), graph.vertexCount());
    }

    @Test
    void edgeCount() {
        assertEquals(52, graph.edgeCount());
    }

    @Test
    void addVertex() {
        final String city = "Amsterdam";
        graph.addVertex(city);
        assertEquals(CITIES.size() + 1, graph.vertexCount());
        assertEquals(city, graph.vertexAt(graph.vertexCount() - 1));
    }

    @Test
    void indexOf() {
        assertEquals(8, graph.indexOf("Atlanta"));
    }

    @Test
    void neighboursOf() {
        assertEquals(List.of("Dallas", "Houston", "Chicago", "Washington", "Miami"), graph.neighboursOf("Atlanta"));
        System.out.println(graph.neighboursOf("Atlanta"));
    }

    @Test
    void edgesOf() {
        assertEquals(5, graph.edgesOf("Atlanta").size());
    }

    @Test
    void bfs() {
        final Optional<Node<String>> result =
                GenericSearch.bfs("Boston", v -> v.equals("Miami"), graph::neighboursOf);
        assertTrue(result.isPresent());

        final List<String> path = result.get().path();
        assertEquals("Boston", path.get(0));
        assertEquals("Miami", path.get(path.size() - 1));
    }

    @Test
    void contains() {
        final UnweightedGraph<String> graph = new UnweightedGraph<>();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("D");

        assertFalse(graph.contains("C"));
        assertTrue(graph.contains("B"));
    }
}