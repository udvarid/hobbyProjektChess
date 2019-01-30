package chess.evaluations;

import chess.Color;
import chess.Coordinate;
import chess.Governor;
import chess.Pawn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberOfSinglyPawnsTest {

    @Test
    void testScore () {
        Governor governor = new Governor();
        NumberOfSinglyPawns test = new NumberOfSinglyPawns(100);
        assertEquals(0, test.score(Color.WHITE, Color.BLACK, governor.getGame()));

        governor.getGame().cleanTable();

        Pawn pawnW = new Pawn(Color.WHITE, new Coordinate(5, 1), 1);
        Pawn pawnW2 = new Pawn(Color.WHITE, new Coordinate(5, 2), 1);
        Pawn pawnW3 = new Pawn(Color.WHITE, new Coordinate(5, 4), 1);
        Pawn pawnW4 = new Pawn(Color.WHITE, new Coordinate(5, 8), 1);
        governor.getGame().getFigures().add(pawnW);
        governor.getGame().getFigures().add(pawnW2);
        governor.getGame().getFigures().add(pawnW3);
        governor.getGame().getFigures().add(pawnW4);
        governor.getGame().finalValidMoves(true);
        governor.getGame().cleanFromChessRelatedMoves();
        assertEquals(-400, test.score(Color.WHITE, Color.BLACK, governor.getGame()));

        Pawn pawnB = new Pawn(Color.BLACK, new Coordinate(7, 5), 1);
        governor.getGame().getFigures().add(pawnB);
        governor.getGame().finalValidMoves(true);
        governor.getGame().cleanFromChessRelatedMoves();
        assertEquals(-400, test.score(Color.WHITE, Color.BLACK, governor.getGame()));

        pawnB.getActualPosition().setX(3);
        assertEquals(-200, test.score(Color.WHITE, Color.BLACK, governor.getGame()));



        pawnW4.getActualPosition().setY(5);
        assertEquals(200, test.score(Color.WHITE, Color.BLACK, governor.getGame()));


    }
}
