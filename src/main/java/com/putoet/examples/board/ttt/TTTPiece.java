// TTTPiece.java
// From Classic Computer Science Problems in Java Chapter 8
// Copyright 2020 Rene vna Putten
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

package com.putoet.examples.board.ttt;

import com.putoet.board.Piece;

public enum TTTPiece implements Piece {
    X, O, E;

    @Override
    public TTTPiece opposite() {
        return switch(this) {
            case X -> O;
            case O -> X;
            default -> E;
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case X ->  "X";
            case O -> "O";
            default -> " ";
        };
    }
}