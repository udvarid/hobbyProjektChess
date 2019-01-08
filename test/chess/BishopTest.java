package chess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BishopTest {

    @Test
    public void testValidMoves() {

        Bishop bishop;

        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                bishop = new Bishop(Color.WHITE, new Coordinate(i, j), 1);

                for (ValidMove validmove : bishop.getValidMoves()) {
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
        Bishop bishop = new Bishop(Color.WHITE, new Coordinate(1, 1), 3);
        newGame.addFigures(bishop);
        newGame.finalValidMoves(true);
        int numberOfMoves = newGame.getValidmoves().size();
        Assertions.assertEquals(7, numberOfMoves);

        //change location to B2
        bishop.setActualPosition(new Coordinate(2, 2));
        newGame.finalValidMoves(true);
        numberOfMoves = newGame.getValidmoves().size();
        Assertions.assertEquals(9, numberOfMoves);

        //change location to C3
        bishop.setActualPosition(new Coordinate(3, 3));
        newGame.finalValidMoves(true);
        numberOfMoves = newGame.getValidmoves().size();
        Assertions.assertEquals(11, numberOfMoves);

        List<Coordinate> bishopMoves = new ArrayList<>();
        for (ValidMovePair validMovePair : newGame.getValidmoves()) {
            bishopMoves.add(validMovePair.getEnd());
        }
        Assertions.assertTrue(bishopMoves.contains(new Coordinate(2,2)));

        //put one friendly figure to B2
        Knight knight = new Knight(Color.WHITE, new Coordinate(2, 2), 3);
        newGame.addFigures(knight);
        newGame.finalValidMoves(true);
        numberOfMoves = 0;
        bishopMoves = new ArrayList<>();
        for (ValidMovePair validMovePair : newGame.getValidmoves()) {
            if (validMovePair.getStart().equals(new Coordinate(3,3))) {
                numberOfMoves++;
                bishopMoves.add(validMovePair.getEnd());
            }
        }
        Assertions.assertEquals(9, numberOfMoves);
        Assertions.assertFalse(bishopMoves.contains(new Coordinate(2,2)));

        //put an enemy figure to B2
        newGame = new Game();
        newGame.cleanTable();
        bishop = new Bishop(Color.WHITE, new Coordinate(3, 3), 3);
        newGame.addFigures(bishop);

        knight = new Knight(Color.BLACK, new Coordinate(2, 2), 3);
        newGame.addFigures(knight);

        newGame.finalValidMoves(true);
        numberOfMoves = 0;
        bishopMoves = new ArrayList<>();
        for (ValidMovePair validMovePair : newGame.getValidmoves()) {
            if (validMovePair.getStart().equals(new Coordinate(3,3))) {
                numberOfMoves++;
                bishopMoves.add(validMovePair.getEnd());
            }
        }
        Assertions.assertEquals(10, numberOfMoves);
        Assertions.assertTrue(bishopMoves.contains(new Coordinate(2,2)));



    }

}
