package chess.evaluations;

import chess.Color;
import chess.Figure;
import chess.FigureType;
import chess.Game;

public class DevelopedLightOfficers implements Evaluate {

    private int weight;

    public DevelopedLightOfficers(int weight) {
        this.weight = weight;
    }

    @Override
    public int score(Color colorOwn, Color colorEnemy, Game game) {
        int numberOwn = 0;
        int numberEnemy = 0;
        int score;
        for (Figure figure : game.getFigures()) {
            if (!figure.isStillInStartingPosition() && (figure.getFigureType() == FigureType.BISHOP ||
                    figure.getFigureType() == FigureType.KNIGHT)) {
                if (figure.getColor() == colorOwn) {
                    numberOwn++;
                } else {
                    numberEnemy++;
                }
            }
        }
        score = numberOwn - numberEnemy;
        score = score * 1000 / 4;
        return score;
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
