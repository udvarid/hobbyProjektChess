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
        int score;
        for (Figure figure : game.getFigures()) {
            if (figure.getColor() == colorOwn) {
                resultOwn += figure.getValue();
            } else {
                resultEnemy += figure.getValue();
            }
        }

        //max score is 38
        score = resultOwn - resultEnemy;
        score = score * 1000 / 38;

        return score;
    }

    @Override
    public int getWeight() {
        return weight;
    }

}
