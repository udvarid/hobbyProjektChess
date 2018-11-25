package chess;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Bishop extends Figure {



    public Bishop(char color, Coordinate startingPosition, int value) {
        super("Bishop", 'B', color, startingPosition,  value);
        setValidMoves();
    }

    private void setValidMoves() {

        //up and down
        for (int i = 1; i <= 7; i++) {
            List<Coordinate> coordinatesOfEmptyCellsUpRight = new ArrayList<>();
            List<Coordinate> coordinatesOfEmptyCellsUpLeft = new ArrayList<>();
            List<Coordinate> coordinatesOfEmptyCellsDownRigth = new ArrayList<>();
            List<Coordinate> coordinatesOfEmptyCellsDownLeft = new ArrayList<>();
            for (int j = 1; j < i; j++) {
                coordinatesOfEmptyCellsUpRight.add(new Coordinate(j, j));
                coordinatesOfEmptyCellsUpLeft.add(new Coordinate(j, -j));
                coordinatesOfEmptyCellsDownRigth.add(new Coordinate(-j,j));
                coordinatesOfEmptyCellsDownLeft.add(new Coordinate(-j,  - j));
            }
            setValidMoves(new ValidMove(new Coordinate(i, i), coordinatesOfEmptyCellsUpRight));
            setValidMoves(new ValidMove(new Coordinate(i, -i), coordinatesOfEmptyCellsUpLeft));
            setValidMoves(new ValidMove(new Coordinate(-i, i), coordinatesOfEmptyCellsDownRigth));
            setValidMoves(new ValidMove(new Coordinate(-i, -i), coordinatesOfEmptyCellsDownLeft));
        }


    }

}
