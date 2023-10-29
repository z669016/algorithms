// WeightedGraph.java
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

package com.putoet.graph;

import java.util.*;
import java.util.function.IntConsumer;

public class WeightedGraph<V> extends AbstractGraph<V, WeightedEdge> {

    public record DijkstraNode(int vertex, double distance) implements Comparable<DijkstraNode> {

        @Override
        public int compareTo(DijkstraNode other) {
            final Double mine = distance;
            final Double theirs = other.distance;
            return mine.compareTo(theirs);
        }
    }

    public record DijkstraResult(double[] distances, Map<Integer, WeightedEdge> pathMap) {

        @Override
        public String toString() {
            return "{distances: " + Arrays.toString(distances) + ", pathMap: " + pathMap;
        }
    }

    public WeightedGraph() {
        super();
    }

    public WeightedGraph(List<V> vertices) {
        super(vertices);
    }

    public void addEdge(WeightedEdge edge) {
        validateEdge(edge);

        edges.get(edge.u).add(edge);
        edges.get(edge.v).add(edge.reversed());
    }

    public void addEdge(int u, int v, double weight) {
        addEdge(new WeightedEdge(u, v, weight));
    }

    public void addEdge(V first, V second, double weight) {
        addEdge(indexOf(first), indexOf(second), weight);
    }

    public static double totalWeight(List<WeightedEdge> path) {
        assert path != null;

        return path.stream().mapToDouble(we -> we.weight).sum();
    }

    public static double totalWeight(double[] weights) {
        return Arrays.stream(weights).sum();
    }

    public List<WeightedEdge> mst(V vertex) {
        return mst(indexOf(vertex));
    }

    public List<WeightedEdge> mst(int start) {
        assert start >= 0 && start < vertexCount();

        final List<WeightedEdge> result = new LinkedList<>();
        final PriorityQueue<WeightedEdge> pq = new PriorityQueue<>();
        boolean[] visited = new boolean[vertexCount()];

        final IntConsumer visit = index -> {
            visited[index] = true;
            for (WeightedEdge edge : edgesOf(index)) {
                if (!visited[edge.v]) {
                    pq.offer(edge);
                }
            }
        };

        visit.accept(start);
        while (!pq.isEmpty()) {
            final WeightedEdge edge = pq.poll();
            if (visited[edge.v]) {
                continue;
            }
            result.add(edge);
            visit.accept(edge.v);
        }

        return result;
    }

    public DijkstraResult dijkstra(V root) {
        return dijkstra(indexOf(root));
    }

    public DijkstraResult dijkstra(int first) {
        final double[] distances = new double[vertexCount()];
        final boolean[] visited = new boolean[vertexCount()];

        distances[first] = 0;
        visited[first] = true;

        final Map<Integer, WeightedEdge> pathMap = new HashMap<>();
        final PriorityQueue<DijkstraNode> pq = new PriorityQueue<>();
        pq.offer(new DijkstraNode(first, 0));

        while (!pq.isEmpty()) {
            int u = pq.poll().vertex;
            double distU = distances[u];

            for (WeightedEdge we : edgesOf(u)) {
                double distV = distances[we.v];
                double pathWeight = we.weight + distU;
                if (!visited[we.v] || (distV > pathWeight)) {
                    visited[we.v] = true;
                    distances[we.v] = pathWeight;
                    pathMap.put(we.v, we);
                    pq.offer(new DijkstraNode(we.v, pathWeight));
                }
            }
        }

        return new DijkstraResult(distances, pathMap);
    }

    public Map<V, Double> distancesMap(double[] distances) {
        assert distances != null;

        final Map<V, Double> distanceMap = new HashMap<>();
        for (int i = 0; i < distances.length; i++) {
            distanceMap.put(vertexAt(i), distances[i]);
        }
        return distanceMap;
    }

    public List<WeightedEdge> pathOf(V start, V end, Map<Integer, WeightedEdge> path) {
        return pathOf(indexOf(start), indexOf(end), path);
    }

    public static List<WeightedEdge> pathOf(int start, int end, Map<Integer, WeightedEdge> path) {
        assert path != null;

        if (path.isEmpty()) {
            return List.of();
        }

        final List<WeightedEdge> list = new LinkedList<>();
        WeightedEdge edge = path.get(end);
        list.add(edge);
        while (edge.u != start) {
            edge = path.get(edge.u);
            list.add(edge);
        }
        Collections.reverse(list);
        return list;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vertexCount(); i++) {
            sb.append(vertexAt(i).toString());
            sb.append(" -> ");
            sb.append(Arrays.toString(edgesOf(i).stream()
                    .map(we -> "(" + vertexAt(we.v) + ", " + we.weight + ")")
                    .toArray())
            );
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public String toString(List<WeightedEdge> path) {
        final StringBuilder sb = new StringBuilder();
        for (final WeightedEdge edge : path) {
            sb.append(String.format("%s %.0f > %s", vertexAt(edge.u), edge.weight, vertexAt(edge.v)));
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
