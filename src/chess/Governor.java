package chess;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

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
        boolean wasMove = false;
        Player whoIsNext = playerA;
        game.finalValidMoves(true);
        game.cleanFromChessRelatedMoves();
        while (!endGame) {

            printActualStatus();

            wasMove = actualPlayerIsMoving(whoIsNext);

            endGame = endGameEvaluating(wasMove, whoIsNext);

            whoIsNext = nextPlayerSet(whoIsNext);

        }
    }

    private boolean endGameEvaluating(boolean wasMove, Player whoIsNext) {

        boolean result = false;
        if (!wasMove && enemyKingInChess(whoIsNext)) {
            System.out.println("Checkmate");
            result = true;
        } else if (!wasMove) {
            System.out.println("No valid move, this is a Stalemate");
            result = true;
        } else if (thisIsADraw()) {
            System.out.println("This is a draw");
            result = true;
        }

        return result;
    }

    private boolean enemyKingInChess(Player whoIsNext) {
        game.finalValidMoves(true);
        game.cleanFromChessRelatedMoves();
        Figure enemyKing = findingEnemyKing(whoIsNext);
        return enemyKingIsAttacked(enemyKing);
    }

    private boolean enemyKingIsAttacked(Figure enemyKing) {
        boolean result = false;

        for (ValidMovePair validMovePair : game.getValidmoves()) {
            if (validMovePair.getEnd().equals(enemyKing.getActualPosition())) {
                result = true;
                break;
            }
        }

        return result;
    }

    private Figure findingEnemyKing(Player whoIsNext) {
        Figure result = null;
        for (Figure figure : game.getFigures()) {
            if (figure.getSign() == 'K' && figure.getColor() == whoIsNext.getColor()) {
                result = figure;
                break;
            }
        }
        return result;
    }

    private boolean thisIsADraw() {

        boolean result = false;

        if (this.figureCapturedLastTime - this.round >= 50 ||
                this.pawnMovedLastTime - this.round >= 50) {
            result = true;
        }

        //TODO ha nincs lehetőség matt adásra
        //TODO accepcted draw
        return result;
    }

    private boolean actualPlayerIsMoving(Player whoIsNext) {

        boolean result = false;
        if (whoIsNext.getColor().equals(Color.WHITE)) {
            this.round++;
        }

        //get all of valid moves from Game object
        game.finalValidMoves(true);
        game.cleanFromChessRelatedMoves();
        Set<ValidMovePair> validmovesForThisPlayer = filterValidMoves(game.getValidmoves(), whoIsNext.getColor());
        //if have any, asking a valid one from Player
        if (!validmovesForThisPlayer.isEmpty()) {
            ValidMovePair moveActual = askingValidmove(validmovesForThisPlayer, whoIsNext);
            makeMove(moveActual);
            result = true;
            if (moveActual.getFigure().getSign() == 'P') {
                this.pawnMovedLastTime = this.round;
            }
            handlePromotingIfNecessery(moveActual);

        }


        return result;

    }

    private void handlePromotingIfNecessery(ValidMovePair moveActual) {
        if (canPromote(moveActual)) {
            boolean goodPromoteFormat = false;
            String toPromote = null;
            while (!goodPromoteFormat) {
                System.out.println("What type would you want promote to? Only one character: R/K/B/Q");
                toPromote = scanner.nextLine();
                if (toPromote.length() == 1 && toPromote.matches("[rkbqRKBQ]")) {
                    goodPromoteFormat = true;
                }
            }
            game.promote(moveActual, toPromote.toLowerCase().charAt(0));


        }
    }

    private boolean canPromote(ValidMovePair moveActual) {

        if (moveActual.getFigure().getSign() == 'P') {
            if (moveActual.getFigure().getColor().equals(Color.WHITE) && moveActual.getEnd().getX() == 8 ||
                    moveActual.getFigure().getColor().equals(Color.BLACK) && moveActual.getEnd().getX() == 1) {
                return true;
            }
        }

        return false;
    }


    private void makeMove(ValidMovePair moveActual) {

        if (game.deleteFigure(moveActual.getEnd())) {
            this.figureCapturedLastTime = round;
        }
        ;

        moveActual.getFigure().getActualPosition().setX(moveActual.getEnd().getX());
        moveActual.getFigure().getActualPosition().setY(moveActual.getEnd().getY());


    }

    private ValidMovePair askingValidmove(Set<ValidMovePair> validmovesForThisPlayer, Player whoIsNext) {

        ValidMovePair result = null;
        Coordinate start = null;
        Coordinate end = null;

        if (whoIsNext.getType().equals(PlayerType.HUMAN)) {
            boolean validMoveAlreadyGiven = false;
            while (!validMoveAlreadyGiven) {
                System.out.println(whoIsNext.getColor().toString() + " player, give me a valid move separated by '-', e.g: 'e2-e4'");
                String[] order = scanner.nextLine().split("-");
                try {
                    start = convertToCoordinate(order[0]);
                    end = convertToCoordinate(order[1]);
                } catch (Exception e) {
                }
                result = new ValidMovePair(start, end, null);
                validMoveAlreadyGiven = orderOnTheListOfValidMoves(validmovesForThisPlayer, result);
            }
        }

        return result;

    }

    private boolean orderOnTheListOfValidMoves(Set<ValidMovePair> validmovesForThisPlayer, ValidMovePair result) {

        if (result.getStart() != null & result.getEnd() != null) {
            for (ValidMovePair validMovePair : validmovesForThisPlayer) {
                if (validMovePair.getStart().equals(result.getStart()) && validMovePair.getEnd().equals(result.getEnd())) {
                    result.setFigure(validMovePair.getFigure());
                    return true;
                }
            }
        }

        return false;
    }

    private Coordinate convertToCoordinate(String s) {
        Coordinate result = null;

        int x = 0;
        int y = 0;

        if (s.length() == 2) {
            switch (s.charAt(0)) {
                case 'a':
                    x = 1;
                    break;
                case 'b':
                    x = 2;
                    break;
                case 'c':
                    x = 3;
                    break;
                case 'd':
                    x = 4;
                    break;
                case 'e':
                    x = 5;
                    break;
                case 'f':
                    x = 6;
                    break;
                case 'g':
                    x = 7;
                    break;
                case 'h':
                    x = 8;
                    break;

            }


            try {
                y = Integer.parseInt(s.substring(1, 2));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            if (x >= 1 && y >= 1 && y <= 8) {
                result = new Coordinate(y, x);
            }
        }

        return result;
    }

    private Set<ValidMovePair> filterValidMoves(Set<ValidMovePair> validmoves, Color color) {

        Set<ValidMovePair> result = new HashSet<>();

        for (ValidMovePair validMovePair : validmoves) {
            if (validMovePair.getFigure().getColor().equals(color)) {
                result.add(validMovePair);
            }
        }

        return result;

    }

    private Player nextPlayerSet(Player whoIsNext) {
        if (whoIsNext == playerA) {
            return playerB;
        } else {
            return playerA;
        }
    }


    private void printActualStatus() {
        game.printBoard();
        //game.printValidMoves();
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
