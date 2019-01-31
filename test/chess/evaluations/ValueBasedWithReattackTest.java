package chess.evaluations;

import chess.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValueBasedWithReattackTest {


    @Test
    void testScore() {
        Governor governor = new Governor();
        ValueBasedWithReattack test = new ValueBasedWithReattack(100);
        assertEquals(0, test.score(Color.WHITE, Color.BLACK, governor.getGame()));

        governor.getGame().cleanTable();

        Bishop bishopW = new Bishop(Color.WHITE, new Coordinate(1, 1), 3);
        Pawn pawnW = new Pawn(Color.WHITE, new Coordinate(2, 1), 1);
        Bishop bishopB = new Bishop(Color.BLACK, new Coordinate(8, 1), 3);
        governor.getGame().getFigures().add(bishopB);
        governor.getGame().getFigures().add(bishopW);
        governor.getGame().getFigures().add(pawnW);
        governor.getGame().finalValidMoves(true);
        governor.getGame().cleanFromChessRelatedMoves();

        assertEquals(100, test.score(Color.WHITE, Color.BLACK, governor.getGame()));

        bishopB.getActualPosition().setY(6);
        bishopB.getActualPosition().setX(6);
        governor.getGame().finalValidMoves(true);
        governor.getGame().cleanFromChessRelatedMoves();
        assertEquals(-200, test.score(Color.WHITE, Color.BLACK, governor.getGame()));

        Pawn pawnW2 = new Pawn(Color.WHITE, new Coordinate(2, 4), 1);
        Pawn pawnW3 = new Pawn(Color.WHITE, new Coordinate(2, 5), 1);
        governor.getGame().getFigures().add(pawnW2);
        governor.getGame().getFigures().add(pawnW3);
        governor.getGame().finalValidMoves(true);
        governor.getGame().cleanFromChessRelatedMoves();

        assertEquals(0, test.score(Color.WHITE, Color.BLACK, governor.getGame()));

        Queen queen = new Queen(Color.WHITE, new Coordinate(2, 6), 8);
        governor.getGame().getFigures().add(queen);
        governor.getGame().finalValidMoves(true);
        governor.getGame().cleanFromChessRelatedMoves();
        assertEquals(575, test.score(Color.WHITE, Color.BLACK, governor.getGame()));

        Pawn pawnB = new Pawn(Color.BLACK, new Coordinate(3, 5), 1);
        governor.getGame().getFigures().add(pawnB);
        governor.getGame().finalValidMoves(true);
        governor.getGame().cleanFromChessRelatedMoves();
        assertEquals(-200, test.score(Color.WHITE, Color.BLACK, governor.getGame()));
    }
}

