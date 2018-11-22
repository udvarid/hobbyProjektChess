package chess;

import java.util.ArrayList;

public class ValidMove {

    private Coordinate coordinate;
    private ArrayList<Coordinate> emptyCells;
    private boolean specialMove;
    private String specialMoveType;

    public ValidMove(Coordinate coordinate, ArrayList<Coordinate> emptyCells, boolean specialMove, String specialMoveType) {
        this.coordinate = coordinate;
        this.emptyCells = emptyCells;
        this.specialMove = specialMove;
        this.specialMoveType = specialMoveType;
    }

    public ValidMove(Coordinate coordinate, ArrayList<Coordinate> emptyCells) {
        this(coordinate, emptyCells, false, null);
    }

    public ValidMove(Coordinate coordinate) {
        this(coordinate, new ArrayList<Coordinate>(), false, null);
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public ArrayList<Coordinate> getEmptyCells() {
        return emptyCells;
    }

    public boolean isSpecialMove() {
        return specialMove;
    }

    public String getSpecialMoveType() {
        return specialMoveType;
    }
}
