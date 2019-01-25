package chess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GovernorTest {

    @Test
    public void testConvertToCoordinate() {
        Governor governor = new Governor();
        Coordinate aim = new Coordinate(2, 2);
        Coordinate converted = governor.convertToCoordinate("b2");
        assertTrue(aim.equals(converted));

        aim = new Coordinate(4, 4);
        converted = governor.convertToCoordinate("d4");
        assertTrue(aim.equals(converted));

        aim = new Coordinate(7, 5);
        converted = governor.convertToCoordinate("e7");
        assertTrue(aim.equals(converted));

        aim = new Coordinate(7, 8);
        converted = governor.convertToCoordinate("h7");
        assertTrue(aim.equals(converted));
    }

    @Test
    public void testMakeMovePawn() {
        Governor governor = new Governor();
        Figure figure = null;
        for (Figure fig : governor.getGame().getFigures()) {
            if (fig.getActualPosition().equals(new Coordinate(2, 5))) {
                figure = fig;
            }
        }
        Pawn pawn = (Pawn) figure;
        assertTrue(pawn.isStillInStartingPosition());
        assertFalse(pawn.isLastMoveIsDoubleOpening());
        ValidMovePair vp = new ValidMovePair(new Coordinate(2, 5), new Coordinate(4, 5), figure);
        governor.makeMove(vp);
        assertFalse(figure.isStillInStartingPosition());
        assertTrue(pawn.isLastMoveIsDoubleOpening());

    }

    @Test
    public void testMakeMoveKing() {
        Governor governor = new Governor();
        governor.getGame().cleanTable();
        King king = new King(Color.WHITE, new Coordinate(1, 5), 1);
        Rook rook = new Rook(Color.WHITE, new Coordinate(1, 8), 5);
        governor.getGame().addFigures(king);
        governor.getGame().addFigures(rook);
        assertTrue(king.isStillInStartingPosition());
        assertFalse(king.isAlredyCastled());
        ValidMovePair vp = new ValidMovePair(new Coordinate(1, 5), new Coordinate(1, 7), king);
        governor.makeMove(vp);
        assertFalse(king.isStillInStartingPosition());
        assertTrue(king.isAlredyCastled());

    }

    @Test
    public void testCanPromote() {
        Governor governor = new Governor();
        governor.getGame().cleanTable();
        Pawn pawn = new Pawn(Color.WHITE, new Coordinate(7, 4), 1);
        Pawn pawn2 = new Pawn(Color.WHITE, new Coordinate(6, 5), 1);
        governor.getGame().addFigures(pawn);
        governor.getGame().addFigures(pawn2);
        ValidMovePair vp1 = new ValidMovePair(new Coordinate(7, 4), new Coordinate(8, 4), pawn);
        ValidMovePair vp2 = new ValidMovePair(new Coordinate(6, 5), new Coordinate(7, 5), pawn2);
        assertTrue(governor.canPromote(vp1));
        assertFalse(governor.canPromote(vp2));
    }

    @Test
    public void testEnemyKingInChess() {
        Governor governor = new Governor();
        governor.getGame().cleanTable();
        Pawn pawnWhite = new Pawn(Color.WHITE, new Coordinate(7, 4), 1);
        Pawn pawnBlack = new Pawn(Color.BLACK, new Coordinate(4, 1), 1);
        King kingWhite = new King(Color.WHITE, new Coordinate(3, 1), 1);
        King kingBlack = new King(Color.BLACK, new Coordinate(8, 5), 1);
        governor.getGame().addFigures(pawnWhite);
        governor.getGame().addFigures(pawnBlack);
        governor.getGame().addFigures(kingWhite);
        governor.getGame().addFigures(kingBlack);

        governor.getGame().finalValidMoves(true);
        governor.getGame().cleanFromChessRelatedMoves();


        assertFalse(governor.enemyKingInChess(governor.getPlayerA()));
        assertTrue(governor.enemyKingInChess(governor.getPlayerB()));

    }

    @Test
    public void testNotEnoughMaterialVersion1() {
        Governor governor = new Governor();
        governor.getGame().cleanTable();
        Pawn pawnWhite = new Pawn(Color.WHITE, new Coordinate(7, 4), 1);
        Pawn pawnBlack = new Pawn(Color.BLACK, new Coordinate(4, 1), 1);
        King kingWhite = new King(Color.WHITE, new Coordinate(3, 1), 1);
        King kingBlack = new King(Color.BLACK, new Coordinate(8, 5), 1);
        governor.getGame().addFigures(pawnWhite);
        governor.getGame().addFigures(pawnBlack);
        governor.getGame().addFigures(kingWhite);
        governor.getGame().addFigures(kingBlack);

        assertFalse(governor.notEnoughMaterial());

    }

    @Test
    public void testNotEnoughMaterialVersion2() {
        Governor governor = new Governor();
        governor.getGame().cleanTable();
        Pawn pawnWhite = new Pawn(Color.WHITE, new Coordinate(7, 4), 1);
        Rook rookBlack = new Rook(Color.BLACK, new Coordinate(4, 1), 1);
        King kingWhite = new King(Color.WHITE, new Coordinate(3, 1), 1);
        King kingBlack = new King(Color.BLACK, new Coordinate(8, 5), 1);
        governor.getGame().addFigures(pawnWhite);
        governor.getGame().addFigures(rookBlack);
        governor.getGame().addFigures(kingWhite);
        governor.getGame().addFigures(kingBlack);

        assertFalse(governor.notEnoughMaterial());

    }

    @Test
    public void testNotEnoughMaterialVersion3() {
        Governor governor = new Governor();
        governor.getGame().cleanTable();
        Bishop bishopWhite = new Bishop(Color.WHITE, new Coordinate(7, 4), 1);
        Knight knightBlack = new Knight(Color.BLACK, new Coordinate(4, 1), 1);
        King kingWhite = new King(Color.WHITE, new Coordinate(3, 1), 1);
        King kingBlack = new King(Color.BLACK, new Coordinate(8, 5), 1);
        governor.getGame().addFigures(bishopWhite);
        governor.getGame().addFigures(knightBlack);
        governor.getGame().addFigures(kingWhite);
        governor.getGame().addFigures(kingBlack);

        assertTrue(governor.notEnoughMaterial());

    }

    @Test
    public void testNotEnoughMaterialVersion4() {
        Governor governor = new Governor();
        governor.getGame().cleanTable();
        Bishop bishopWhite = new Bishop(Color.WHITE, new Coordinate(7, 4), 1);
        Knight knightWhite = new Knight(Color.WHITE, new Coordinate(4, 1), 1);
        King kingWhite = new King(Color.WHITE, new Coordinate(3, 1), 1);
        King kingBlack = new King(Color.BLACK, new Coordinate(8, 5), 1);
        governor.getGame().addFigures(bishopWhite);
        governor.getGame().addFigures(knightWhite);
        governor.getGame().addFigures(kingWhite);
        governor.getGame().addFigures(kingBlack);

        assertFalse(governor.notEnoughMaterial());

    }

}
