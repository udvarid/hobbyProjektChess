package chess.evaluations;

import chess.*;

public class ValueBasedWithReattack implements Evaluate {

    private int weight;

    @Override
    public int score(Color colorOwn, Color colorEnemy, Game game) {

        int valueOwn = 0;
        int valueEnemy = 0;

        int diff = 0;

        int result = 0;

        for (Figure figure : game.getFigures()) {
            if (figure.getColor() == colorOwn && notAttacked(figure.getActualPosition(), game)) {
                valueOwn += figure.getValue();
            }
            if (figure.getColor() == colorEnemy && notAttacked(figure.getActualPosition(), game)) {
                valueEnemy += figure.getValue();
            }
        }

        diff = valueOwn - valueEnemy;

        if (Math.abs(diff) < 3) {
            result = 100 * diff / Math.abs(diff);
        } else if (Math.abs(diff) < 6) {
            result = 250 * diff / Math.abs(diff);
        } else if (Math.abs(diff) < 11) {
            result = 500 * diff / Math.abs(diff);
        } else if (Math.abs(diff) < 20) {
            result = 750 * diff / Math.abs(diff);
        } else {
            result = 1000 * diff / Math.abs(diff);
        }

        return result;
    }

    private boolean notAttacked(Coordinate actualPosition, Game game) {
        for (ValidMovePair validMovePair : game.getValidmoves()) {
            if (validMovePair.getEnd().equals(actualPosition)) {
                return false;
            }
        }

        return true;
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
