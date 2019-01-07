package chess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class KingTest {

    @Test
    public void testValidMoves() {

        King king;

        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                king = new King('W', new Coordinate(i, j), 0);

                for (ValidMove validmove : king.getValidMoves()) {
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
    public void testNormalMoves() {
        Game newGame = new Game();
        newGame.cleanTable();
        King king = new King('W', new Coordinate(1, 5), 1);
        newGame.addFigures(king);
        newGame.finalValidMoves(true);
        int numberOfMoves = newGame.getValidmoves().size();
        Assertions.assertEquals(5, numberOfMoves);

        king.getActualPosition().setY(1);
        newGame.finalValidMoves(true);
        numberOfMoves = newGame.getValidmoves().size();
        Assertions.assertEquals(3, numberOfMoves);

        king.getActualPosition().setY(3);
        king.getActualPosition().setX(3);
        newGame.finalValidMoves(true);
        numberOfMoves = newGame.getValidmoves().size();
        Assertions.assertEquals(8, numberOfMoves);
    }

    @Test
    public void testOfMovingInCaseOfBlock() {
        Game newGame = new Game();
        newGame.cleanTable();
        King king = new King('W', new Coordinate(1, 5), 1);
        Pawn pawn = new Pawn('W', new Coordinate(2, 5), 1);
        newGame.addFigures(king);
        newGame.addFigures(pawn);
        newGame.finalValidMoves(true);
        int numberOfMoves = 0;
        for (ValidMovePair validMovePair : newGame.getValidmoves()) {
            if (validMovePair.getStart().equals(king.getActualPosition())) {
                numberOfMoves++;
            }
        }
        Assertions.assertEquals(4, numberOfMoves);
        Pawn pawn2 = new Pawn('W', new Coordinate(1, 4), 1);
        newGame.addFigures(pawn2);
        newGame.finalValidMoves(true);
        numberOfMoves = 0;
        for (ValidMovePair validMovePair : newGame.getValidmoves()) {
            if (validMovePair.getStart().equals(king.getActualPosition())) {
                numberOfMoves++;
            }
        }
        Assertions.assertEquals(3, numberOfMoves);

    }

    @Test
    public void testNormalCastling() {
        Game newGame = new Game();
        newGame.cleanTable();
        King king = new King('W', new Coordinate(1, 5), 1);
        Pawn pawn = new Pawn('W', new Coordinate(1, 8), 1);
        newGame.addFigures(king);
        newGame.addFigures(pawn);
        newGame.finalValidMoves(true);
        int numberOfMoves = 0;
        for (ValidMovePair validMovePair : newGame.getValidmoves()) {
            if (validMovePair.getStart().equals(king.getActualPosition())) {
                numberOfMoves++;
            }
        }
        Assertions.assertEquals(5, numberOfMoves);

        //change the pawn to a Rook
        newGame.getFigures().remove(pawn);
        Rook rook = new Rook('W', new Coordinate(1, 8), 5);
        Rook rook2 = new Rook('W', new Coordinate(1, 1), 5);
        newGame.addFigures(rook);
        newGame.addFigures(rook2);
        newGame.finalValidMoves(true);
        numberOfMoves = 0;
        List<Coordinate> validMovesOfKing = new ArrayList<>();
        for (ValidMovePair validMovePair : newGame.getValidmoves()) {
            if (validMovePair.getStart().equals(king.getActualPosition())) {
                numberOfMoves++;
                validMovesOfKing.add(validMovePair.getEnd());
            }
        }
        Assertions.assertEquals(7, numberOfMoves);
        Assertions.assertTrue(validMovesOfKing.contains(new Coordinate(1, 7)));
        Assertions.assertTrue(validMovesOfKing.contains(new Coordinate(1, 3)));


    }

    @Test
    public void testCastlingWhenThePathIsNotClear() {
        Game newGame = new Game();
        newGame.cleanTable();
        King king = new King('W', new Coordinate(1, 5), 1);
        Rook rook = new Rook('W', new Coordinate(1, 8), 5);
        Rook rook2 = new Rook('W', new Coordinate(1, 1), 5);
        Pawn pawn = new Pawn('W', new Coordinate(1, 3), 1);
        newGame.addFigures(king);
        newGame.addFigures(rook);
        newGame.addFigures(rook2);
        newGame.addFigures(pawn);
        newGame.finalValidMoves(true);
        int numberOfMoves = 0;
        List<Coordinate> validMovesOfKing = new ArrayList<>();
        for (ValidMovePair validMovePair : newGame.getValidmoves()) {
            if (validMovePair.getStart().equals(king.getActualPosition())) {
                numberOfMoves++;
                validMovesOfKing.add(validMovePair.getEnd());
            }
        }
        Assertions.assertEquals(6, numberOfMoves);
        Pawn pawn2 = new Pawn('W', new Coordinate(1, 7), 1);
        newGame.addFigures(pawn2);
        newGame.finalValidMoves(true);
        numberOfMoves = 0;
        validMovesOfKing = new ArrayList<>();
        for (ValidMovePair validMovePair : newGame.getValidmoves()) {
            if (validMovePair.getStart().equals(king.getActualPosition())) {
                numberOfMoves++;
                validMovesOfKing.add(validMovePair.getEnd());
            }
        }
        Assertions.assertEquals(5, numberOfMoves);


    }

    @Test
    public void TestKingMovementIntoChess() {
        Game newGame = new Game();
        newGame.cleanTable();
        King king = new King('W', new Coordinate(1, 5), 1);
        Rook rook = new Rook('W', new Coordinate(1, 8), 5);
        Rook rook2 = new Rook('W', new Coordinate(1, 1), 5);
        Rook rook3 = new Rook('B', new Coordinate(2, 1), 5);
        newGame.addFigures(king);
        newGame.addFigures(rook);
        newGame.addFigures(rook2);
        newGame.addFigures(rook3);
        newGame.finalValidMoves(true);
        newGame.cleanFromChessRelatedMoves();
        int numberOfMoves = 0;
        List<Coordinate> validMovesOfKing = new ArrayList<>();
        for (ValidMovePair validMovePair : newGame.getValidmoves()) {
            if (validMovePair.getStart().equals(king.getActualPosition())) {
                numberOfMoves++;
                validMovesOfKing.add(validMovePair.getEnd());
            }
        }
        Assertions.assertEquals(4, numberOfMoves);

    }

    @Test
    public void testCastlingWhenThePathIsAttacked() {
        Game newGame = new Game();
        newGame.cleanTable();
        King king = new King('W', new Coordinate(1, 5), 1);
        Rook rook = new Rook('W', new Coordinate(1, 8), 5);
        Rook rook2 = new Rook('W', new Coordinate(1, 1), 5);
        Rook rook3 = new Rook('B', new Coordinate(8, 3), 5);
        newGame.addFigures(king);
        newGame.addFigures(rook);
        newGame.addFigures(rook2);
        newGame.addFigures(rook3);
        newGame.finalValidMoves(true);
        newGame.cleanFromChessRelatedMoves();
        int numberOfMoves = 0;
        List<Coordinate> validMovesOfKing = new ArrayList<>();
        for (ValidMovePair validMovePair : newGame.getValidmoves()) {
            if (validMovePair.getStart().equals(king.getActualPosition())) {
                numberOfMoves++;
                validMovesOfKing.add(validMovePair.getEnd());
            }
        }
        Assertions.assertEquals(6, numberOfMoves);

        Bishop bishop = new Bishop('B', new Coordinate(3, 8), 3);
        newGame.addFigures(bishop);
        newGame.finalValidMoves(true);
        newGame.cleanFromChessRelatedMoves();
        numberOfMoves = 0;
        validMovesOfKing = new ArrayList<>();
        for (ValidMovePair validMovePair : newGame.getValidmoves()) {
            if (validMovePair.getStart().equals(king.getActualPosition())) {
                numberOfMoves++;
                validMovesOfKing.add(validMovePair.getEnd());
            }
        }
        Assertions.assertEquals(4, numberOfMoves);
    }

    @Test
    public void testCastlingWhenKingInChess() {
        Game newGame = new Game();
        newGame.cleanTable();
        King king = new King('W', new Coordinate(1, 5), 1);
        Rook rook = new Rook('W', new Coordinate(1, 8), 5);
        Rook rook2 = new Rook('W', new Coordinate(1, 1), 5);
        Rook rook3 = new Rook('B', new Coordinate(8, 5), 5);
        newGame.addFigures(king);
        newGame.addFigures(rook);
        newGame.addFigures(rook2);
        newGame.addFigures(rook3);
        newGame.finalValidMoves(true);
        newGame.cleanFromChessRelatedMoves();
        int numberOfMoves = 0;
        List<Coordinate> validMovesOfKing = new ArrayList<>();
        for (ValidMovePair validMovePair : newGame.getValidmoves()) {
            if (validMovePair.getStart().equals(king.getActualPosition())) {
                numberOfMoves++;
                System.out.println(validMovePair.getEnd());
                validMovesOfKing.add(validMovePair.getEnd());
            }
        }
        Assertions.assertEquals(4, numberOfMoves);
    }
}