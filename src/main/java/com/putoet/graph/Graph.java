// Graph.java
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

public interface Graph<V, E extends Edge> {
    int vertexCount();

    int edgeCount();

    int addVertex(V vertex);

    V vertexAt(int index);

    int indexOf(V vertex);

    List<V> neighboursOf(int index);

    List<V> neighboursOf(V vertex);

    List<E> edgesOf(int index);

    List<E> edgesOf(V vertex);

    void validateEdge(Edge edge);

    boolean contains(V vertex);
}
