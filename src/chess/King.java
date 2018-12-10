package chess;



public class King extends Figure {

    private boolean inChess;
    private boolean wasAlreadyInChess;
    private boolean alredyCastled;

    public King(char color, Coordinate startingPosition, int value) {
        super("King", 'K', color, startingPosition, value);
        this.inChess = false;
        this.wasAlreadyInChess = false;
        this.alredyCastled = false;
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

        setValidMoves(new ValidMove(new Coordinate(1, -1)));
        setValidMoves(new ValidMove(new Coordinate(1, 0)));
        setValidMoves(new ValidMove(new Coordinate(1, 1)));
        setValidMoves(new ValidMove(new Coordinate(0, -1)));
        setValidMoves(new ValidMove(new Coordinate(0, 1)));
        setValidMoves(new ValidMove(new Coordinate(-1, -1)));
        setValidMoves(new ValidMove(new Coordinate(-1, 0)));
        setValidMoves(new ValidMove(new Coordinate(-1, 1)));
        setValidMoves(new ValidMove(new Coordinate(0, 2),true,"Castling"));
        setValidMoves(new ValidMove(new Coordinate(0, -2),true,"Castling"));


    }

}
