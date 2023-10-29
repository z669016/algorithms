package com.putoet.heap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MinHeapSetTest {
    private MinHeapSet<Integer, String> heap;

    @BeforeEach
    void setup() {
        heap = new MinHeapSet<>();
    }

    @Test
    void top() {
        assertEquals(0, heap.size());
        assertTrue(heap.isEmpty());
        assertThrows(IllegalStateException.class, () -> heap.top());

        heap = testHeap();
        assertEquals("one", heap.top());
        assertEquals("two", heap.top());
        assertEquals("three", heap.top());
        assertEquals("four", heap.top());
        assertEquals("five", heap.top());
        assertEquals("six", heap.top());
        assertEquals("seven", heap.top());
        assertEquals("eight", heap.top());
        assertEquals("nine", heap.top());
        assertEquals("ten", heap.top());

        assertThrows(IllegalStateException.class, () -> heap.top());
    }

    @Test
    void peek() {
        assertThrows(IllegalStateException.class, () -> heap.peek());

        heap.insert(3, "three");
        heap.insert(2, "two");
        heap.insert(1, "one");

        assertEquals("one", heap.peek());
        heap.top();
        assertEquals("two", heap.peek());
        heap.top();
        assertEquals("three", heap.peek());
        heap.top();

        assertThrows(IllegalStateException.class, () -> heap.peek());
    }

    @Test
    void remove() {
        heap = testHeap();
        heap.remove("one");
        heap.remove("three");
        heap.remove("five");
        heap.remove("seven");
        heap.remove("ten");

        assertEquals("two", heap.top());
        assertEquals("four", heap.top());
        assertEquals("six", heap.top());
        assertEquals("eight", heap.top());
        assertEquals("nine", heap.top());
    }

    @Test
    void update() {
        heap = testHeap();
        heap.update("five", 0);
        heap.update("ten", 5);

        assertEquals("five", heap.top());
        assertEquals("one", heap.top());
        assertEquals("two", heap.top());
        assertEquals("three", heap.top());
        assertEquals("four", heap.top());
        assertEquals("ten", heap.top());
        assertEquals("six", heap.top());
        assertEquals("seven", heap.top());
        assertEquals("eight", heap.top());
        assertEquals("nine", heap.top());

    }

    private static MinHeapSet<Integer, String> testHeap() {
        final var heap = new MinHeapSet<Integer, String>();

        heap.insert(7, "seven");
        heap.insert(2, "two");
        heap.insert(6, "six");
        heap.insert(3, "three");
        heap.insert(4, "four");
        heap.insert(10, "ten");
        heap.insert(5, "five");
        heap.insert(8, "eight");
        heap.insert(1, "one");
        heap.insert(9, "nine");

        return heap;
    }
}