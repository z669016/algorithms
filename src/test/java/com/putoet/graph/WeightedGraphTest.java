package com.putoet.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WeightedGraphTest {
    private WeightedGraph<String> graph;

    @BeforeEach
    void setup() {
        graph = new WeightedGraph<>(UnweightedGraphTest.CITIES);
        graph.addEdge("Seattle", "Chicago",1737);
        graph.addEdge("Seattle", "San Francisco",678);
        graph.addEdge("San Francisco", "Riverside",386);
        graph.addEdge("San Francisco", "Los Angeles", 348);
        graph.addEdge("Los Angeles", "Riverside",50);
        graph.addEdge("Los Angeles", "Phoenix",357);
        graph.addEdge("Riverside", "Phoenix", 307);
        graph.addEdge("Riverside", "Chicago",1704);
        graph.addEdge("Phoenix", "Dallas",887);
        graph.addEdge("Phoenix", "Houston",1015);
        graph.addEdge("Dallas", "Chicago", 805);
        graph.addEdge("Dallas", "Atlanta", 721);
        graph.addEdge("Dallas", "Houston", 225);
        graph.addEdge("Houston", "Atlanta", 702);
        graph.addEdge("Houston", "Miami",968);
        graph.addEdge("Atlanta", "Chicago",588);
        graph.addEdge("Atlanta", "Washington",543);
        graph.addEdge("Atlanta", "Miami", 604);
        graph.addEdge("Miami", "Washington", 923);
        graph.addEdge("Chicago", "Detroit", 238);
        graph.addEdge("Detroit", "Boston",613);
        graph.addEdge("Detroit", "Washington",396);
        graph.addEdge("Detroit", "New York",482);
        graph.addEdge("Boston", "New York",190);
        graph.addEdge("New York", "Philadelphia", 81);
        graph.addEdge("Philadelphia", "Washington", 123);
    }

    @Test
    void mst() {
        final List<WeightedEdge> mst = graph.mst("Seattle");
        assertEquals(UnweightedGraphTest.CITIES.size() - 1, mst.size());
        assertEquals(5372.0, WeightedGraph.totalWeight(mst));
        System.out.println(graph.toString(mst));
    }

    @Test
    void dijkstra() {
        final WeightedGraph.DijkstraResult result = graph.dijkstra("Los Angeles");
        final Map<String, Double> distances = graph.distancesMap(result.distances());

        assertEquals(2474.0, distances.get("New York"));
        assertEquals(1992.0, distances.get("Detroit"));
        assertEquals(1026.0, distances.get("Seattle"));
        assertEquals(1754.0, distances.get("Chicago"));
        assertEquals(2388.0, distances.get("Washington"));
        assertEquals(2340.0, distances.get("Miami"));
        assertEquals(348.0, distances.get("San Francisco"));
        assertEquals(1965.0, distances.get("Atlanta"));
        assertEquals(357.0, distances.get("Phoenix"));
        assertEquals(0.0, distances.get("Los Angeles"));
        assertEquals(1244.0, distances.get("Dallas"));
        assertEquals(2511.0, distances.get("Philadelphia"));
        assertEquals(50.0, distances.get("Riverside"));
        assertEquals(2605.0, distances.get("Boston"));
        assertEquals(1372, distances.get("Houston"));

        final List<WeightedEdge> path = graph.pathOf("Los Angeles", "Boston", result.pathMap());
        assertEquals(2605.0, WeightedGraph.totalWeight(path));
    }
}