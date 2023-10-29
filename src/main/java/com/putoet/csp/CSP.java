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
