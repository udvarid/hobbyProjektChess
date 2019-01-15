package chess;

import java.util.ArrayList;
import java.util.List;

public class ValidMove {

    private Coordinate coordinate;
    private List<Coordinate> emptyCells;
    private boolean specialMove;
    private MoveType specialMoveType;

    public ValidMove(Coordinate coordinate, List<Coordinate> emptyCells, boolean specialMove, MoveType specialMoveType) {
        this.coordinate = coordinate;
        this.emptyCells = emptyCells;
        this.specialMove = specialMove;
        this.specialMoveType = specialMoveType;
    }

    public ValidMove(Coordinate coordinate, List<Coordinate> emptyCells) {
        this(coordinate, emptyCells, false, null);
    }

    public ValidMove(Coordinate coordinate, boolean specialMove, MoveType specialMoveType) {
        this(coordinate, new ArrayList<Coordinate>(), specialMove, specialMoveType);
    }

    public ValidMove(Coordinate coordinate) {

        this(coordinate, new ArrayList<Coordinate>(), false, null);
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public List<Coordinate> getEmptyCells() {
        return emptyCells;
    }

    public boolean isSpecialMove() {
        return specialMove;
    }

    public MoveType getSpecialMoveType() {
        return specialMoveType;
    }
}
