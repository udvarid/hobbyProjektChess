package chess.evaluations;

import chess.Color;
import chess.Figure;
import chess.FigureType;
import chess.Game;

public class NumberOfSinglyPawns implements Evaluate {

    private int weight;

    public NumberOfSinglyPawns(int weight) {
        this.weight = weight;
    }

    @Override
    public int score(Color colorOwn, Color colorEnemy, Game game) {
        int numberOwn = 0;
        int numberEnemy = 0;
        int diff = 0;
        int score = 0;

        for (Figure figure : game.getFigures()) {
            if (figure.getFigureType() == FigureType.PAWN && figure.getColor() == colorOwn && !hasNeighbour(figure, colorOwn, game)) {
                numberOwn++;
            } else if (figure.getFigureType() == FigureType.PAWN && figure.getColor() == colorEnemy && !hasNeighbour(figure, colorEnemy, game)) {
                numberEnemy++;
            }
        }

        diff = numberOwn - numberEnemy;
        if (diff != 0) {
            if (Math.abs(diff) < 4) {
                score = -200 * Math.abs(diff) * diff / Math.abs(diff);
            } else if (Math.abs(diff) < 6) {
                score = -750 * diff / Math.abs(diff);
            } else {
                score = -1000 * diff / Math.abs(diff);
            }
        }

        return score;
    }

    private boolean hasNeighbour(Figure figure, Color color, Game game) {

        for (Figure fig : game.getFigures()) {
            if (fig.getColor() == color && fig.getFigureType() == FigureType.PAWN &&
                    Math.abs(fig.getActualPosition().getY() - figure.getActualPosition().getY()) == 1) {
                return true;
            }
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
