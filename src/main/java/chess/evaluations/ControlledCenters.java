package chess.evaluations;

import chess.*;

import java.util.HashSet;
import java.util.Set;

public class ControlledCenters implements Evaluate {

    private int weight;

    public ControlledCenters(int weight) {
        this.weight = weight;
    }

    @Override
    public int score(Color colorOwn, Color colorEnemy, Game game) {
        int score;
        Set<Coordinate> centersOwn = new HashSet<>();
        Set<Coordinate> centersEnemy = new HashSet<>();


        for (ValidMovePair validMovePair : game.getValidmoves()) {
            if (validMovePair.getFigure().getColor() == colorOwn) {
                if (isCenter(validMovePair.getStart())) {
                    centersOwn.add(validMovePair.getStart());
                }
                if (isCenter(validMovePair.getEnd()) && validMovePair.getFigure().getFigureType() != FigureType.PAWN) {
                    centersOwn.add(validMovePair.getEnd());
                }
                if (isCenter(validMovePair.getEnd()) && validMovePair.getFigure().getFigureType() == FigureType.PAWN &&
                        validMovePair.getStart().getY() != validMovePair.getEnd().getY()) {
                    centersOwn.add(validMovePair.getEnd());
                }
            } else {
                if (isCenter(validMovePair.getStart())) {
                    centersEnemy.add(validMovePair.getStart());
                }
                if (isCenter(validMovePair.getEnd()) && validMovePair.getFigure().getFigureType() != FigureType.PAWN) {
                    centersEnemy.add(validMovePair.getEnd());
                }
                if (isCenter(validMovePair.getEnd()) && validMovePair.getFigure().getFigureType() == FigureType.PAWN &&
                        validMovePair.getStart().getY() != validMovePair.getEnd().getY()) {
                    centersEnemy.add(validMovePair.getEnd());
                }
            }
        }

        //max score is 4
        score = centersOwn.size() - centersEnemy.size();
        score = score * 1000 / 4;

        return score;
    }

    private boolean isCenter(Coordinate start) {
        Coordinate center1 = new Coordinate(4, 4);
        if (center1.equals(start)) {
            return true;
        }
        Coordinate center2 = new Coordinate(4, 5);
        if (center2.equals(start)) {
            return true;
        }
        Coordinate center3 = new Coordinate(5, 4);
        if (center3.equals(start)) {
            return true;
        }
        Coordinate center4 = new Coordinate(5, 5);
        if (center4.equals(start)) {
            return true;
        }
        return false;
    }

    @Override
    public int getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(int weight) {
        this.weight = weight;
    }
}
