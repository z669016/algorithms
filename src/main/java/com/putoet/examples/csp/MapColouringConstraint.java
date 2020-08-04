// MapColouringConstraint.java
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

import java.util.*;

public final class MapColouringConstraint extends Constraint<String, String> {
    private final String place1, place2;

    public MapColouringConstraint(String place1, String place2) {
        super(List.of(place1, place2));
        this.place1 = place1;
        this.place2 = place2;
    }

    @Override
    public boolean satisfied(Map<String, String> assignment) {
        if (!assignment.containsKey(place1) || !assignment.containsKey(place2)) {
            return true;
        }

        return !assignment.get(place1).equals(assignment.get(place2));
    }

    public static void main(String[] args) {
        final List<String> variables = List.of(
                "Western Australia",
                "Northern Territory",
                "South Australia",
                "Queensland",
                "New South Wales",
                "Victoria",
                "Tasmania");

        final CSP<String, String> csp = new CSP<>(variables, List.of("red", "green", "blue"));
        csp.addConstraint(new MapColouringConstraint("Western Australia", "Northern Territory"));
        csp.addConstraint(new MapColouringConstraint("Western Australia", "South Australia"));
        csp.addConstraint(new MapColouringConstraint("South Australia", "Northern Territory"));
        csp.addConstraint(new MapColouringConstraint("Queensland", "Northern Territory"));
        csp.addConstraint(new MapColouringConstraint("Queensland", "South Australia"));
        csp.addConstraint(new MapColouringConstraint("Queensland", "New South Wales"));
        csp.addConstraint(new MapColouringConstraint("New South Wales", "South Australia"));
        csp.addConstraint(new MapColouringConstraint("Victoria", "South Australia"));
        csp.addConstraint(new MapColouringConstraint("Victoria", "New South Wales"));
        csp.addConstraint(new MapColouringConstraint("Victoria", "Tasmania"));

        final Optional<Map<String, String>> solution = csp.backtrackingSearch();
        if (solution.isEmpty()) {
            System.out.println("No solution found!");
        } else {
            System.out.println(solution);
        }
    }
}