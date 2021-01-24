// GenericSearch.java
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

package com.putoet.search;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;

public class GenericSearch {

    public static <T extends Comparable<T>> boolean linearContains(List<T> list, T key) {
        assert list != null;
        assert key != null;

        for (T item : list) {
            if (item.compareTo(key) == 0) {
                return true;
            }
        }
        return false;
    }

    public static <T extends Comparable<T>> boolean binaryContains(List<T> list, T key) {
        assert list != null;
        assert key != null;

        int low = 0;
        int high = list.size() - 1;
        while (low <= high) {
            int middle = (low + high) / 2;
            int comparison = list.get(middle).compareTo(key);
            if (comparison < 0) {
                low = middle + 1;
            } else if (comparison > 0) {
                high = middle - 1;
            } else {
                return true;
            }
        }
        return false;
    }

    public static class Node<T> implements Comparable<Node<T>> {
        public final T state;
        public final Node<T> parent;
        public final double cost;
        public final double heuristic;

        public Node(T state, Node<T> parent) {
            this(state, parent, 0.0, 0.0);
        }

        public Node(T state, Node<T> parent, double cost, double heuristic) {
            assert state != null;

            this.state = state;
            this.parent = parent;
            this.cost = cost;
            this.heuristic = heuristic;
        }

        @Override
        public int compareTo(Node<T> other) {
            final Double mine = cost + heuristic;
            final Double theirs = other.cost + other.heuristic;
            return mine.compareTo(theirs);
        }

        public List<T> path() {
            final List<T> path = new ArrayList<>();
            Node<T> node = this;

            path.add(node.state);
            while (node.parent != null) {
                node = node.parent;
                path.add(0, node.state); // add to front
            }
            return path;
        }

        public int steps() {
            int steps = 0;
            for (Node<T> node = this; node.parent != null; node = node.parent) {
                steps++;
            }
            return steps;
        }
    }

    public static <T> Optional<Node<T>> dfs(T initial, Predicate<T> goalTest, Function<T, List<T>> successors) {
        assert initial != null;
        assert goalTest != null;
        assert successors != null;

        final Stack<Node<T>> frontier = new Stack<>();
        frontier.push(new Node<>(initial, null));

        final Set<T> explored = new HashSet<>();
        explored.add(initial);

        while (!frontier.isEmpty()) {
            final Node<T> currentNode = frontier.pop();
            final T currentState = currentNode.state;

            if (goalTest.test(currentState)) {
                return Optional.of(currentNode);
            }

            for (T child : successors.apply(currentState)) {
                if (explored.contains(child)) {
                    continue;
                }
                explored.add(child);
                frontier.push(new Node<>(child, currentNode));
            }
        }
        return Optional.empty();
    }

    public static <T> List<T> nodeToPath(Node<T> node) {
        assert node != null;

        return node.path();
    }

    public static <T> Optional<Node<T>> bfs(T initial, Predicate<T> goalTest, Function<T, List<T>> successors) {
        assert initial != null;
        assert goalTest != null;
        assert successors != null;

        final Queue<Node<T>> frontier = new LinkedList<>();
        frontier.offer(new Node<>(initial, null));

        final Set<T> explored = new HashSet<>();
        explored.add(initial);

        while (!frontier.isEmpty()) {
            final Node<T> currentNode = frontier.poll();
            final T currentState = currentNode.state;

            if (goalTest.test(currentState)) {
                return Optional.of(currentNode);
            }

            for (T child : successors.apply(currentState)) {
                if (explored.contains(child)) {
                    continue;
                }
                explored.add(child);
                frontier.offer(new Node<>(child, currentNode));
            }
        }
        return Optional.empty();
    }

    public static <T> List<Node<T>> findAll(T initial, Predicate<T> goalTest, Function<T, List<T>> successors) {
        assert initial != null;
        assert goalTest != null;
        assert successors != null;

        final List<Node<T>> all = new ArrayList<>();
        final Queue<Node<T>> frontier = new LinkedList<>();
        frontier.offer(new Node<>(initial, null));

        final Set<T> explored = new HashSet<>();
        explored.add(initial);

        while (!frontier.isEmpty()) {
            final Node<T> currentNode = frontier.poll();
            final T currentState = currentNode.state;

            if (goalTest.test(currentState)) {
                all.add(currentNode);
                continue;
            }

            for (T child : successors.apply(currentState)) {
                if (explored.contains(child)) {
                    continue;
                }
                explored.add(child);
                frontier.offer(new Node<>(child, currentNode));
            }
        }

        return all;
    }

    public static <T> Optional<Node<T>> astar(T initial, Predicate<T> goalTest, Function<T, List<T>> successors, ToDoubleFunction<T> heuristic) {
        assert initial != null;
        assert goalTest != null;
        assert successors != null;
        assert heuristic != null;

        final Queue<Node<T>> frontier = new PriorityQueue<>();
        frontier.offer(new Node<>(initial, null, 0.0, heuristic.applyAsDouble(initial)));

        final Map<T, Double> explored = new HashMap<>();
        explored.put(initial, 0.0);

        while (!frontier.isEmpty()) {
            final Node<T> currentNode = frontier.poll();
            final T currentState = currentNode.state;

            if (goalTest.test(currentState)) {
                return Optional.of(currentNode);
            }

            for (T child : successors.apply(currentState)) {
                double newCost = currentNode.cost + 1;
                if (!explored.containsKey(child) || explored.get(child) > newCost) {
                    explored.put(child, newCost);
                    frontier.offer(new Node<>(child, currentNode, newCost, heuristic.applyAsDouble(child)));
                }
            }
        }

        return Optional.empty();
    }
}