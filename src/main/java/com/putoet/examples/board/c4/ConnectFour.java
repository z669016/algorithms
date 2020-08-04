// ConnectFour.java
// From Classic Computer Science Problems in Java Chapter 8
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

package com.putoet.examples.board.c4;

import com.putoet.board.Minimax;

import java.util.Scanner;

public class ConnectFour {
    private final Scanner scanner = new Scanner(System.in);
    private C4Board board = new C4Board();

   private void runGame() {
        boolean done = false;

        while (!done) {
            System.out.println(board);
            done = playMove(getPlayerMove(), "Human ");
            if (!done) {
                System.out.println(board);
                done = playMove(Minimax.findBestMove(board, 7), "Computer");
            }
        }
       System.out.println(board);
    }

    private int getPlayerMove() {
        int playerMove = -1;
        while (!board.getLegalMoves().contains(playerMove)) {
            System.out.println("Enter a legal column (0-6):");
            playerMove = scanner.nextInt();
        }
        return playerMove;
    }

    private boolean playMove(int move, String name) {
        board = board.move(move);
        if (board.isWin()) {
            System.out.println(name + " wins!");
            return true;
        } else if (board.isDraw()) {
            System.out.println("Draw!");
            return true;
        }

        return false;
    }

    public static void main(String[] args) {
        new ConnectFour().runGame();
    }
}