// Gene.java
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

package com.putoet.examples.search;

import com.putoet.search.GenericSearch;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Gene {

    public enum Nucleotide {
        A, C, G, T
    }

    public static class Codon implements Comparable<Codon> {
        public final Nucleotide first, second, third;

        public Codon(String codonStr) {
            first = Enum.valueOf(Nucleotide.class, codonStr.substring(0, 1));
            second = Enum.valueOf(Nucleotide.class, codonStr.substring(1, 2));
            third = Enum.valueOf(Nucleotide.class, codonStr.substring(2, 3));
        }

        @Override
        public int compareTo(Codon other) {
            assert other != null;

            return Comparator.comparing((Codon c) -> c.first)
                    .thenComparing((Codon c) -> c.second)
                    .thenComparing((Codon c) -> c.third)
                    .compare(this, other);
        }
    }

    private final List<Codon> codons = new ArrayList<>();
    private final List<Codon> sortedCodons;

    public Gene(String gene) {
        assert gene != null && gene.length() >= 3 && gene.length() % 3 == 0;

        for (int i = 0; i < gene.length() - 3; i++) {
            codons.add(new Codon(gene.substring(i, i + 3)));
        }

        sortedCodons = codons.stream().sorted().collect(Collectors.toList());
    }

    public boolean linearContains(Codon key) {
        return GenericSearch.linearContains(codons, key);
    }

    public boolean binaryContains(Codon key) {
        assert key != null;
        return GenericSearch.binaryContains(sortedCodons, key);
    }

    public static void main(String[] args) {
        final String genes = "ACGTGGCTCTCTAACGTACGTACGTACGGGGTTTATATATACCCTAGGACTCCCTTT";
        final Gene myGene = new Gene(genes);
        final Codon acg = new Codon("ACG");
        final Codon gat = new Codon("GAT");
        System.out.println(myGene.linearContains(acg)); // true
        System.out.println(myGene.linearContains(gat)); // false
        System.out.println(myGene.binaryContains(acg)); // true
        System.out.println(myGene.binaryContains(gat)); // false
    }
}