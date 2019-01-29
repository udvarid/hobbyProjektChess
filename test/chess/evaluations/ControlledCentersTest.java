package chess.evaluations;

import chess.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ControlledCentersTest {

    @Test
    void testScan() {
        Governor governor = new Governor();
        ControlledCenters test = new ControlledCenters(100);
        //assertEquals(0, test.score(Color.WHITE, Color.BLACK, governor.getGame()));

        governor.getGame().cleanTable();

        Pawn pawnW = new Pawn(Color.WHITE, new Coordinate(3, 4), 1);
        Pawn pawnB = new Pawn(Color.BLACK, new Coordinate(7, 4), 1);
        governor.getGame().getFigures().add(pawnW);
        governor.getGame().getFigures().add(pawnB);
        pawnW.setStillInStartingPosition(false);
        pawnB.setStillInStartingPosition(false);
        governor.getGame().finalValidMoves(true);
        governor.getGame().cleanFromChessRelatedMoves();
        assertEquals(250, test.score(Color.WHITE, Color.BLACK, governor.getGame()));

        pawnW.getActualPosition().setX(4);
        governor.getGame().finalValidMoves(true);
        governor.getGame().cleanFromChessRelatedMoves();
        assertEquals(500, test.score(Color.WHITE, Color.BLACK, governor.getGame()));

        pawnB.getActualPosition().setX(6);
        governor.getGame().finalValidMoves(true);
        governor.getGame().cleanFromChessRelatedMoves();
        assertEquals(250, test.score(Color.WHITE, Color.BLACK, governor.getGame()));
    }
}
