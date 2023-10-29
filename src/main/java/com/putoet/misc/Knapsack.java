// Knapsack.java
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
import java.util.List;

public final class Knapsack {
    public interface Item {
        String name();
        int weight();
        double value();
    }

    public static Item itemOf(String name, int weight, double value) {
        return new Item() {
            @Override
            public String name() {
                return name;
            }

            @Override
            public int weight() {
                return weight;
            }

            @Override
            public double value() {
                return value;
            }
        };
    }

    public static List<Item> fill(List<Item> items, int maxCapacity) {
        double[][] table = new double[items.size() + 1][maxCapacity + 1];
        for (int i = 0; i < items.size(); i++) {
            final Item item = items.get(i);
            for (int capacity = 1; capacity <= maxCapacity; capacity++) {
                double prevItemValue = table[i][capacity];
                if (capacity >= item.weight()) {
                    double valueFreeingWeightForItem = table[i][capacity - item.weight()];
                    table[i + 1][capacity] = Math.max(valueFreeingWeightForItem + item.value(), prevItemValue);
                } else {
                    table[i + 1][capacity] = prevItemValue;
                }
            }
        }

        final List<Item> solution = new ArrayList<>();
        int capacity = maxCapacity;
        for (int i = items.size(); i > 0; i--) {
            if (table[i - 1][capacity] != table[i][capacity]) {
                solution.add(items.get(i - 1));
                capacity -= items.get(i - 1).weight();
            }
        }

        return solution;
    }
}