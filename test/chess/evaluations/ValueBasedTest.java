package chess.evaluations;

import chess.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValueBasedTest {

    @Test
    void testScore() {
        Governor governor = new Governor();
        ValueBased test = new ValueBased(100);
        assertEquals(0, test.score(Color.WHITE, Color.BLACK, governor.getGame()));

        governor.getGame().cleanTable();
        Bishop bishopW = new Bishop(Color.WHITE, new Coordinate(1, 1), 3);
        Pawn pawnW = new Pawn(Color.WHITE, new Coordinate(2, 1), 1);
        Bishop bishopB = new Bishop(Color.BLACK, new Coordinate(8, 1), 3);
        governor.getGame().getFigures().add(bishopB);
        governor.getGame().getFigures().add(bishopW);
        governor.getGame().getFigures().add(pawnW);
        assertEquals(100, test.score(Color.WHITE, Color.BLACK, governor.getGame()));

        Pawn pawnW2 = new Pawn(Color.WHITE, new Coordinate(2, 2), 1);
        governor.getGame().getFigures().add(pawnW2);
        assertEquals(100, test.score(Color.WHITE, Color.BLACK, governor.getGame()));

        Bishop bishopB2 = new Bishop(Color.BLACK, new Coordinate(8, 3), 3);
        governor.getGame().getFigures().add(bishopB2);
        assertEquals(-100, test.score(Color.WHITE, Color.BLACK, governor.getGame()));

        Rook rook = new Rook(Color.WHITE, new Coordinate(3, 3), 5);
        governor.getGame().getFigures().add(rook);
        assertEquals(250, test.score(Color.WHITE, Color.BLACK, governor.getGame()));

        Rook rook2 = new Rook(Color.WHITE, new Coordinate(3, 3), 5);
        governor.getGame().getFigures().add(rook2);
        assertEquals(500, test.score(Color.WHITE, Color.BLACK, governor.getGame()));

        Queen queen = new Queen(Color.WHITE, new Coordinate(3, 3), 8);
        governor.getGame().getFigures().add(queen);
        assertEquals(750, test.score(Color.WHITE, Color.BLACK, governor.getGame()));

        Queen queen2 = new Queen(Color.WHITE, new Coordinate(3, 3), 8);
        governor.getGame().getFigures().add(queen2);
        assertEquals(1000, test.score(Color.WHITE, Color.BLACK, governor.getGame()));

    }
}
