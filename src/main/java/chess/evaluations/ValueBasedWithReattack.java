package chess.evaluations;

import chess.*;

public class ValueBasedWithReattack implements Evaluate {

    public ValueBasedWithReattack(int weight) {
        this.weight = weight;
    }

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
            if (figure.getColor() == colorEnemy) {
                valueEnemy += figure.getValue();
            }
        }

        diff = valueOwn - valueEnemy;

        if (diff != 0) {
            if (Math.abs(diff) < 3) {
                result = 100 * Math.abs(diff) * (diff / Math.abs(diff));
            } else if (Math.abs(diff) < 6) {
                result = 200 + 75 * (Math.abs(diff) - 2) * (diff / Math.abs(diff));
            } else if (Math.abs(diff) < 11) {
                result = 425 + 50 * (Math.abs(diff) - 5) * (diff / Math.abs(diff));
            } else if (Math.abs(diff) < 20) {
                result = 675 + 25 * (Math.abs(diff) - 10) * (diff / Math.abs(diff));
            } else {
                result = 1000 * diff / Math.abs(diff);
            }
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
