package chess.evaluations;

import chess.*;

public class CanGiveCheck implements Evaluate {

    private int weight;

    public CanGiveCheck(int weight) {
        this.weight = weight;
    }

    @Override
    public int score(Color colorOwn, Color colorEnemy, Game game) {

        for (Figure figure : game.getFigures()) {
            if (figure.getColor() == colorEnemy && figure.getFigureType() == FigureType.KING) {
                King king = (King) figure;
                if (king.isInChess()) {
                    return 1000;
                }
            }
        }

        return 0;

    }

    @Override
    public int getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Can give a check";
    }
}
