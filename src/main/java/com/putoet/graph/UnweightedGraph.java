// UnweightedGraph.java
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

import java.util.List;

public class UnweightedGraph<V> extends AbstractGraph<V, Edge> {

    public UnweightedGraph() {
        super();
    }

    public UnweightedGraph(List<V> vertices) {
        super(vertices);
    }

    public void addEdge(Edge edge) {
        assert edge != null;

        validateEdge(edge);
        if (!edgesOf(edge.u).contains(edge))
            edges.get(edge.u).add(edge);
        if (!edgesOf(edge.v).contains(edge.reversed()))
            edges.get(edge.v).add(edge.reversed());
    }

    public void addEdge(int u, int v) {
        addEdge(new Edge(u, v));
    }

    public void addEdge(V first, V second) {
        addEdge(indexOf(first), indexOf(second));
    }
}
