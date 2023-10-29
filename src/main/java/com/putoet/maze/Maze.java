// Mze.java
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

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface Maze<T> {
    record Location(int row, int column) {
        public Location {
            assert row >= 0;
            assert column >= 0;

        }

        @Override
        public String toString() {
            return "(" + column + "," + row + ")";
        }

        public static Location of(int row, int column) {
            assert row >= 0;
            assert column >= 0;

            return new Location(row, column);
        }
    }

    default boolean goalTest(Location location) {
        throw new IllegalArgumentException("Default goalTest must not be used");
    }

    default List<Location> successors(Location location) {
        throw new IllegalArgumentException("Default successors must not be used");
    }

    default double euclideanDistance(Location from) {
        throw new IllegalArgumentException("Default euclideanDistance must not be used");
    }

    default double manhattanDistance(Location from) {
        throw new IllegalArgumentException("Default manhattanDistance must not be used");
    }

    T cell(Location location);

    Optional<Location> locate(Predicate<T> predicate);

    default Optional<Location> locate(T[][] grid, Predicate<T> filter) {
        for (int row = 0; row < grid.length; row++)
            for (int column = 0; column < grid[row].length; column++)
                if (filter.test(grid[row][column]))
                    return Optional.of(Location.of(row, column));

        return Optional.empty();
    }

    static double euclideanDistance(Location from, Location to) {
        assert from != null;
        assert to != null;

        int xdist = from.column - to.column;
        int ydist = from.row - to.row;
        return Math.sqrt((xdist * xdist) + (ydist * ydist));
    }

    static double manhattanDistance(Location from, Location to) {
        assert from != null;
        assert to != null;

        int xdist = Math.abs(from.column - to.column);
        int ydist = Math.abs(from.row - to.row);
        return (xdist + ydist);
    }

    static void checkLocation(Location location, int maxRow, int maxColumn) {
        if (location.row < 0 || location.row >= maxRow || location.column < 0 || location.column >= maxColumn)
            throw new IllegalArgumentException(
                    "Location " + location + " is outside the maze (max row: " + maxRow + ", max column: " + maxColumn + ")"
            );
    }
}
