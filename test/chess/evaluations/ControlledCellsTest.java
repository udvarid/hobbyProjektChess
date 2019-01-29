package chess.evaluations;

import chess.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ControlledCellsTest {

    @Test
    void testScan() {
        Governor governor = new Governor();
        ControlledCells test = new ControlledCells(100);
        assertEquals(0, test.score(Color.WHITE, Color.BLACK, governor.getGame()));

        governor.getGame().cleanTable();

        Bishop bishopW = new Bishop(Color.WHITE, new Coordinate(1, 1), 3);
        Pawn pawnW = new Pawn(Color.WHITE, new Coordinate(2, 1), 1);
        Knight knightB = new Knight(Color.BLACK, new Coordinate(8, 1), 3);
        governor.getGame().getFigures().add(knightB);
        governor.getGame().getFigures().add(bishopW);
        governor.getGame().getFigures().add(pawnW);
        governor.getGame().finalValidMoves(true);
        governor.getGame().cleanFromChessRelatedMoves();
        assertEquals(250, test.score(Color.WHITE, Color.BLACK, governor.getGame()));

        Knight knightB2 = new Knight(Color.BLACK, new Coordinate(8, 8), 3);
        governor.getGame().getFigures().add(knightB2);
        governor.getGame().finalValidMoves(true);
        governor.getGame().cleanFromChessRelatedMoves();
        assertEquals(100, test.score(Color.WHITE, Color.BLACK, governor.getGame()));

        Bishop bishopW2 = new Bishop(Color.WHITE, new Coordinate(1, 8), 3);
        governor.getGame().getFigures().add(bishopW2);
        governor.getGame().finalValidMoves(true);
        governor.getGame().cleanFromChessRelatedMoves();
        assertEquals(500, test.score(Color.WHITE, Color.BLACK, governor.getGame()));

        Rook rook = new Rook(Color.WHITE, new Coordinate(3, 4), 3);
        governor.getGame().getFigures().add(rook);
        governor.getGame().finalValidMoves(true);
        governor.getGame().cleanFromChessRelatedMoves();
        assertEquals(1000, test.score(Color.WHITE, Color.BLACK, governor.getGame()));
    }
}
