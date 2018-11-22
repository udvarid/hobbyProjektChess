package chess;

public class Pawn extends Figure {

    private boolean lastMoveIsDoubleOpening;

    public Pawn(char color, Cell startingPosition, int value) {
        super("Pawn", 'P', color, startingPosition,  value);
        this.lastMoveIsDoubleOpening = false;
    }

    public boolean isLastMoveIsDoubleOpening() {
        return lastMoveIsDoubleOpening;
    }

    public void setLastMoveIsDoubleOpening(boolean lastMoveIsDoubleOpening) {
        if (!this.lastMoveIsDoubleOpening)
            this.lastMoveIsDoubleOpening = lastMoveIsDoubleOpening;
    }
}
