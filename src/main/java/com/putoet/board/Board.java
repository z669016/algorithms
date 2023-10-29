package com.putoet.board;
import java.util.List;

public interface Board<Move> {
    Piece getTurn();

    Board<Move> move(Move location);

    List<Move> getLegalMoves();

    boolean isWin();

    default boolean isDraw() {
        return (!isWin() && (getLegalMoves().isEmpty()));
    }

    double evaluate(Piece player);
}