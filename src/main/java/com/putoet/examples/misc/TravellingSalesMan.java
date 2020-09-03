package com.putoet.examples.misc;

import com.putoet.misc.TSP;

import java.util.Arrays;
import java.util.Map;

public class TravellingSalesMan {
    public static void main(String[] args) {
        final Map<String, Map<String, Integer>> vtDistances = Map.of(
                "Rutland", Map.of(
                        "Burlington", 67,
                        "White River Junction", 46,
                        "Bennington", 55,
                        "Brattleboro", 75),
                "Burlington", Map.of(
                        "Rutland", 67,
                        "White River Junction", 91,
                        "Bennington", 122,
                        "Brattleboro", 153),
                "White River Junction", Map.of(
                        "Rutland", 46,
                        "Burlington", 91,
                        "Bennington", 98,
                        "Brattleboro", 65),
                "Bennington", Map.of(
                        "Rutland", 55,
                        "Burlington", 122,
                        "White River Junction", 98,
                        "Brattleboro", 40),
                "Brattleboro", Map.of(
                        "Rutland", 75,
                        "Burlington", 153,
                        "White River Junction", 65,
                        "Bennington", 40));

        final TSP tsp = new TSP(vtDistances);
        final String[] shortestPath = tsp.findShortestPath();
        final int distance = tsp.pathDistance(shortestPath);
        System.out.println("The shortest path is " + Arrays.toString(shortestPath) + " in " +
                distance + " miles.");
    }
}
