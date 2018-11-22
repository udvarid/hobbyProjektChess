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

        HashSet<ValidMove> modifiedSet = new HashSet<>();
        for (ValidMove validmove : this.validMoves) {
            if (stayOnBoard(validmove)) {
                modifiedSet.add(validmove);
            }
        }

        return modifiedSet;
    }

    private boolean stayOnBoard(ValidMove validmove) {

        int actX = getActualPosition().getCoordinate().getX();
        int actY = getActualPosition().getCoordinate().getY();
        int moveX = validmove.getCoordinate().getX();
        int moveY = validmove.getCoordinate().getY();

        if (actX + moveX >= 1 && actX + moveX <= 8 &&
                actY + moveY >= 1 && actY + moveY <= 8)
            return true;

        return false;
    }
}
