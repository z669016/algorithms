// QueensConstraint.java
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

package com.putoet.examples.csp;

import com.putoet.csp.CSP;
import com.putoet.csp.Constraint;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

public class QueensConstraint extends Constraint<Integer, Integer> {
    private final List<Integer> columns;

    public QueensConstraint(List<Integer> columns) {
        super(columns);
        this.columns = columns;
    }

    @Override
    public boolean satisfied(Map<Integer, Integer> assignment) {
        assert assignment != null;

        for (Entry<Integer, Integer> item : assignment.entrySet()) {
            final int q1c = item.getKey();
            final int q1r = item.getValue();

            for (int q2c = q1c + 1; q2c <= columns.size(); q2c++) {
                if (assignment.containsKey(q2c)) {
                    final int q2r = assignment.get(q2c);
                    if (q1r == q2r) {
                        return false;
                    }
                    if (Math.abs(q1r - q2r) == Math.abs(q1c - q2c)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        final List<Integer> columns = List.of(1, 2, 3, 4, 5, 6, 7, 8);
        final CSP<Integer, Integer> csp = new CSP<>(columns, columns);
        csp.addConstraint(new QueensConstraint(columns));

        final Optional<Map<Integer, Integer>> solution = csp.backtrackingSearch();
        if (solution.isEmpty()) {
            System.out.println("No solution found!");
        } else {
            System.out.println(solution);
        }
    }
}