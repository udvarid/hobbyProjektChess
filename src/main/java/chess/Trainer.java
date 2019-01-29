package chess;

import chess.evaluations.Evaluate;

import java.util.*;

public class Trainer {

    public static final int NUMBER_OF_PLAYES = 10;
    private Governor coach;
    private List<TrainerNote> notes = new ArrayList<>();

    public static void main(String[] args) {
        Trainer trainer = new Trainer();
        trainer.startTraining();
    }

    public void startTraining() {

        int numberOfGames = 1;

        for (int j = 0; j <= 100; j = j + 25) {


            for (int i = 0; i < NUMBER_OF_PLAYES; i++) {


                coach = new Governor(PlayerType.COMPUTER, PlayerType.COMPUTER);

                for (int k = 0; k < coach.getPlayerA().getEvaluates().size() - 1; k++) {
                    Evaluate evaluate = coach.getPlayerA().getEvaluates().get(k);
                    evaluate.setWeight(evaluate.getWeight() * j / 100);
                }
                coach.getPlayerA().getEvaluates().get(coach.getPlayerA().getEvaluates().size() - 1).setWeight(100 - j);

                while (coach.isGameIsOn()) {
                    coach.actualPlayerIsMoving(coach.getWhoIsNext(), null);
                    coach.getGame().finalValidMoves(true);
                    coach.getGame().cleanFromChessRelatedMoves();

                    EndGameType endGameType = giveGameStatus();
                    MoveHistory moveHistory = coach.getMoveHistory().get(coach.getMoveHistory().size() - 1);
                    moveHistory.setGiveChess(coach.enemyKingInChess(coach.getWhoIsNext().getEnemyColor()));

                    System.out.println(numberOfGames + "-" + coach.getRound() + " - " + coach.getWhoIsNext().getColor() + " - " + moveHistory);

                    if (coach.getRound() > 250) {
                        endGameType = EndGameType.TOOLONG;
                    }
                    if (endGameType != EndGameType.NOEND) {
                        coach.setGameIsOn(false);
                        System.out.println(numberOfGames++ + " - " + endGameType.getDisplayName());
                        TrainerNote note = new TrainerNote(endGameType, coach.getWhoIsNext().getColor(), 1, j);
                        fillNotes(note);
                    }

                    coach.nextPlayerSet();
                }
            }
        }


        printResultOfTraining();


    }

    private void printResultOfTraining() {

        System.out.println("--------------------------------------------");
        System.out.println("--------------------------------------------");
        System.out.println("--------------------------------------------");

        Map<Integer, Integer> result = new HashMap<>();

        for (TrainerNote note : notes) {
            System.out.println(note);
            if (note.getColorOfLastMover() == Color.WHITE &&
                    note.getEndGameType() == EndGameType.MATE) {
                result.put(note.getWeight(), note.getNumber());
            }
        }

        for (Map.Entry<Integer, Integer> entry : result.entrySet()) {
            System.out.println(entry.getKey() + " - " + (double) (entry.getValue()) / (double) (NUMBER_OF_PLAYES) * 100);
        }

    }


    private void fillNotes(TrainerNote note) {

        boolean foundOne = false;
        for (TrainerNote trainerNote : notes) {
            if (trainerNote.equals(note)) {
                foundOne = true;
                trainerNote.setNumber(trainerNote.getNumber() + 1);
            }
        }
        if (!foundOne) {
            notes.add(note);
        }


    }


    private EndGameType giveGameStatus() {
        EndGameType endGameType = EndGameType.NOEND;
        if (coach.enemyInMate(coach.getWhoIsNext().getEnemyColor())) {
            endGameType = EndGameType.MATE;
        } else if (coach.enemyHasNoValidMoves(coach.getWhoIsNext().getEnemyColor())) {
            endGameType = EndGameType.NOVALIDMOVES;
        } else if (coach.thisIsADraw()) {
            endGameType = EndGameType.PASSIVGAME;
        } else if (coach.notEnoughMaterial()) {
            endGameType = EndGameType.UNSUFFICIENTMATERIAL;
        }
        return endGameType;
    }


}
