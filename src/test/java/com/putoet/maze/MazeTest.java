package com.putoet.maze;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MazeTest {
    @Test
    void createMazeLocation() {
        assertThrows(AssertionError.class, () -> Maze.Location.of(-1, 0));
        assertThrows(AssertionError.class, () -> Maze.Location.of(0, -1));

        assertEquals("(3,7)", Maze.Location.of(7, 3).toString());
    }

    @Test
    void manhattanDistance() {
        assertEquals(8.0, Maze.manhattanDistance(Maze.Location.of(1, 3), Maze.Location.of(5, 7)));
    }

    @Test
    void checkLocation() {
        assertThrows(IllegalArgumentException.class, () -> Maze.checkLocation(Maze.Location.of(3,0), 3, 7));
        assertThrows(IllegalArgumentException.class, () -> Maze.checkLocation(Maze.Location.of(0,7), 3, 7));
    }
}