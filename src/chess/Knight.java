package chess;

import java.util.ArrayList;
import java.util.HashSet;

public class Knight extends Figure {

    private HashSet<ValidMove> validMoves;


    public Knight(char color, Cell startingPosition, int value) {
        super("Knight", 'N', color, startingPosition, value);
        validMoves = new HashSet<ValidMove>();
        setValidMoves();
    }

    private void setValidMoves() {

        validMoves.add(new ValidMove(new Coordinate(2, -1)));
        validMoves.add(new ValidMove(new Coordinate(2, 1)));
        validMoves.add(new ValidMove(new Coordinate(-2, -1)));
        validMoves.add(new ValidMove(new Coordinate(-2, 1)));

        validMoves.add(new ValidMove(new Coordinate(1, 2)));
        validMoves.add(new ValidMove(new Coordinate(1, -2)));
        validMoves.add(new ValidMove(new Coordinate(-1, 2)));
        validMoves.add(new ValidMove(new Coordinate(-1, -2)));

    }

    public HashSet<ValidMove> getValidMoves() {
        return validMoves;
    }
}
