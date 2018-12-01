package chess;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Pawn extends Figure {

    private boolean lastMoveIsDoubleOpening;


    public Pawn(char color, Coordinate startingPosition, int value) {
        super("Pawn", 'P', color, startingPosition,  value);
        this.lastMoveIsDoubleOpening = false;
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
        setValidMoves(new ValidMove(new Coordinate(1 * colorModifier, 1), true, "Pawn hit"));
        setValidMoves(new ValidMove(new Coordinate(1 * colorModifier, -1), true, "Pawn hit"));

        //előre 1 lépés
        List<Coordinate> coordinatesOfEmptyCells = new ArrayList<>();
        coordinatesOfEmptyCells.add(new Coordinate(1 * colorModifier, 0));
        setValidMoves(new ValidMove(new Coordinate(1 * colorModifier, 0), coordinatesOfEmptyCells));
        //előre 2 lépés
        coordinatesOfEmptyCells = new ArrayList<>();
        coordinatesOfEmptyCells.add(new Coordinate(1 * colorModifier, 0));
        coordinatesOfEmptyCells.add(new Coordinate(2 * colorModifier, 0));
        setValidMoves(new ValidMove(new Coordinate(2 * colorModifier, 0), coordinatesOfEmptyCells, true, "Pawn double move"));


    }
}
