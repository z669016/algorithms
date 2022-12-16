// TSP.java
// From Classic Computer Science Problems in Java Chapter 9
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

package com.putoet.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TSP {
    private final Map<String, Map<String, Integer>> distances;

    public TSP(Map<String, Map<String, Integer>> distances) {
        assert distances != null;

        this.distances = distances;
    }

    private static <T> void swap(T[] array, int first, int second) {
        T temp = array[first];
        array[first] = array[second];
        array[second] = temp;
    }

    private static <T> void allPermutationsHelper(T[] permutation, List<T[]> permutations, int n) {
        if (n <= 0) {
            permutations.add(permutation);
            return;
        }

        final T[] tempPermutation = Arrays.copyOf(permutation, permutation.length);
        for (int i = 0; i < n; i++) {
            swap(tempPermutation, i, n - 1);
            allPermutationsHelper(tempPermutation, permutations, n - 1);
            swap(tempPermutation, i, n - 1);
        }
    }

    public static <T> List<T[]> permutations(T[] original) {
        assert original != null;

        final List<T[]> permutations = new ArrayList<>();
        allPermutationsHelper(original, permutations, original.length);
        return permutations;
    }

    public int pathDistance(String[] path) {
        assert path != null;

        String last = path[0];
        int distance = 0;
        for (String next : Arrays.copyOfRange(path, 1, path.length)) {
            distance += distances.get(last).get(next);
            last = next;
        }
        return distance;
    }

    public String[] findShortestPath() {
        final String[] cities = distances.keySet().toArray(String[]::new);
        final List<String[]> paths = permutations(cities);

        String[] shortestPath = null;
        int minDistance = Integer.MAX_VALUE;
        for (String[] path : paths) {
            int distance = pathDistance(path);

            distance += distances.get(path[path.length - 1]).get(path[0]);
            if (distance < minDistance) {
                minDistance = distance;
                shortestPath = path;
            }
        }

        shortestPath = Arrays.copyOf(shortestPath, shortestPath.length + 1);
        shortestPath[shortestPath.length - 1] = shortestPath[0];
        return shortestPath;
    }

    public String[] findLongestPath() {
        final String[] cities = distances.keySet().toArray(String[]::new);
        final List<String[]> paths = permutations(cities);

        String[] longestPath = null;
        int maxDistance = Integer.MIN_VALUE;
        for (String[] path : paths) {
            int distance = pathDistance(path);

            distance += distances.get(path[path.length - 1]).get(path[0]);
            if (distance > maxDistance) {
                maxDistance = distance;
                longestPath = path;
            }
        }

        longestPath = Arrays.copyOf(longestPath, longestPath.length + 1);
        longestPath[longestPath.length - 1] = longestPath[0];
        return longestPath;
    }
}