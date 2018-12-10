package chess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class PawnTest {

    @Test
    public void testValidMoves() {

        Pawn pawn;

        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                pawn = new Pawn('W', new Coordinate(i, j), 1);

                for (ValidMove validmove : pawn.getValidMoves()) {
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
        Pawn pawn = new Pawn('W', new Coordinate(2, 2), 1);
        newGame.addFigures(pawn);
        newGame.finalValidMoves(true);
        int numberOfMoves = newGame.getValidmoves().size();
        Assertions.assertEquals(2, numberOfMoves);
        //ha ez a kezdő lépése és van előtte valaki
        Pawn pawn0 = new Pawn('W', new Coordinate(3, 2), 1);
        newGame.addFigures(pawn0);
        newGame.finalValidMoves(true);
        List<Coordinate> pawnMoves = new ArrayList<>();
        for (ValidMovePair validMovePair : newGame.getValidmoves()) {
            if (validMovePair.getStart().equals(pawn.getActualPosition())) {
                pawnMoves.add(validMovePair.getEnd());
            }
        }
        Assertions.assertEquals(0, pawnMoves.size());

        //ha ez nem az első lépése
        newGame.getFigures().remove(pawn0);
        pawn.setActualPosition(new Coordinate(3,2));
        pawn.setStillInStartingPosition(false);
        newGame.finalValidMoves(true);
        pawnMoves = new ArrayList<>();
        for (ValidMovePair validMovePair : newGame.getValidmoves()) {
            if (validMovePair.getStart().equals(pawn.getActualPosition())) {
                pawnMoves.add(validMovePair.getEnd());
            }
        }
        Assertions.assertEquals(1, pawnMoves.size());
        Assertions.assertTrue( pawnMoves.contains(new Coordinate(4,2)));

        //ha van előtte egy ellenség
        Pawn pawn2 = new Pawn('B', new Coordinate(4, 2), 1);
        newGame.addFigures(pawn2);
        newGame.finalValidMoves(true);
        pawnMoves = new ArrayList<>();
        for (ValidMovePair validMovePair : newGame.getValidmoves()) {
            if (validMovePair.getStart().equals(pawn.getActualPosition())) {
                pawnMoves.add(validMovePair.getEnd());
            }
        }
        Assertions.assertEquals(0, pawnMoves.size());

        //ha van előtte egy barát
        newGame.getFigures().remove(pawn2);
        Pawn pawn3 = new Pawn('W', new Coordinate(4, 2), 1);
        newGame.addFigures(pawn3);
        newGame.finalValidMoves(true);
        pawnMoves = new ArrayList<>();
        for (ValidMovePair validMovePair : newGame.getValidmoves()) {
            if (validMovePair.getStart().equals(pawn.getActualPosition())) {
                pawnMoves.add(validMovePair.getEnd());
            }
        }
        Assertions.assertEquals(0, pawnMoves.size());

        //van srégen ellenség
        Pawn pawn4 = new Pawn('B', new Coordinate(4, 3), 1);
        newGame.addFigures(pawn4);
        newGame.finalValidMoves(true);
        pawnMoves = new ArrayList<>();
        for (ValidMovePair validMovePair : newGame.getValidmoves()) {
            if (validMovePair.getStart().equals(pawn.getActualPosition())) {
                pawnMoves.add(validMovePair.getEnd());
            }
        }
        Assertions.assertEquals(1, pawnMoves.size());
        Assertions.assertTrue(pawnMoves.contains(new Coordinate(4,3)));

        //másik srégen is van ellenség
        Pawn pawn5 = new Pawn('B', new Coordinate(4, 1), 1);
        newGame.addFigures(pawn5);
        newGame.finalValidMoves(true);
        pawnMoves = new ArrayList<>();
        for (ValidMovePair validMovePair : newGame.getValidmoves()) {
            if (validMovePair.getStart().equals(pawn.getActualPosition())) {
                pawnMoves.add(validMovePair.getEnd());
            }
        }
        Assertions.assertEquals(2, pawnMoves.size());
        Assertions.assertTrue(pawnMoves.contains(new Coordinate(4,3)));
        Assertions.assertTrue(pawnMoves.contains(new Coordinate(4,1)));

        //egyik srégen van ellenség, másikon barát
        newGame.getFigures().remove(pawn5);
        Pawn pawn6 = new Pawn('W', new Coordinate(4, 1), 1);
        newGame.addFigures(pawn6);
        newGame.finalValidMoves(true);
        pawnMoves = new ArrayList<>();
        for (ValidMovePair validMovePair : newGame.getValidmoves()) {
            if (validMovePair.getStart().equals(pawn.getActualPosition())) {
                pawnMoves.add(validMovePair.getEnd());
            }
        }
        Assertions.assertEquals(1, pawnMoves.size());
        Assertions.assertTrue(pawnMoves.contains(new Coordinate(4,3)));

        //en passan - white
        newGame = new Game();
        newGame.cleanTable();
        pawn = new Pawn('W', new Coordinate(5, 2), 1);
        pawn2 = new Pawn('B', new Coordinate(5, 3), 1);
        pawn.setStillInStartingPosition(false);
        pawn2.setStillInStartingPosition(false);


        newGame.addFigures(pawn);
        newGame.addFigures(pawn2);

        newGame.finalValidMoves(true);

        pawnMoves = new ArrayList<>();
        for (ValidMovePair validMovePair : newGame.getValidmoves()) {
            if (validMovePair.getStart().equals(pawn.getActualPosition())) {
                pawnMoves.add(validMovePair.getEnd());
            }
        }
        Assertions.assertEquals(1, pawnMoves.size());
        Assertions.assertTrue(pawnMoves.contains(new Coordinate(6,2)));


        //if this wasn't the black's last move
        pawn2.setLastMoveIsDoubleOpening(true);
        newGame.finalValidMoves(true);

        pawnMoves = new ArrayList<>();
        for (ValidMovePair validMovePair : newGame.getValidmoves()) {
            if (validMovePair.getStart().equals(pawn.getActualPosition())) {
                pawnMoves.add(validMovePair.getEnd());
            }
        }
        Assertions.assertEquals(2, pawnMoves.size());
        Assertions.assertTrue(pawnMoves.contains(new Coordinate(6,3)));
        Assertions.assertFalse(pawnMoves.contains(new Coordinate(3,3)));

        //if this isn't happening in the right row (5th)
        pawn.setActualPosition(new Coordinate(4, 2));
        pawn2.setActualPosition(new Coordinate(4, 3));
        newGame.finalValidMoves(true);

        pawnMoves = new ArrayList<>();
        for (ValidMovePair validMovePair : newGame.getValidmoves()) {
            if (validMovePair.getStart().equals(pawn.getActualPosition())) {
                pawnMoves.add(validMovePair.getEnd());
            }
        }
        Assertions.assertEquals(1, pawnMoves.size());
        Assertions.assertTrue(pawnMoves.contains(new Coordinate(5,2)));





    }

}
