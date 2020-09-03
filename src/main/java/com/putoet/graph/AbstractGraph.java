// AbstractGraph.java
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractGraph<V, E extends Edge> implements Graph<V, E> {
    final protected List<V> vertices;
    final protected List<ArrayList<E>> edges;

    public AbstractGraph() {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public AbstractGraph(List<V> vertices) {
        assert vertices != null;

        this.vertices = new ArrayList<>(vertices);
        edges = new ArrayList<>();
        for (V vertice : vertices) {
            edges.add(new ArrayList<>());
        }
    }

    @Override
    public int vertexCount() {
        return vertices.size();
    }

    @Override
    public int edgeCount() {
        return edges.stream().mapToInt(ArrayList::size).sum();
    }

    @Override
    public int addVertex(V vertex) {
        assert vertex != null;

        vertices.add(vertex);
        edges.add(new ArrayList<>());
        return vertexCount() - 1;
    }

    @Override
    public V vertexAt(int index) {
        assert index >=0 && index < vertices.size();

        return vertices.get(index);
    }

    @Override
    public int indexOf(V vertex) {
        assert vertex != null;
        final int index = vertices.indexOf(vertex);

        if (index < 0)
            throw new IllegalArgumentException("Vertex '" + vertex + "' not part of the graph");

        return index;
    }

    @Override
    public List<V> neighboursOf(int index) {
        assert index >=0 && index < vertices.size();

        return edges.get(index).stream()
                .map(edge -> vertexAt(edge.v))
                .collect(Collectors.toList());
    }

    @Override
    public List<V> neighboursOf(V vertex) {
        assert vertex != null;

        return neighboursOf(indexOf(vertex));
    }

    @Override
    public List<E> edgesOf(int index) {
        assert index >=0 && index < vertices.size();

        return edges.get(index);
    }

    @Override
    public List<E> edgesOf(V vertex) {
        return edgesOf(indexOf(vertex));
    }

    @Override
    public void validateEdge(Edge edge) {
        if ((edge.u < 0 || edge.u >= vertexCount()) || (edge.v < 0 || edge.v >= vertexCount()))
            throw new IllegalArgumentException("Invalid edge '" + edge + "'");
    }

    @Override
    public boolean contains(V vertex) {
        return vertices.contains(vertex);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vertexCount(); i++) {
            sb.append(vertexAt(i).toString());
            sb.append(" -> ");
            sb.append(Arrays.toString(neighboursOf(i).toArray()));
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}