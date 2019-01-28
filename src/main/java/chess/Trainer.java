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

        for (int j = 0; j <= 100; j = j + 10) {


            for (int i = 0; i < NUMBER_OF_PLAYES; i++) {


                coach = new Governor(PlayerType.COMPUTER, PlayerType.COMPUTER);

                for (int k = 0; k < coach.getPlayerA().getEvaluates().size() - 1; k++) {
                    Evaluate evaluate = coach.getPlayerA().getEvaluates().get(k);
                    evaluate.setWeight(evaluate.getWeight() / 100 * j);
                }
                coach.getPlayerA().getEvaluates().get(coach.getPlayerA().getEvaluates().size() - 1).setWeight(100 - j);

                while (coach.isGameIsOn()) {
                    coach.actualPlayerIsMoving(coach.getWhoIsNext(), null);
                    coach.getGame().finalValidMoves(true);
                    coach.getGame().cleanFromChessRelatedMoves();

                    EndGameType endGameType = giveGameStatus();
                    MoveHistory moveHistory = coach.getMoveHistory().get(coach.getMoveHistory().size() - 1);
                    moveHistory.setGiveChess(coach.enemyKingInChess(coach.getWhoIsNext().getEnemyColor()));

                    //System.out.println(coach.getRound() + " - " + coach.getWhoIsNext().getColor() + " - " + moveHistory);

                    if (coach.getRound() > 500) {
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
            if (note.getColorOfLastMover() == Color.WHITE &&
                    note.getEndGameType() == EndGameType.MATE) {
                if (result.containsKey(note.getWeight())) {
                    result.put(note.getWeight(), result.get(note.getWeight()) + 1);
                } else {
                    result.put(note.getWeight(), 1);
                }
            }
        }

        Map<Integer, Integer> newResult = sortMyMap(result);

        for (Map.Entry<Integer, Integer> entry : newResult.entrySet()) {
            System.out.println(entry.getKey() + " - " + (double) (entry.getValue()) / (double) (NUMBER_OF_PLAYES));
        }

    }

    private Map<Integer, Integer> sortMyMap(Map<Integer, Integer> result) {

        Map<Integer, Integer> newResult = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : result.entrySet()) {
            list.add(entry.getValue());
        }

        Collections.sort(list, Collections.reverseOrder());

        for (Integer numb : list) {
            for (Map.Entry<Integer, Integer> entry : result.entrySet()) {
                if (entry.getValue().equals(numb)) {
                    newResult.put(entry.getKey(), entry.getValue());
                }
            }
        }

        return newResult;


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
