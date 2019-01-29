package chess.evaluations;

import chess.Color;
import chess.Coordinate;
import chess.Governor;
import chess.Pawn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PassedPawnTest {

    @Test
    void testScore() {
        Governor governor = new Governor();
        PassedPawn test = new PassedPawn(100);
        assertEquals(0, test.score(Color.WHITE, Color.BLACK, governor.getGame()));

        governor.getGame().cleanTable();

        Pawn pawnW = new Pawn(Color.WHITE, new Coordinate(5, 1), 1);
        Pawn pawnW2 = new Pawn(Color.WHITE, new Coordinate(2, 2), 1);
        Pawn pawnB = new Pawn(Color.BLACK, new Coordinate(6, 2), 1);
        governor.getGame().getFigures().add(pawnW);
        governor.getGame().getFigures().add(pawnW2);
        governor.getGame().getFigures().add(pawnB);

        assertEquals(0, test.score(Color.WHITE, Color.BLACK, governor.getGame()));

        pawnB.getActualPosition().setX(5);
        assertEquals(200, test.score(Color.WHITE, Color.BLACK, governor.getGame()));

        Pawn pawnW3 = new Pawn(Color.WHITE, new Coordinate(4, 3), 1);
        governor.getGame().getFigures().add(pawnW3);
        assertEquals(200, test.score(Color.WHITE, Color.BLACK, governor.getGame()));

        pawnB.getActualPosition().setX(4);
        assertEquals(400, test.score(Color.WHITE, Color.BLACK, governor.getGame()));

        governor.getGame().getFigures().remove(pawnW2);
        assertEquals(200, test.score(Color.WHITE, Color.BLACK, governor.getGame()));




    }
}
