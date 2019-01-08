package chess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class RookTest {

    @Test
    public void testValidMoves() {

        Rook rook;

        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                rook = new Rook(Color.WHITE, new Coordinate(i, j), 1);

                for (ValidMove validmove : rook.getValidMoves()) {
                    int getX = validmove.getCoordinate().getX();
                    int getY = validmove.getCoordinate().getY();
                    Assertions.assertEquals(true, getX >= 1 && getX <= 8);
                    Assertions.assertEquals(true, getY >= 1 && getY <= 8);
                    for (Coordinate coordinate : validmove.getEmptyCells()) {
                        int getEmptyX = coordinate.getX();
                        int getEmptyY = coordinate.getY();
                        Assertions.assertEquals(true, getEmptyX >= 1 && getEmptyX <= 8);
                        Assertions.assertEquals(true, getEmptyY >= 1 && getEmptyY <= 8);
                    }
                }

            }
        }


    }

    @Test
    public void testConcreateValidMoves() {
        Game newGame = new Game();
        newGame.cleanTable();
        Rook rook = new Rook(Color.WHITE, new Coordinate(1, 1), 5);
        newGame.addFigures(rook);
        newGame.finalValidMoves(true);
        int numberOfMoves = newGame.getValidmoves().size();
        Assertions.assertEquals(14, numberOfMoves);
        List<Coordinate> rookMoves = new ArrayList<>();
        for (ValidMovePair validMovePair : newGame.getValidmoves()) {
            rookMoves.add(validMovePair.getEnd());
        }
        Assertions.assertTrue(rookMoves.contains(new Coordinate(1,2)));
        Assertions.assertTrue(rookMoves.contains(new Coordinate(2,1)));
        Assertions.assertFalse(rookMoves.contains(new Coordinate(2,2)));

        //change the location to D4
        rook.setActualPosition(new Coordinate(4,4));
        newGame.finalValidMoves(true);
        numberOfMoves = newGame.getValidmoves().size();
        Assertions.assertEquals(14, numberOfMoves);

        //put an enemy to the location of B4
        Rook rook2 = new Rook(Color.BLACK, new Coordinate(2, 4), 5);
        newGame.addFigures(rook2);
        newGame.finalValidMoves(true);
        rookMoves = new ArrayList<>();
        for (ValidMovePair validMovePair : newGame.getValidmoves()) {
            if (validMovePair.getStart().equals(rook.getActualPosition()))
                rookMoves.add(validMovePair.getEnd());
        }
        Assertions.assertEquals(13, rookMoves.size());
        Assertions.assertTrue(rookMoves.contains(new Coordinate(2,4)));
        Assertions.assertFalse(rookMoves.contains(new Coordinate(1,4)));

        //change the enemy figure to friendly one
        newGame.getFigures().remove(rook2);
        Rook rook3 = new Rook(Color.WHITE, new Coordinate(2, 4), 5);
        newGame.addFigures(rook3);
        newGame.finalValidMoves(true);
        rookMoves = new ArrayList<>();
        for (ValidMovePair validMovePair : newGame.getValidmoves()) {
            if (validMovePair.getStart().equals(rook.getActualPosition()))
                rookMoves.add(validMovePair.getEnd());
        }
        Assertions.assertEquals(12, rookMoves.size());
        Assertions.assertFalse(rookMoves.contains(new Coordinate(2,4)));

    }


}
