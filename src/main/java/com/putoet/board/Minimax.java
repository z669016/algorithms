// Minimax.java
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

package com.putoet.board;

public class Minimax {
    public static <Move> double minimax(Board<Move> board, boolean maximizing, Piece originalPlayer, int maxDepth) {
        assert board != null;
        assert originalPlayer != null;

        if (board.isWin() || board.isDraw() || maxDepth == 0) {
            return board.evaluate(originalPlayer);
        }

        return maximizing ? minimaxMaximize(board, originalPlayer, maxDepth) : minimaxMinimize(board, originalPlayer, maxDepth);
    }

    private static <Move> double minimaxMaximize(Board<Move> board, Piece originalPlayer, int maxDepth) {
        double bestEval = Double.NEGATIVE_INFINITY;
        for (Move move : board.getLegalMoves()) {
            double result = minimax(board.move(move), false, originalPlayer, maxDepth - 1);
            bestEval = Math.max(result, bestEval);
        }
        return bestEval;
    }

    private static <Move> double minimaxMinimize(Board<Move> board, Piece originalPlayer, int maxDepth) {
        double worstEval = Double.POSITIVE_INFINITY;
        for (Move move : board.getLegalMoves()) {
            double result = minimax(board.move(move), true, originalPlayer, maxDepth - 1);
            worstEval = Math.min(result, worstEval);
        }
        return worstEval;
    }

    public static <Move> Move findBestMove(Board<Move> board, int maxDepth) {
        assert board != null;

        double bestEval = Double.NEGATIVE_INFINITY;
        Move bestMove = null;
        for (Move move : board.getLegalMoves()) {
            double result = alphabeta(board.move(move), false, board.getTurn(), maxDepth);
            if (result > bestEval) {
                bestEval = result;
                bestMove = move;
            }
        }
        return bestMove;
    }

    public static <Move> double alphabeta(Board<Move> board, boolean maximizing, Piece originalPlayer, int maxDepth) {
        assert board != null;
        assert originalPlayer != null;

        return alphabeta(board, maximizing, originalPlayer, maxDepth, Double.NEGATIVE_INFINITY,
                Double.POSITIVE_INFINITY);
    }

    private static <Move> double alphabeta(
            Board<Move> board, boolean maximizing, Piece originalPlayer, int maxDepth, double alpha, double beta) {
        if (board.isWin() || board.isDraw() || maxDepth == 0) {
            return board.evaluate(originalPlayer);
        }

        return maximizing ?
                alphabetaMaximize(board, originalPlayer, maxDepth, alpha, beta) :
                alphabetaMinimize(board, originalPlayer, maxDepth, alpha, beta);
    }

    private static <Move> double alphabetaMinimize(Board<Move> board, Piece originalPlayer, int maxDepth, double alpha, double beta) {
        for (Move m : board.getLegalMoves()) {
            beta = Math.min(beta, alphabeta(board.move(m), true, originalPlayer, maxDepth - 1, alpha, beta));
            if (beta <= alpha) {
                break;
            }
        }
        return beta;
    }

    private static <Move> double alphabetaMaximize(Board<Move> board, Piece originalPlayer, int maxDepth, double alpha, double beta) {
        for (Move m : board.getLegalMoves()) {
            alpha = Math.max(alpha, alphabeta(board.move(m), false, originalPlayer, maxDepth - 1, alpha, beta));
            if (beta <= alpha) {
                break;
            }
        }
        return alpha;
    }
}