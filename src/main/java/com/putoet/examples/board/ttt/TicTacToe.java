// TicTacToe.java
// From Classic Computer Science Problems in Java Chapter 8
// Copyright 2020 David Kopec
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

import com.putoet.board.Minimax;

import java.util.Scanner;

public class TicTacToe {
    private final Scanner scanner = new Scanner(System.in);
    private TTTBoard board = new TTTBoard();

    private int getPlayerMove() {
        int playerMove = -1;
        while (!board.getLegalMoves().contains(playerMove)) {
            System.out.println("Enter a legal square (0-8):");
            playerMove = scanner.nextInt();
        }
        return playerMove;
    }

    private void runGame() {
        boolean done = false;
        while (!done) {
            done = playMove("Human", getPlayerMove());

            if (!done)
                done = playMove("Computer", Minimax.findBestMove(board, 9));
        }
    }

    private boolean playMove(String player, int move) {
        System.out.println(player + " move is " + move);
        board = board.move(move);
        System.out.println(board);

        if (board.isWin()) {
            System.out.println(player + " wins!");
            return true;
        } else if (board.isDraw()) {
            System.out.println("Draw!");
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        new TicTacToe().runGame();
    }

}