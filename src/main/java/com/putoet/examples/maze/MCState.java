// MCState.java
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

package com.putoet.examples.maze;

import com.putoet.search.GenericSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public class MCState {
    private static final int MAX_NUM = 3;
    private final int wm; // west bank missionaries
    private final int wc; // west bank cannibals
    private final int em; // east bank missionaries
    private final int ec; // east bank cannibals
    private final boolean boat; // is boat on west bank?

    public MCState(int missionaries, int cannibals, boolean boat) {
        wm = missionaries;
        wc = cannibals;
        em = MAX_NUM - wm;
        ec = MAX_NUM - wc;
        this.boat = boat;
    }

    @Override
    public String toString() {
        return String.format(
                "On the west bank there are %d missionaries and %d cannibals.%n"
                        + "On the east bank there are %d missionaries and %d cannibals.%n"
                        + "The boat is on the %s bank.",
                wm, wc, em, ec,
                boat ? "west" : "east");
    }

    public boolean goalTest() {
        return isLegal() && em == MAX_NUM && ec == MAX_NUM;
    }

    public boolean isLegal() {
        if (wm < wc && wm > 0) {
            return false;
        }
        return em >= ec || em <= 0;
    }

    public static List<MCState> successors(MCState mcs) {
        assert mcs != null;

        final List<MCState> successors = new ArrayList<>();
        if (mcs.boat) {
            if (mcs.wm > 1) {
                successors.add(new MCState(mcs.wm - 2, mcs.wc, false));
            }
            if (mcs.wm > 0) {
                successors.add(new MCState(mcs.wm - 1, mcs.wc, false));
            }
            if (mcs.wc > 1) {
                successors.add(new MCState(mcs.wm, mcs.wc - 2, false));
            }
            if (mcs.wc > 0) {
                successors.add(new MCState(mcs.wm, mcs.wc - 1, false));
            }
            if (mcs.wc > 0 && mcs.wm > 0) {
                successors.add(new MCState(mcs.wm - 1, mcs.wc - 1, false));
            }
        } else { // boat on east bank
            if (mcs.em > 1) {
                successors.add(new MCState(mcs.wm + 2, mcs.wc, true));
            }
            if (mcs.em > 0) {
                successors.add(new MCState(mcs.wm + 1, mcs.wc, true));
            }
            if (mcs.ec > 1) {
                successors.add(new MCState(mcs.wm, mcs.wc + 2, true));
            }
            if (mcs.ec > 0) {
                successors.add(new MCState(mcs.wm, mcs.wc + 1, true));
            }
            if (mcs.ec > 0 && mcs.em > 0) {
                successors.add(new MCState(mcs.wm + 1, mcs.wc + 1, true));
            }
        }
        successors.removeIf(Predicate.not(MCState::isLegal));
        return successors;
    }

    public static void displaySolution(List<MCState> path) {
        assert path != null;
        if (path.isEmpty())
            return;

        MCState oldState = path.get(0);
        System.out.println(oldState);
        for (MCState currentState : path.subList(1, path.size())) {
            if (currentState.boat) {
                System.out.printf("%d missionaries and %d cannibals moved from the east bank to the west bank." +
                                System.lineSeparator(),
                        oldState.em - currentState.em,
                        oldState.ec - currentState.ec);
            } else {
                System.out.printf("%d missionaries and %d cannibals moved from the west bank to the east bank." +
                                System.lineSeparator(),
                        oldState.wm - currentState.wm,
                        oldState.wc - currentState.wc);
            }
            System.out.println(currentState);
            oldState = currentState;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MCState mcState)) return false;
        return wm == mcState.wm &&
                wc == mcState.wc &&
                em == mcState.em &&
                ec == mcState.ec &&
                boat == mcState.boat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(wm, wc, em, ec, boat);
    }

    public static void main(String[] args) {
        final MCState start = new MCState(MAX_NUM, MAX_NUM, true);
        final Optional<GenericSearch.Node<MCState>> solution = GenericSearch.bfs(start, MCState::goalTest, MCState::successors);
        if (solution.isEmpty()) {
            System.out.println("No solution found!");
        } else {
            final List<MCState> path = GenericSearch.nodeToPath(solution.get());
            displaySolution(path);
        }
    }

}