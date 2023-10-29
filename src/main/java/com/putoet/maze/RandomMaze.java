// RandomMaze.java
//
// Copyright 2020 Rene van Putten
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.putoet.maze;

import java.util.*;
import java.util.function.Predicate;

public class RandomMaze implements Maze<RandomMaze.Cell> {
    public enum Cell {
        EMPTY, BLOCKED, START, GOAL, PATH;

        @Override
        public String toString() {
            return switch (this) {
                case EMPTY -> ".";
                case BLOCKED -> "X";
                case START -> "S";
                case GOAL -> "G";
                case PATH -> "*";
            };
        }
    }

    private final int rows, columns;
    private final Location start, goal;
    private final Cell[][] grid;

    public RandomMaze(int rows, int columns, Location start, Location goal, double sparseness) {
        assert rows > 0;
        assert columns > 0;
        assert start != null;
        assert goal != null;

        this.rows = rows;
        this.columns = columns;
        this.start = start;
        this.goal = goal;

        grid = new Cell[rows][columns];
        for (Cell[] row : grid) {
            Arrays.fill(row, Cell.EMPTY);
        }

        randomlyFill(sparseness);

        grid[start.row()][start.column()] = Cell.START;
        grid[goal.row()][goal.column()] = Cell.GOAL;
    }

    public RandomMaze() {
        this(10, 10, new Location(0, 0), new Location(9, 9), 0.2);
    }

    private void randomlyFill(double sparseness) {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (Math.random() < sparseness) {
                    grid[row][column] = Cell.BLOCKED;
                }
            }
        }
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
    public boolean goalTest(Location ml) {
        assert ml != null;

        return goal.equals(ml);
    }

    @Override
    public List<Location> successors(Location ml) {
        assert ml != null;

        final List<Location> locations = new ArrayList<>();
        if (ml.row() + 1 < rows && grid[ml.row() + 1][ml.column()] != Cell.BLOCKED) {
            locations.add(new Location(ml.row() + 1, ml.column()));
        }
        if (ml.row() - 1 >= 0 && grid[ml.row() - 1][ml.column()] != Cell.BLOCKED) {
            locations.add(new Location(ml.row() - 1, ml.column()));
        }
        if (ml.column() + 1 < columns && grid[ml.row()][ml.column() + 1] != Cell.BLOCKED) {
            locations.add(new Location(ml.row(), ml.column() + 1));
        }
        if (ml.column() - 1 >= 0 && grid[ml.row()][ml.column() - 1] != Cell.BLOCKED) {
            locations.add(new Location(ml.row(), ml.column() - 1));
        }
        return locations;
    }

    public void mark(List<Location> path) {
        assert path != null;

        for (Location ml : path) {
            grid[ml.row()][ml.column()] = Cell.PATH;
        }
        grid[start.row()][start.column()] = Cell.START;
        grid[goal.row()][goal.column()] = Cell.GOAL;
    }

    public void clear(List<Location> path) {
        assert path != null;

        for (Location ml : path) {
            grid[ml.row()][ml.column()] = Cell.EMPTY;
        }
        grid[start.row()][start.column()] = Cell.START;
        grid[goal.row()][goal.column()] = Cell.GOAL;
    }

    @Override
    public double euclideanDistance(Location from) {
        return Maze.euclideanDistance(from, goal);
    }

    @Override
    public double manhattanDistance(Location from) {
        return Maze.manhattanDistance(from, goal);
    }

    @Override
    public Cell cell(Location location) {
        Maze.checkLocation(location, rows, columns);
        return grid[location.row()][location.column()];
    }
}