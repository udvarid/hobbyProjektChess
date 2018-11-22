package chess;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Rook extends Figure {

    private HashSet<ValidMove> validMoves;

    public Rook(char color, Cell startingPosition, int value) {
        super("Rook", 'R', color, startingPosition, value);
        validMoves = new HashSet<ValidMove>();
        setValidMoves();
    }

    private void setValidMoves() {

        //up and down
        for (int i = 1; i <= 7; i++) {
            List<Coordinate> coordinatesOfEmptyCellsUp = new ArrayList<>();
            List<Coordinate> coordinatesOfEmptyCellsDown = new ArrayList<>();
            List<Coordinate> coordinatesOfEmptyCellsRigth = new ArrayList<>();
            List<Coordinate> coordinatesOfEmptyCellsLeft = new ArrayList<>();
            for (int j = 1; j < i; j++) {
                coordinatesOfEmptyCellsUp.add(new Coordinate(j, 0));
                coordinatesOfEmptyCellsDown.add(new Coordinate(-j, 0));
                coordinatesOfEmptyCellsRigth.add(new Coordinate(0,j));
                coordinatesOfEmptyCellsLeft.add(new Coordinate(0,  - j));
            }
            validMoves.add(new ValidMove(new Coordinate(i, 0), coordinatesOfEmptyCellsUp));
            validMoves.add(new ValidMove(new Coordinate(-i, 0), coordinatesOfEmptyCellsDown));
            validMoves.add(new ValidMove(new Coordinate(0, i), coordinatesOfEmptyCellsRigth));
            validMoves.add(new ValidMove(new Coordinate(0, -i), coordinatesOfEmptyCellsLeft));
        }


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
