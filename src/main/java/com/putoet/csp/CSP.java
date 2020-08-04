// CSP.java
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

package com.putoet.csp;

import java.util.*;

public class CSP<V,D> {
    private final List<V> variables;
    private final Map<V, List<D>> domains;
    private final Map<V, List<Constraint<V, D>>> constraints = new HashMap<>();

    public CSP(List<V> variables, List<D> domain) {
        assert variables != null;
        assert domain != null;

        this.variables = variables;
        this.domains = new HashMap<>();

        for (V variable : variables) {
            domains.put(variable, domain);
            constraints.put(variable, new ArrayList<>());
        }
    }

    public CSP(List<V> variables, Map<V, List<D>> domains) {
        assert variables != null;
        assert domains != null;

        this.variables = variables;
        this.domains = domains;
        for (V variable : variables) {
            constraints.put(variable, new ArrayList<>());
            if (!domains.containsKey(variable)) {
                throw new IllegalArgumentException("Every variable should have a domain assigned to it.");
            }
        }
    }

    public void addConstraint(Constraint<V, D> constraint) {
        assert constraint != null;

        for (V variable : constraint.variables) {
            if (!variables.contains(variable)) {
                throw new IllegalArgumentException("Variable in constraint not in CSP");
            } else {
                constraints.get(variable).add(constraint);
            }
        }
    }

    public boolean consistent(V variable, Map<V, D> assignment) {
        assert variable != null;
        assert assignment != null;

        for (Constraint<V, D> constraint : constraints.get(variable)) {
            if (!constraint.satisfied(assignment)) {
                return false;
            }
        }
        return true;
    }

    public Optional<Map<V, D>> backtrackingSearch(Map<V, D> assignment) {
        assert assignment != null;

        if (assignment.size() == variables.size()) {
            return Optional.of(assignment);
        }

        final Optional<V> unassigned = variables.stream().filter(v -> (!assignment.containsKey(v))).findFirst();
        if (unassigned.isEmpty())
            return Optional.empty();

        for (D value : domains.get(unassigned.get())) {
            final Map<V, D> localAssignment = new HashMap<>(assignment);
            localAssignment.put(unassigned.get(), value);
            if (consistent(unassigned.get(), localAssignment)) {
                final Optional<Map<V, D>> result = backtrackingSearch(localAssignment);
                if (result.isPresent()) {
                    return result;
                }
            }
        }
        return Optional.empty();
    }

    public Optional<Map<V, D>> backtrackingSearch() {
        return backtrackingSearch(Map.of());
    }
}
