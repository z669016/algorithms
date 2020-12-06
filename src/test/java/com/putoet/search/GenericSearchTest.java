package com.putoet.search;

import com.putoet.maze.Maze;
import com.putoet.maze.RandomMaze;
import com.putoet.search.GenericSearch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GenericSearchTest {
    private Maze.Location start;
    private Maze.Location goal;
    private RandomMaze maze;

    @BeforeEach
    void setup() {
        start = Maze.Location.of(0, 0);
        goal = Maze.Location.of(9, 9);
        maze = new RandomMaze(10, 10, start, goal, 0.2);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    void linearContains() {
        assertThrows(AssertionError.class, () -> GenericSearch.linearContains(null, 5));
        assertThrows(AssertionError.class, () -> GenericSearch.linearContains(List.of(), (String) null));

        assertTrue(GenericSearch.linearContains(List.of(1, 5, 15, 15, 15, 15, 20), 5));
    }

    @Test
    void binaryContains() {
        assertThrows(AssertionError.class, () -> GenericSearch.binaryContains(null, 5));
        assertThrows(AssertionError.class, () -> GenericSearch.binaryContains(List.of(), (String) null));

        assertTrue(GenericSearch.binaryContains(List.of("a", "d", "e", "f", "z"), "f"));
        assertFalse(GenericSearch.binaryContains(List.of("john", "mark", "ronald", "sarah"), "sheila"));
    }

    @Test
    void nodeCreate() {
        assertThrows(AssertionError.class, () -> new GenericSearch.Node<String>((String) null, null));

        final String initial = "START";
        final GenericSearch.Node<String> startNode = new GenericSearch.Node<>(initial, null);
        assertEquals(startNode.state, initial);
        assertNull(startNode.parent);
        assertEquals(0, Double.compare(0.0, startNode.cost));
        assertEquals(0, Double.compare(0.0, startNode.heuristic));

        final String greater = ">";
        final GenericSearch.Node<String> greaterNode = new GenericSearch.Node<>(greater, startNode, 11.0, 13.0);
        assertEquals(startNode, greaterNode.parent);
        assertEquals(0, Double.compare(11.0, greaterNode.cost));
        assertEquals(0, Double.compare(13.0, greaterNode.heuristic));
        assertEquals(1, greaterNode.compareTo(startNode));

        final String smaller = "<";
        final GenericSearch.Node<String> smallerNode = new GenericSearch.Node<>(smaller, startNode, 1.0, 3.0);
        assertEquals(smallerNode.state, smaller);
        assertEquals(-1, smallerNode.compareTo(greaterNode));

        final String equal = "==";
        final GenericSearch.Node<String> equalNode = new GenericSearch.Node<>(equal, startNode, 1.0, 3.0);
        assertEquals(0, equalNode.compareTo(smallerNode));
    }

    @Test
    void dfs() {
        final Optional<GenericSearch.Node<Maze.Location>> result = GenericSearch.dfs(start, maze::goalTest, maze::successors);
        assertTrue(result.isPresent());

        final List<Maze.Location> path = GenericSearch.nodeToPath(result.get());
        assertEquals(start, path.get(0));
        assertEquals(goal, path.get(path.size() - 1));
        maze.mark(path);
        System.out.println("Depth First Search");
        System.out.println(maze);
        maze.clear(path);
        assertEquals(path.size() - 1, result.get().steps());

        System.out.println("Path is " + path);
        System.out.println("Steps is " + result.get().steps());
    }

    @Test
    void bfs() {
        final Optional<GenericSearch.Node<Maze.Location>> result = GenericSearch.bfs(start, maze::goalTest, maze::successors);
        assertTrue(result.isPresent());

        final List<Maze.Location> path = GenericSearch.nodeToPath(result.get());
        assertEquals(start, path.get(0));
        assertEquals(goal, path.get(path.size() - 1));
        maze.mark(path);
        System.out.println("Broad First Search");
        System.out.println(maze);
        maze.clear(path);
        assertEquals(path.size() - 1, result.get().steps());

        System.out.println("Path is " + path);
        System.out.println("Steps is " + result.get().steps());
    }

    @Test
    void astar() {
        final Optional<GenericSearch.Node<Maze.Location>> result =
                GenericSearch.astar(start, maze::goalTest, maze::successors, maze::manhattanDistance);
        assertTrue(result.isPresent());

        final List<Maze.Location> path = GenericSearch.nodeToPath(result.get());
        assertEquals(start, path.get(0));
        assertEquals(goal, path.get(path.size() - 1));
        maze.mark(path);
        System.out.println("A* Search");
        System.out.println(maze);
        maze.clear(path);
        assertEquals(path.size() - 1, result.get().steps());

        System.out.println("Path is " + path);
        System.out.println("Steps is " + result.get().steps());
    }
}