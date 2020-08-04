package com.putoet.graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {
    private final Edge edge = new Edge(3, 7);

    @Test
    void reversed() {
        assertEquals(7, edge.reversed().u);
        assertEquals(3, edge.reversed().v);
    }

    @Test
    void testToString() {
        assertEquals("3 -> 7", edge.toString());
    }
}