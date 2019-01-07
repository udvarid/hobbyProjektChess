package chess;

import java.util.Scanner;

public class Governor {

    private Player playerA;
    private Player playerB;

    private static Scanner scanner = new Scanner(System.in);

    private int round;
    private int pawnMovedLastTime;
    private int figureCapturedLastTime;
    //TODO - a 3szori ismétlődés ellenőrzéséhez elvileg minden kör állását el kellene mentni és körönként csekkolni

    private Game game;

    public Governor() {
        this.playerA = new Player(Color.WHITE, PlayerType.HUMAN);
        this.playerB = new Player(Color.BLACK, PlayerType.HUMAN);
        this.round = 0;
        this.pawnMovedLastTime = 0;
        this.figureCapturedLastTime = 0;
        this.game = new Game();
    }

    public void startGame() {
        boolean endGame = false;
        Player whoIsNext = playerA;
        while (!endGame) {
            printActualStatus();

            actualPlayerIsMoving(whoIsNext);

            nextPlayerSet(whoIsNext);

            endGame = endGameEvaluating();
        }
    }

    private void actualPlayerIsMoving(Player whoIsNext) {

        if (whoIsNext.getColor().equals(Color.WHITE)) {
            this.round++;
        }

        //get all of valid moves from Game object

        //if have any, asking a valid one from Player

        //make the move and making the necessery changes:
        // e.g.: move, capture, promote, administrating

    }

    private void nextPlayerSet(Player whoIsNext) {
        if (whoIsNext.getColor().equals(Color.WHITE)) {
            whoIsNext = playerB;
        } else {
            whoIsNext = playerA;
        }
    }


    private boolean endGameEvaluating() {

        return false;
    }




    private void printActualStatus() {
        game.printBoard();
        game.finalValidMoves(true);
        game.cleanFromChessRelatedMoves();
        game.printValidMoves();
    }

    public Player getPlayerA() {
        return playerA;
    }

    public Player getPlayerB() {
        return playerB;
    }

    public int getRound() {
        return round;
    }

    public int getPawnMovedLastTime() {
        return pawnMovedLastTime;
    }

    public int getFigureCapturedLastTime() {
        return figureCapturedLastTime;
    }

    public Game getGame() {
        return game;
    }
}
