package chess;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Figure {


    public Rook(Color color, Coordinate startingPosition, int value) {
        super("Rook", 'R', color, startingPosition, value);
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
            setValidMoves(new ValidMove(new Coordinate(i, 0), coordinatesOfEmptyCellsUp));
            setValidMoves(new ValidMove(new Coordinate(-i, 0), coordinatesOfEmptyCellsDown));
            setValidMoves(new ValidMove(new Coordinate(0, i), coordinatesOfEmptyCellsRigth));
            setValidMoves(new ValidMove(new Coordinate(0, -i), coordinatesOfEmptyCellsLeft));
        }


    }

}
