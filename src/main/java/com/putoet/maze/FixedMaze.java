package com.putoet.maze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class FixedMaze implements Maze<FixedMaze.Cell> {
    public enum Cell {
        EMPTY, BLOCKED, START, GOAL, PATH;

        @Override
        public String toString() {
            return switch (this) {
                case EMPTY -> ".";
                case BLOCKED -> "#";
                case START -> "0";
                case GOAL -> "G";
                case PATH -> "*";
            };
        }

        static Cell of(int codepoint) {
            return switch(codepoint) {
                case '.' -> EMPTY;
                case '#' -> BLOCKED;
                case 'S' -> START;
                case 'G' -> GOAL;
                default -> throw new IllegalArgumentException("Invalid maze character '" + Character.toString(codepoint) + "'");
            };
        }
    }

    private final int rows, columns;
    private final Maze.Location start, goal;
    private final Cell[][] grid;

    public FixedMaze(List<String> maze) {
        assert maze != null;
        assert !maze.isEmpty();

        grid = maze.stream()
                .map(line -> line.chars()
                        .mapToObj(Cell::of)
                        .toArray(Cell[]::new))
                .toArray(Cell[][]::new);

        this.rows = grid.length;
        this.columns = grid[0].length;

        Arrays.stream(grid).forEach(row -> {
            if (row.length != this.columns)
                throw new IllegalArgumentException("Rows have variable lengths, which is not allowed");
        });

        this.start = locate(cell -> cell == Cell.START).orElseThrow(() -> new IllegalArgumentException("No start symbol on this maze"));
        this.goal = locate(cell -> cell == Cell.GOAL).orElseThrow(() -> new IllegalArgumentException("No goal symbol on this maze"));
    }

    @Override
    public Optional<Location> locate(Predicate<Cell> filter) {
        return locate(grid, filter);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (Cell[] row : grid) {
            for (Cell cell : row) {
                sb.append(cell.toString());
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    @Override
    public boolean goalTest(Maze.Location ml) {
        assert ml != null;

        return goal.equals(ml);
    }

    @Override
    public List<Maze.Location> successors(Maze.Location ml) {
        assert ml != null;

        final List<Maze.Location> locations = new ArrayList<>();
        if (ml.row() + 1 < rows && grid[ml.row() + 1][ml.column()] != Cell.BLOCKED) {
            locations.add(new Maze.Location(ml.row() + 1, ml.column()));
        }
        if (ml.row() - 1 >= 0 && grid[ml.row() - 1][ml.column()] != Cell.BLOCKED) {
            locations.add(new Maze.Location(ml.row() - 1, ml.column()));
        }
        if (ml.column() + 1 < columns && grid[ml.row()][ml.column() + 1] != Cell.BLOCKED) {
            locations.add(new Maze.Location(ml.row(), ml.column() + 1));
        }
        if (ml.column() - 1 >= 0 && grid[ml.row()][ml.column() - 1] != Cell.BLOCKED) {
            locations.add(new Maze.Location(ml.row(), ml.column() - 1));
        }
        return locations;
    }

    public void mark(List<Maze.Location> path) {
        assert path != null;

        for (Maze.Location ml : path) {
            grid[ml.row()][ml.column()] = Cell.PATH;
        }
        grid[start.row()][start.column()] = Cell.START;
        grid[goal.row()][goal.column()] = Cell.GOAL;
    }

    public void clear(List<Maze.Location> path) {
        assert path != null;

        for (Maze.Location ml : path) {
            grid[ml.row()][ml.column()] = Cell.EMPTY;
        }
        grid[start.row()][start.column()] = Cell.START;
        grid[goal.row()][goal.column()] = Cell.GOAL;
    }

    @Override
    public double euclideanDistance(Maze.Location from) {
        return Maze.euclideanDistance(from, goal);
    }

    @Override
    public double manhattanDistance(Maze.Location from) {
        return Maze.manhattanDistance(from, goal);
    }

    @Override
    public Cell cell(Location location) {
        Maze.checkLocation(location, rows, columns);
        return grid[location.row()][location.column()];
    }
}
