package chess;

import java.util.ArrayList;
import java.util.HashSet;

public class King extends Figure {

    private boolean inChess;
    private boolean wasAlreadyInChess;
    private boolean alredyCastled;
    private HashSet<ValidMove> validMoves;

    public King(char color, Cell startingPosition) {
        super("King", 'K', color, startingPosition, 0);
        this.inChess = false;
        this.wasAlreadyInChess = false;
        this.alredyCastled = false;
        validMoves = new HashSet<ValidMove>();
        setValidMoves();
    }

    public boolean isInChess() {
        return inChess;
    }

    public void setInChess(boolean inChess) {
        this.inChess = inChess;
    }

    public boolean isWasAlreadyInChess() {
        return wasAlreadyInChess;
    }

    public void setWasAlreadyInChess(boolean wasAlreadyInChess) {
        if (!this.wasAlreadyInChess && wasAlreadyInChess)
            this.wasAlreadyInChess = wasAlreadyInChess;
    }

    public boolean isAlredyCastled() {
        return alredyCastled;
    }

    public void setAlredyCastled(boolean alredyCastled) {
        if (!this.alredyCastled && alredyCastled)
            this.alredyCastled = alredyCastled;
    }

    private void setValidMoves() {

        validMoves.add(new ValidMove(new Coordinate(1, -1)));
        validMoves.add(new ValidMove(new Coordinate(1, 0)));
        validMoves.add(new ValidMove(new Coordinate(1, 1)));
        validMoves.add(new ValidMove(new Coordinate(0, -1)));
        validMoves.add(new ValidMove(new Coordinate(0, 1)));
        validMoves.add(new ValidMove(new Coordinate(-1, -1)));
        validMoves.add(new ValidMove(new Coordinate(-1, 0)));
        validMoves.add(new ValidMove(new Coordinate(-1, 1)));

        //TODO the castlng part is missing

    }

    public HashSet<ValidMove> getValidMoves() {
        return validMoves;
    }
}
