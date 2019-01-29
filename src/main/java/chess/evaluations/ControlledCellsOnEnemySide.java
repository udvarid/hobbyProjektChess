package chess.evaluations;

import chess.*;

import java.util.HashSet;
import java.util.Set;

public class ControlledCellsOnEnemySide implements Evaluate {

    private int weight;

    public ControlledCellsOnEnemySide(int weight) {
        this.weight = weight;
    }

    @Override
    public int score(Color colorOwn, Color colorEnemy, Game game) {
        int diff = 0;
        int result= 0;
        Set<Coordinate> cellsOwn = new HashSet<>();
        Set<Coordinate> cellsEnemy = new HashSet<>();


        for (ValidMovePair validMovePair : game.getValidmoves()) {
            if (validMovePair.getFigure().getColor() == colorOwn) {
                if (isOnEnemySide(validMovePair.getStart(), colorOwn)) {
                    cellsOwn.add(validMovePair.getStart());
                }
                if (isOnEnemySide(validMovePair.getEnd(), colorOwn) && validMovePair.getFigure().getFigureType() != FigureType.PAWN) {
                    cellsOwn.add(validMovePair.getEnd());
                }
                if (isOnEnemySide(validMovePair.getEnd(), colorOwn) && validMovePair.getFigure().getFigureType() == FigureType.PAWN &&
                        validMovePair.getStart().getY() != validMovePair.getEnd().getY()) {
                    cellsOwn.add(validMovePair.getEnd());
                }
            } else {
                if (isOnEnemySide(validMovePair.getStart(), colorEnemy)) {
                    cellsEnemy.add(validMovePair.getStart());
                }
                if (isOnEnemySide(validMovePair.getEnd(), colorEnemy) && validMovePair.getFigure().getFigureType() != FigureType.PAWN) {
                    cellsEnemy.add(validMovePair.getEnd());
                }
                if (isOnEnemySide(validMovePair.getEnd(), colorEnemy) && validMovePair.getFigure().getFigureType() == FigureType.PAWN &&
                        validMovePair.getStart().getY() != validMovePair.getEnd().getY()) {
                    cellsEnemy.add(validMovePair.getEnd());
                }
            }
        }

        diff = cellsOwn.size() - cellsEnemy.size();
        if (diff != 0) {
            if (Math.abs(diff) < 5) {
                result = 100 * diff / Math.abs(diff);
            } else if (Math.abs(diff) < 10) {
                result = 250 * diff / Math.abs(diff);
            } else if (Math.abs(diff) < 15) {
                result = 500 * diff / Math.abs(diff);
            } else if (Math.abs(diff) < 20) {
                result = 750 * diff / Math.abs(diff);
            } else {
                result = 1000 * diff / Math.abs(diff);
            }
        }


        return result;
    }

    private boolean isOnEnemySide(Coordinate coordinate, Color color) {

        if (color == Color.WHITE && coordinate.getX() > 4 ||
            color == Color.BLACK && coordinate.getX() < 5) {
            return true;
        }

        return false;
    }

    @Override
    public int getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(int weight) {
        this.weight = weight;
    }
}
