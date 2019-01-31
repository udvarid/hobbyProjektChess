package chess;

import chess.evaluations.*;
import com.rits.cloning.Cloner;

import java.util.*;

public class Player {

    private Color color;
    private Color enemyColor;
    private PlayerType type;
    private List<ValidMovePairWithScore> vms = new ArrayList<>();
    private List<ValidMovePairWithScore> vmsEnemy = new ArrayList<>();
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

    public ValidMovePair giveCleverMove(Game game) {
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

                moveIt(validMovePair, gameCopy, this.color);

                List<ValidMovePair> vpFromEnemy = movesFromEnemy(gameCopy);

                int scoreToSet = 0;
                //matt esetek ellenőrzése
                if (containsMatt(vpFromEnemy)) {
                    scoreToSet = -10000;
                    vmsThis.setScore(scoreToSet);
                } else {
                    //ha nicns matt, akkor az összes eseten végigmegyünk és legjobb eseteknél
                    List<Integer> scoreToAvg = new ArrayList<>();
                    for (ValidMovePair vpEnemy : vpFromEnemy) {
                        //klónozni az állást
                        Game game2Copy = cloner.deepClone(gameCopy);
                        //leléptetni az ellenfél lépését

                        moveIt(vpEnemy, game2Copy, this.enemyColor);

                        //majd az összes lehetésges lépésen végigmenni
                        for (ValidMovePair vpFinal : game2Copy.getValidmoves()) {
                            if (vpFinal.isMateTest() && vpFinal.getFigure().getColor() == this.color) {
                                scoreToAvg.add(2000);
                                break;
                            }
                        }
                        //klónozni
                        int maxScoreFinal = Integer.MIN_VALUE;
                        for (ValidMovePair vpFinal : game2Copy.getValidmoves()) {
                            if (vpFinal.getFigure().getColor() == this.color) {

                                Game game3Copy = cloner.deepClone(game2Copy);

                                moveIt(vpFinal, game3Copy, this.color);


                                int scoreToSetFinal = 0;
                                for (Evaluate evaluate : this.evaluates) {
                                    int partScore = evaluate.score(this.color, this.enemyColor, game3Copy) * evaluate.getWeight() / 100;
                                    scoreToSetFinal += partScore;
                                }

                                if (maxScoreFinal < scoreToSetFinal) {
                                    maxScoreFinal = scoreToSetFinal;
                                }


                            }
                        }

                        scoreToAvg.add(maxScoreFinal);

                    }
                    for (Integer score : scoreToAvg) {
                        scoreToSet += score;
                    }
                    scoreToSet /= scoreToAvg.size();
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

        return result;
    }

    private boolean containsMatt(List<ValidMovePair> vpFromEnemy) {
        for (ValidMovePair vp : vpFromEnemy) {
            if (vp.isMateTest()) {
                return true;
            }
        }
        return false;
    }

    private List<ValidMovePair> movesFromEnemy(Game gameCopy) {

        List<ValidMovePair> listEnemyMoves = new ArrayList<>();

        for (ValidMovePair validMovePair : gameCopy.getValidmoves()) {
            if (validMovePair.isMateTest() && validMovePair.getFigure().getColor() == this.enemyColor) {
                listEnemyMoves.add(validMovePair);
                return listEnemyMoves;
            }
        }
        Cloner clonerEnemy = new Cloner();

        vmsEnemy = new ArrayList<>();
        int maxScoreEnemy = Integer.MIN_VALUE;

        for (ValidMovePair validMovePair : gameCopy.getValidmoves()) {
            if (validMovePair.getFigure().getColor() == this.enemyColor) {

                ValidMovePairWithScore vmsThis = new ValidMovePairWithScore(validMovePair);
                vmsEnemy.add(vmsThis);

                Game gameCopyEnemy = clonerEnemy.deepClone(gameCopy);

                moveIt(validMovePair, gameCopyEnemy, this.enemyColor);


                int scoreToSet = 0;
                for (Evaluate evaluate : this.evaluates) {
                    int partScore = evaluate.score(this.enemyColor, this.color, gameCopyEnemy) * evaluate.getWeight() / 100;
                    scoreToSet += partScore;
                }
                vmsThis.setScore(scoreToSet);

                if (maxScoreEnemy < scoreToSet) {
                    maxScoreEnemy = scoreToSet;
                }


            }
        }

        Collections.sort(vmsEnemy);
        Iterator<ValidMovePairWithScore> iterator = vmsEnemy.iterator();
        while (iterator.hasNext()) {
            ValidMovePairWithScore vmsToCheck = iterator.next();
            if (vmsToCheck.getScore() < maxScoreEnemy) {
                iterator.remove();
            }
        }

        for (int i = 0; i < Math.min(vmsEnemy.size(), 3); i++) {
            listEnemyMoves.add(vmsEnemy.get(i).getValidMovePair());
        }

        return listEnemyMoves;


    }

    public ValidMovePair giveMove(Game game) {
        ValidMovePair result = null;
        Cloner cloner = new Cloner();

        for (ValidMovePair validMovePair : game.getValidmoves()) {
            if (validMovePair.isMateTest() && validMovePair.getFigure().getColor() == this.color) {
                return validMovePair;
            }
        }


        vms = new ArrayList<>();
        int maxScore = Integer.MIN_VALUE;
        for (ValidMovePair validMovePair : game.getValidmoves()) {
            if (validMovePair.getFigure().getColor() == this.color) {

                ValidMovePairWithScore vmsThis = new ValidMovePairWithScore(validMovePair);
                vms.add(vmsThis);

                Game gameCopy = cloner.deepClone(game);

                moveIt(validMovePair, gameCopy, this.color);

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

    private void moveIt(ValidMovePair validMovePair, Game gameCopy, Color colorToCheck) {
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
                ((colorToCheck == Color.BLACK && validMovePair.getEnd().getX() == 1) ||
                        (colorToCheck == Color.WHITE && validMovePair.getEnd().getX() == 8))) {
            gameCopy.promote(validMovePair, 'q');
        }

        gameCopy.finalValidMoves(true);
        gameCopy.cleanFromChessRelatedMoves();
    }


    public List<Evaluate> getEvaluates() {
        return evaluates;
    }
}
