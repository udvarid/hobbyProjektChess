package chess;

import java.util.ArrayList;

public class Knight extends Figure {

    private ArrayList<ValidMoves> validMoves;

    public Knight(char color, Cell startingPosition, int value) {
        super("Knight", 'N', color, startingPosition, value);
        validMoves = new ArrayList<ValidMoves>();
        setValidMoves();
    }

    private void setValidMoves() {

        validMoves.add(new ValidMoves(new Coordinate(2, -1)));
        validMoves.add(new ValidMoves(new Coordinate(2, 1)));
        validMoves.add(new ValidMoves(new Coordinate(-2, -1)));
        validMoves.add(new ValidMoves(new Coordinate(-2, 1)));

        validMoves.add(new ValidMoves(new Coordinate(1, 2)));
        validMoves.add(new ValidMoves(new Coordinate(1, -2)));
        validMoves.add(new ValidMoves(new Coordinate(-1, 2)));
        validMoves.add(new ValidMoves(new Coordinate(-1, -2)));

    }

}
