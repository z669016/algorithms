// SendMoreMoneyConstraint.java
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

public class SendMoreMoneyConstraint extends Constraint<Character, Integer> {
    private final List<Character> letters;

    public SendMoreMoneyConstraint(List<Character> letters) {
        super(letters);
        this.letters = letters;
    }

    @Override
    public boolean satisfied(Map<Character, Integer> assignment) {
        assert assignment != null;

        if ((new HashSet<>(assignment.values())).size() < assignment.size()) {
            return false;
        }

        if (assignment.size() == letters.size()) {
            int s = assignment.get('S');
            int e = assignment.get('E');
            int n = assignment.get('N');
            int d = assignment.get('D');
            int m = assignment.get('M');
            int o = assignment.get('O');
            int r = assignment.get('R');
            int y = assignment.get('Y');
            int send = s * 1000 + e * 100 + n * 10 + d;
            int more = m * 1000 + o * 100 + r * 10 + e;
            int money = m * 10000 + o * 1000 + n * 100 + e * 10 + y;
            return send + more == money;
        }

        return true;
    }

    public static void main(String[] args) {
        final List<Character> letters = List.of('S', 'E', 'N', 'D', 'M', 'O', 'R', 'Y');
        final CSP<Character, Integer> csp = new CSP<>(letters, List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        csp.addConstraint(new SendMoreMoneyConstraint(letters));

        final Optional<Map<Character, Integer>> solution = csp.backtrackingSearch();
        if (solution.isEmpty()) {
            System.out.println("No solution found!");
        } else {
            System.out.println(solution);
        }
    }
}