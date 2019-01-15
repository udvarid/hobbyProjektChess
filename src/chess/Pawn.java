package chess;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Figure {

    private boolean lastMoveIsDoubleOpening;


    public Pawn(Color color, Coordinate startingPosition, int value) {
        super(FigureType.PAWN, color, startingPosition, value);
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

        int colorModifier = getColor().equals(Color.BLACK) ? -1 : 1;

        //szimpla ütés
        setValidMoves(new ValidMove(new Coordinate(1 * colorModifier, 1), true, MoveType.PAWN_HIT));
        setValidMoves(new ValidMove(new Coordinate(1 * colorModifier, -1), true, MoveType.PAWN_HIT));

        //előre 1 lépés
        List<Coordinate> coordinatesOfEmptyCells = new ArrayList<>();
        coordinatesOfEmptyCells.add(new Coordinate(1 * colorModifier, 0));
        setValidMoves(new ValidMove(new Coordinate(1 * colorModifier, 0), coordinatesOfEmptyCells));
        //előre 2 lépés
        coordinatesOfEmptyCells = new ArrayList<>();
        coordinatesOfEmptyCells.add(new Coordinate(1 * colorModifier, 0));
        coordinatesOfEmptyCells.add(new Coordinate(2 * colorModifier, 0));
        setValidMoves(new ValidMove(new Coordinate(2 * colorModifier, 0), coordinatesOfEmptyCells, true, MoveType.PAWN_DOUBLE));


    }
}
