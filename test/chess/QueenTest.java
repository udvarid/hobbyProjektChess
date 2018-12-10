package chess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class QueenTest {

    @Test
    public void testValidMoves() {

        Queen queen;

        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                queen = new Queen('W', new Coordinate(i, j), 1);

                for (ValidMove validmove : queen.getValidMoves()) {
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
        Queen queen = new Queen('W', new Coordinate(1, 1), 8);
        newGame.addFigures(queen);
        newGame.finalValidMoves(true);
        int numberOfMoves = newGame.getValidmoves().size();
        Assertions.assertEquals(21, numberOfMoves);

        List<Coordinate> rookMoves = new ArrayList<>();
        for (ValidMovePair validMovePair : newGame.getValidmoves()) {
            rookMoves.add(validMovePair.getEnd());
        }
        Assertions.assertTrue(rookMoves.contains(new Coordinate(1,2)));
        Assertions.assertTrue(rookMoves.contains(new Coordinate(2,1)));
        Assertions.assertTrue(rookMoves.contains(new Coordinate(4,4)));
        Assertions.assertFalse(rookMoves.contains(new Coordinate(4,5)));

        //change the position to E5
        queen.setActualPosition(new Coordinate(5, 5));
        newGame.finalValidMoves(true);
        numberOfMoves = newGame.getValidmoves().size();
        Assertions.assertEquals(27, numberOfMoves);

        //put an enemy to the position of C3
        Queen queen2 = new Queen('B', new Coordinate(3, 3), 8);
        newGame.addFigures(queen2);
        newGame.finalValidMoves(true);
        List<Coordinate> queenMoves = new ArrayList<>();
        for (ValidMovePair validMovePair : newGame.getValidmoves()) {
            if (validMovePair.getStart().equals(queen.getActualPosition())) {
                queenMoves.add(validMovePair.getEnd());
            }
        }
        Assertions.assertEquals(25, queenMoves.size());
        Assertions.assertTrue(queenMoves.contains(new Coordinate(3,3)));
        Assertions.assertFalse(queenMoves.contains(new Coordinate(2,2)));

        //change the enemy to a friend
        newGame.getFigures().remove(queen2);
        Queen queen3 = new Queen('W', new Coordinate(3, 3), 8);
        newGame.addFigures(queen3);
        newGame.finalValidMoves(true);
        queenMoves = new ArrayList<>();
        for (ValidMovePair validMovePair : newGame.getValidmoves()) {
            if (validMovePair.getStart().equals(queen.getActualPosition())) {
                queenMoves.add(validMovePair.getEnd());
            }
        }
        Assertions.assertEquals(24, queenMoves.size());
        Assertions.assertFalse(queenMoves.contains(new Coordinate(3,3)));
    }
}
