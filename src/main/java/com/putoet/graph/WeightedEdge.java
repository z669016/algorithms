// WeightedEdge.java
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

public class WeightedEdge extends Edge implements Comparable<WeightedEdge>{
    public final double weight;

    public WeightedEdge(int u, int v, double weight) {
        super(u, v);

        this.weight = weight;
    }

    public WeightedEdge reversed() {
        return new WeightedEdge(v, u, weight);
    }

    @Override
    public String toString() {
        return String.format("%d %.2f -> %d", u, weight, v);
    }

    @Override
    public int compareTo(WeightedEdge other) {
        assert other != null;

        final Double myWeight = weight;
        return myWeight.compareTo(other.weight);
    }
}
