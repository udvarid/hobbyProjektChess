package chess;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Pawn extends Figure {

    private boolean lastMoveIsDoubleOpening;
    private HashSet<ValidMove> validMoves;


    public Pawn(char color, Cell startingPosition, int value) {
        super("Pawn", 'P', color, startingPosition,  value);
        this.lastMoveIsDoubleOpening = false;
        validMoves = new HashSet<ValidMove>();
        setValidMoves();
    }

    public boolean isLastMoveIsDoubleOpening() {
        return lastMoveIsDoubleOpening;
    }

    public void setLastMoveIsDoubleOpening(boolean lastMoveIsDoubleOpening) {
        if (!this.lastMoveIsDoubleOpening)
            this.lastMoveIsDoubleOpening = lastMoveIsDoubleOpening;
    }

    private void setValidMoves() {

        int colorModifier = getColor() == 'B' ? -1: 1;

        //szimpla ütés
        validMoves.add(new ValidMove(new Coordinate(1 * colorModifier, 1)));
        validMoves.add(new ValidMove(new Coordinate(1 * colorModifier, -1)));

        //előre 1 lépés
        List<Coordinate> coordinatesOfEmptyCells = new ArrayList<>();
        coordinatesOfEmptyCells.add(new Coordinate(1 * colorModifier, 0));
        validMoves.add(new ValidMove(new Coordinate(1 * colorModifier, 0), coordinatesOfEmptyCells));
        //előre 2 lépés
        coordinatesOfEmptyCells.add(new Coordinate(2 * colorModifier, 0));
        validMoves.add(new ValidMove(new Coordinate(2 * colorModifier, 0), coordinatesOfEmptyCells));

        //en passan 1
        coordinatesOfEmptyCells = new ArrayList<>();
        coordinatesOfEmptyCells.add(new Coordinate(1 * colorModifier, 1));
        validMoves.add(new ValidMove(new Coordinate(1 * colorModifier, 1), coordinatesOfEmptyCells, true,"En passan"));

        //en passan 2
        coordinatesOfEmptyCells = new ArrayList<>();
        coordinatesOfEmptyCells.add(new Coordinate(1 * colorModifier, -1));
        validMoves.add(new ValidMove(new Coordinate(1 * colorModifier, -1), coordinatesOfEmptyCells, true,"En passan"));

    }
    public HashSet<ValidMove> getValidMoves() {

        HashSet<ValidMove> modifiedSet = new HashSet<>();
        for (ValidMove validmove : this.validMoves) {
            if (doubleMoveFromStartingPosition(validmove) && stayOnBoard(validmove)) {
                modifiedSet.add(validmove);
            }
        }

        return modifiedSet;
    }

    private boolean stayOnBoard(ValidMove validmove) {

        int actX = getActualPosition().getCoordinate().getX();
        int actY = getActualPosition().getCoordinate().getY();
        int moveX = validmove.getCoordinate().getX();
        int moveY = validmove.getCoordinate().getY();

        if (actX + moveX >= 1 && actX + moveX <= 8 &&
                actY + moveY >= 1 && actY + moveY <= 8)
            return true;

        return false;
    }


    private boolean doubleMoveFromStartingPosition(ValidMove validmove) {
        return !((validmove.getCoordinate().getX() == 2 || validmove.getCoordinate().getX() == -2) &&
                !this.isStillInStartingPosition());
    }
}
