package chess;


public class Knight extends Figure {


    public Knight(Color color, Coordinate startingPosition, int value) {
        super(FigureType.KNIGHT, color, startingPosition, value);
        String picture = color == Color.WHITE ? "KnightWhite.png" : "KnightBlack.png";
        super.setPicture(picture);
        setValidMoves();
    }

    private void setValidMoves() {

        setValidMoves(new ValidMove(new Coordinate(2, -1)));
        setValidMoves(new ValidMove(new Coordinate(2, 1)));
        setValidMoves(new ValidMove(new Coordinate(-2, -1)));
        setValidMoves(new ValidMove(new Coordinate(-2, 1)));

        setValidMoves(new ValidMove(new Coordinate(1, 2)));
        setValidMoves(new ValidMove(new Coordinate(1, -2)));
        setValidMoves(new ValidMove(new Coordinate(-1, 2)));
        setValidMoves(new ValidMove(new Coordinate(-1, -2)));

    }

}
