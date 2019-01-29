package chess.evaluations;

import chess.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DevelopedLightOfficersTest {

    @Test
    void testScore() {
        Governor governor = new Governor();
        DevelopedLightOfficers test = new DevelopedLightOfficers(100);
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
        assertEquals(0, test.score(Color.WHITE, Color.BLACK, governor.getGame()));

        bishopW.getActualPosition().setX(2);
        bishopW.getActualPosition().setY(2);
        bishopW.setStillInStartingPosition(false);
        governor.getGame().finalValidMoves(true);
        governor.getGame().cleanFromChessRelatedMoves();
        assertEquals(250, test.score(Color.WHITE, Color.BLACK, governor.getGame()));

        Knight knightW = new Knight(Color.WHITE, new Coordinate(2, 1), 3);
        governor.getGame().getFigures().add(knightW);
        knightW.setStillInStartingPosition(false);
        governor.getGame().finalValidMoves(true);
        governor.getGame().cleanFromChessRelatedMoves();
        assertEquals(500, test.score(Color.WHITE, Color.BLACK, governor.getGame()));


    }
}
