package chess.evaluations;

import chess.*;

import java.util.HashSet;
import java.util.Set;

public class ControlledCells implements Evaluate {

    private int weight;

    public ControlledCells(int weight) {
        this.weight = weight;
    }

    @Override
    public int score(Color colorOwn, Color colorEnemy, Game game) {
        int diff = 0;
        int result = 0;
        Set<Coordinate> cellsOwn = new HashSet<>();
        Set<Coordinate> cellsEnemy = new HashSet<>();


        for (ValidMovePair validMovePair : game.getValidmoves()) {
            if (validMovePair.getFigure().getColor() == colorOwn) {
                cellsOwn.add(validMovePair.getStart());

                if (validMovePair.getFigure().getFigureType() != FigureType.PAWN) {
                    cellsOwn.add(validMovePair.getEnd());
                }
                if (validMovePair.getFigure().getFigureType() == FigureType.PAWN) {
                    Coordinate coord1 = new Coordinate(validMovePair.getStart().getX() + 1, validMovePair.getStart().getY() + 1);
                    if (coord1.getY() > 0 && coord1.getY() < 9) {
                        cellsOwn.add(coord1);
                    }
                    Coordinate coord2 = new Coordinate(validMovePair.getStart().getX() + 1, validMovePair.getStart().getY() + 1);
                    if (coord2.getY() > 0 && coord2.getY() < 9) {
                        cellsOwn.add(coord2);
                    }
                }
            } else {
                cellsEnemy.add(validMovePair.getStart());
                if (validMovePair.getFigure().getFigureType() != FigureType.PAWN) {
                    cellsEnemy.add(validMovePair.getEnd());
                }
                if (validMovePair.getFigure().getFigureType() == FigureType.PAWN) {
                    Coordinate coord1 = new Coordinate(validMovePair.getStart().getX() - 1, validMovePair.getStart().getY() + 1);
                    if (coord1.getY() > 0 && coord1.getY() < 9) {
                        cellsEnemy.add(coord1);
                    }
                    Coordinate coord2 = new Coordinate(validMovePair.getStart().getX() - 1, validMovePair.getStart().getY() + 1);
                    if (coord2.getY() > 0 && coord2.getY() < 9) {
                        cellsEnemy.add(coord2);
                    }
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

    @Override
    public int getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(int weight) {

        this.weight = weight;

    }
}
