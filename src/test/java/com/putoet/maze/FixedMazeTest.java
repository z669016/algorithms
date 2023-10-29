package com.putoet.maze;

import com.putoet.search.GenericSearch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.putoet.search.GenericSearch.dfs;

import static org.junit.jupiter.api.Assertions.*;

class FixedMazeTest {
    private FixedMaze maze;
    private final List<String> mazeText = List.of(
            "###########",
            "#S........#",
            "#.#######.#",
            "#........G#",
            "###########"
    );

    @BeforeEach
    void setup() {
        maze = new FixedMaze(mazeText);
    }

    @Test
    void create() {
        assertThrows(AssertionError.class, () -> new FixedMaze(null));
        assertThrows(AssertionError.class, () -> new FixedMaze(List.of()));
        assertThrows(IllegalArgumentException.class, () -> new FixedMaze(List.of("##", "###")));
    }

    @Test
    void solve() {
        final Optional<GenericSearch.Node<Maze.Location>> result =
                dfs(Maze.Location.of(1, 1), maze::goalTest, maze::successors);
        assertTrue(result.isPresent());
        assertEquals(Maze.Location.of(3, 9), result.get().state);
    }
}