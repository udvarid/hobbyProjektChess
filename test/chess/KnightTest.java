package chess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KnightTest {

    @Test
    public void testValidMoves() {

        Knight knight;

        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                knight = new Knight('W', new Coordinate(i, j), 1);

                for (ValidMove validmove : knight.getValidMoves()) {
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
        Knight knight = new Knight('W', new Coordinate(1, 1), 3);
        newGame.addFigures(knight);
        newGame.finalValidMoves(true);
        int numberOfMoves = newGame.getValidmoves().size();
        Assertions.assertEquals(2, numberOfMoves);
        List<Coordinate> knightMoves = new ArrayList<>();
        for (ValidMovePair validMovePair : newGame.getValidmoves()) {
            knightMoves.add(validMovePair.getEnd());
        }
        Assertions.assertTrue(knightMoves.contains(new Coordinate(2,3)));
        Assertions.assertTrue(knightMoves.contains(new Coordinate(3,2)));


        //change the location to C3
        knight.setActualPosition(new Coordinate(3, 3));
        newGame.finalValidMoves(true);
        numberOfMoves = newGame.getValidmoves().size();
        Assertions.assertEquals(8, numberOfMoves);

        //put an enemy figure to the table
        Knight knight2 = new Knight('B', new Coordinate(1, 2), 3);
        newGame.addFigures(knight2);
        newGame.finalValidMoves(true);
        knightMoves = new ArrayList<>();
        for (ValidMovePair validMovePair : newGame.getValidmoves()) {
            if (validMovePair.getStart().equals(knight.getActualPosition()))
                knightMoves.add(validMovePair.getEnd());
        }
        Assertions.assertEquals(8, knightMoves.size());

        //put a friendly unit to the table;
        newGame.getFigures().remove(knight2);
        Knight knight3 = new Knight('W', new Coordinate(1, 2), 3);
        newGame.addFigures(knight3);
        newGame.finalValidMoves(true);
        knightMoves = new ArrayList<>();
        for (ValidMovePair validMovePair : newGame.getValidmoves()) {
            if (validMovePair.getStart().equals(knight.getActualPosition()))
                knightMoves.add(validMovePair.getEnd());
        }
        Assertions.assertEquals(7, knightMoves.size());
        Assertions.assertFalse(knightMoves.contains(new Coordinate(1,2)));

    }
}
