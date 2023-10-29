package com.putoet.graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeightedEdgeTest {
    private final WeightedEdge edge = new WeightedEdge(3, 7, 13.0);

    @Test
    void reversed() {
        final WeightedEdge reverse = edge.reversed();
        assertEquals(edge.u, reverse.v);
        assertEquals(edge.v, reverse.u);
        assertEquals(edge.weight, reverse.weight);
    }

    @Test
    void compareTo() {
        assertThrows(AssertionError.class, () -> edge.compareTo(null));

        assertEquals(1, edge.compareTo(new WeightedEdge(1, 2, 1.0)));
        assertEquals(0, edge.compareTo(new WeightedEdge(1, 2, 13.0)));
        assertEquals(-1, edge.compareTo(new WeightedEdge(1, 2, 20.0)));
    }
}