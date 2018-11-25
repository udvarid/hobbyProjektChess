package chess;

import java.util.ArrayList;
import java.util.HashSet;

public class Knight extends Figure {


    public Knight(char color, Coordinate startingPosition, int value) {
        super("Knight", 'N', color, startingPosition, value);
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
