package chess;

import com.rits.cloning.Cloner;

import java.util.*;

public class Player {

    private Color color;
    private Color enemyColor;
    private PlayerType type;
    private List<ValidMovePairWithScore> vms = new ArrayList<>();
    private Random randomNumber = new Random();

    public Player(Color color, PlayerType type) {
        this.color = color;
        this.enemyColor = color == Color.BLACK ? Color.WHITE : Color.BLACK;
        this.type = type;
    }

    public Color getColor() {
        return color;
    }

    public PlayerType getType() {
        return type;
    }

    public ValidMovePair giveMove(Game game) {
        ValidMovePair result = null;

        Evaluator.markMateMoves(this.color, game);
        for (ValidMovePair validMovePair : game.getValidmoves()) {
            if (validMovePair.isMateTest() && validMovePair.getFigure().getColor() == this.color) {
                return validMovePair;
            }
        }
        Cloner cloner = new Cloner();

        vms = new ArrayList<>();
        int maxScore = Integer.MIN_VALUE;
        for (ValidMovePair validMovePair : game.getValidmoves()) {
            if (validMovePair.getFigure().getColor() == this.color) {
                ValidMovePairWithScore vmsThis = new ValidMovePairWithScore(validMovePair);
                vms.add(vmsThis);

                Game gameCopy = cloner.deepClone(game);

                gameCopy.deleteFigure(validMovePair.getEnd());

                Figure figureToMove = null;
                for (Figure figureSearched : gameCopy.getFigures()) {
                    if (figureSearched.getActualPosition().equals(validMovePair.getStart())) {
                        figureToMove = figureSearched;
                        break;
                    }
                }

                figureToMove.getActualPosition().setX(validMovePair.getEnd().getX());
                figureToMove.getActualPosition().setY(validMovePair.getEnd().getY());

                //TODO ezt majd később finomítani, ha több szempont alapján akarunk értékelni
                int own = Evaluator.evaluateBasedValue(this.color, gameCopy);
                int enemy = Evaluator.evaluateBasedValue(this.enemyColor, gameCopy);
                vmsThis.setScore(own - enemy);
                if (maxScore < (own - enemy)) {
                    maxScore = own - enemy;
                }


            }
        }

        Collections.sort(vms);
        Iterator<ValidMovePairWithScore> iterator = vms.iterator();
        while (iterator.hasNext()) {
            ValidMovePairWithScore vmsToCheck = iterator.next();
            if (vmsToCheck.getScore() < maxScore) {
                iterator.remove();
            }
        }
        result = vms.get(randomNumber.nextInt(vms.size())).getValidMovePair();

        return result;
    }
}
