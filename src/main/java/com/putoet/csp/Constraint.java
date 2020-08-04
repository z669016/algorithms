// Constraint.java
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

import java.util.List;
import java.util.Map;

public abstract class Constraint<V, D> {

    protected List<V> variables;

    public Constraint(List<V> variables) {
        assert variables != null;

        this.variables = variables;
    }

    public abstract boolean satisfied(Map<V, D> assignment);
}