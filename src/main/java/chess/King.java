package chess;



public class King extends Figure {

    private boolean inChess;
    private boolean alredyCastled;

    public King(Color color, Coordinate startingPosition, int value) {
        super(FigureType.KING, color, startingPosition, value);
        this.inChess = false;
        this.alredyCastled = false;
        String picture = color == Color.WHITE ? "KingWhite.png" : "KingBlack.png";
        super.setPicture(picture);
        setValidMoves();
    }

    public boolean isInChess() {
        return inChess;
    }

    public void setInChess(boolean inChess) {
        this.inChess = inChess;
    }


    public boolean isAlredyCastled() {
        return alredyCastled;
    }

    public void setAlredyCastled(boolean alredyCastled) {
        if (!this.alredyCastled && alredyCastled)
            this.alredyCastled = true;
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
        setValidMoves(new ValidMove(new Coordinate(0, 2),true,MoveType.CASTLING));
        setValidMoves(new ValidMove(new Coordinate(0, -2),true,MoveType.CASTLING));


    }

}
