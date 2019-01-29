package chess.evaluations;

import chess.Color;
import chess.Figure;
import chess.Game;

public class ValueBased implements Evaluate {

    private int weight;

    public ValueBased(int weight) {
        this.weight = weight;
    }

    @Override
    public int score(Color colorOwn, Color colorEnemy, Game game) {
        int resultOwn = 0;
        int resultEnemy = 0;
        int diff = 0;

        int result = 0;
        for (Figure figure : game.getFigures()) {
            if (figure.getColor() == colorOwn) {
                resultOwn += figure.getValue();
            } else {
                resultEnemy += figure.getValue();
            }
        }

        diff = resultOwn - resultEnemy;

        if (diff != 0) {
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
        }


        return result;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public void setWeight(int weight) {
        this.weight = weight;
    }
}
