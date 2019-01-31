package chess;

import chess.evaluations.*;
import com.rits.cloning.Cloner;

import java.util.*;

public class Player {

    private Color color;
    private Color enemyColor;
    private PlayerType type;
    private List<ValidMovePairWithScore> vms = new ArrayList<>();
    private Random randomNumber = new Random();
    private List<Evaluate> evaluates = new ArrayList<>();

    public Player(Color color, PlayerType type) {
        this.color = color;
        this.enemyColor = color == Color.BLACK ? Color.WHITE : Color.BLACK;
        this.type = type;

        //evaluates.add(new ValueBased(0));
        //evaluates.add(new ControlledCenters(0));
        //evaluates.add(new ControlledCells(0));
        evaluates.add(new ValueBasedWithReattack(65));
        evaluates.add(new CanGiveCheck(5));
        evaluates.add(new ControlledCellsOnEnemySide(15));
        evaluates.add(new DevelopedLightOfficers(5));
        evaluates.add(new NumberOfSinglyPawns(5));
        evaluates.add(new PassedPawn(5));
    }

    public Color getEnemyColor() {
        return enemyColor;
    }

    public Color getColor() {
        return color;
    }

    public PlayerType getType() {
        return type;
    }

    public ValidMovePair giveMove(Game game) {
        ValidMovePair result = null;

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

                figureToMove.setStillInStartingPosition(false);


                if (figureToMove.getFigureType() == FigureType.PAWN &&
                        ((this.color == Color.BLACK && validMovePair.getEnd().getX() == 1) ||
                                (this.color == Color.WHITE && validMovePair.getEnd().getX() == 8))) {
                    gameCopy.promote(validMovePair, 'q');
                }

                gameCopy.finalValidMoves(true);
                gameCopy.cleanFromChessRelatedMoves();

                int scoreToSet = 0;
                for (Evaluate evaluate : this.evaluates) {
                    int partScore = evaluate.score(this.color, this.enemyColor, gameCopy) * evaluate.getWeight() / 100;
                    scoreToSet += partScore;
                    //System.out.println(validMovePair.toString() + " - " + evaluate.toString() + " - " + partScore);
                }
                vmsThis.setScore(scoreToSet);

                if (maxScore < scoreToSet) {
                    maxScore = scoreToSet;
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

        //System.out.println(maxScore);

        return result;
    }

    public List<Evaluate> getEvaluates() {
        return evaluates;
    }
}
