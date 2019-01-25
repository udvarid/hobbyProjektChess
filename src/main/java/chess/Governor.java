package chess;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.util.*;

public class Governor {

    private Player playerA;
    private Player playerB;
    private Player whoIsNext;
    private boolean gameIsOn;

    private static Scanner scanner = new Scanner(System.in);

    private int round;
    private int pawnMovedLastTime;
    private int figureCapturedLastTime;

    private Game game;

    private List<MoveHistory> moveHistory = new ArrayList<>();

    public Governor() {
        this(PlayerType.HUMAN, PlayerType.HUMAN);
    }

    public Governor(PlayerType p1, PlayerType p2) {
        this.playerA = new Player(Color.WHITE, p1);
        this.playerB = new Player(Color.BLACK, p2);
        this.whoIsNext = this.playerA;
        this.round = 0;
        this.pawnMovedLastTime = 0;
        this.figureCapturedLastTime = 0;
        this.game = new Game();
        this.gameIsOn = true;
        game.finalValidMoves(true);
        game.cleanFromChessRelatedMoves();
    }


    boolean actualPlayerIsMoving(Player whoIsNext, ValidMovePair vpFromFX) {

        boolean result = false;
        if (whoIsNext.getColor() == Color.WHITE) {
            this.round++;
        }

        //get all of valid moves from Game object


        Set<ValidMovePair> validmovesForThisPlayer = filterValidMoves(game.getValidmoves(), whoIsNext.getColor());
        //if have any, asking a valid one from Player
        if (!validmovesForThisPlayer.isEmpty()) {
            ValidMovePair moveActual = null;
            if (vpFromFX == null) {
                //in the JavaFX version this can happen onyl, when the player is computer
                moveActual = whoIsNext.giveMove(this.game);
            } else {
                moveActual = vpFromFX;
                for (Figure figure : game.getFigures()) {
                    if (figure.getActualPosition().equals(moveActual.getStart())) {
                        moveActual.setFigure(figure);
                    }
                }
            }
            boolean thereWasAHit = makeMove(moveActual);


            MoveHistory thisMove = new MoveHistory();
            thisMove.setRound(this.round);
            thisMove.setColor(whoIsNext.getColor() == Color.WHITE ? Color.WHITE : Color.BLACK);
            thisMove.setStartCoordinate(new Coordinate(moveActual.getStart().getX(), moveActual.getStart().getY()));
            thisMove.setEndCoordinate(new Coordinate(moveActual.getEnd().getX(), moveActual.getEnd().getY()));
            thisMove.setHitEnemy(thereWasAHit);
            moveHistory.add(thisMove);

            result = true;
            if (moveActual.getFigure().getFigureType() == FigureType.PAWN) {
                this.pawnMovedLastTime = this.round;
            }

        }

        return result;
    }

    private Set<ValidMovePair> filterValidMoves(Set<ValidMovePair> validmoves, Color color) {

        Set<ValidMovePair> result = new HashSet<>();

        for (ValidMovePair validMovePair : validmoves) {
            if (validMovePair.getFigure().getColor() == color) {
                result.add(validMovePair);
            }
        }

        return result;

    }


    Coordinate convertToCoordinate(String s) {
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

            }

            if (x >= 1 && y >= 1 && y <= 8) {
                result = new Coordinate(y, x);
            }
        }

        return result;
    }


    boolean makeMove(ValidMovePair moveActual) {

        boolean result = false;

        if (game.deleteFigure(moveActual.getEnd())) {
            this.figureCapturedLastTime = round;
            result = true;
        }

        moveActual.getFigure().getActualPosition().setX(moveActual.getEnd().getX());
        moveActual.getFigure().getActualPosition().setY(moveActual.getEnd().getY());

        moveActual.getFigure().setStillInStartingPosition(false);
        if (moveActual.getFigure().getFigureType() == FigureType.PAWN) {
            Pawn pawn = (Pawn) moveActual.getFigure();
            if (Math.abs(moveActual.getEnd().getX() - moveActual.getStart().getX()) == 2) {
                pawn.setLastMoveIsDoubleOpening(true);
            } else {
                pawn.setLastMoveIsDoubleOpening(false);
            }
        }

        if (moveActual.getFigure().getFigureType() == FigureType.KING) {
            King king = (King) moveActual.getFigure();
            if (Math.abs(moveActual.getEnd().getY() - moveActual.getStart().getY()) == 2) {
                king.setAlredyCastled(true);
                moveTheRook(moveActual);
            }
        }

        return result;

    }

    private void moveTheRook(ValidMovePair moveActual) {

        int actualColumn = moveActual.getEnd().getY() > moveActual.getStart().getY() ? 8 : 1;
        int newColumn = actualColumn == 8 ? 6 : 4;
        Coordinate coordinateLooked = new Coordinate(moveActual.getStart().getX(), actualColumn);
        Figure rook = null;
        for (Figure figure : this.game.getFigures()) {
            if (figure.getActualPosition().equals(coordinateLooked)) {
                rook = figure;
                break;
            }
        }
        rook.getActualPosition().setY(newColumn);

    }


    boolean canPromote(ValidMovePair moveActual) {

        if (moveActual.getFigure().getFigureType() == FigureType.PAWN) {
            if (moveActual.getFigure().getColor().equals(Color.WHITE) && moveActual.getEnd().getX() == 8 ||
                    moveActual.getFigure().getColor().equals(Color.BLACK) && moveActual.getEnd().getX() == 1) {
                return true;
            }
        }

        return false;
    }

    String endGameEvaluating(boolean wasMove, Player whoIsNext) {

        String result = "OK";
        if (!wasMove && enemyKingInChess(whoIsNext)) {
            result = "Checkmate";
        } else if (!wasMove) {
            result = "No valid move, this is a Stalemate";
        } else if (thisIsADraw()) {
            result = "This is a draw";
        }

        if (!result.equals("OK")) {
            this.gameIsOn = false;
        }

        return result;
    }

    boolean enemyKingInChess(Player whoIsNext) {
        game.finalValidMoves(true);
        game.cleanFromChessRelatedMoves();
        King enemyKing = (King) findingEnemyKing(whoIsNext);

        return enemyKing.isInChess();
    }

    private Figure findingEnemyKing(Player whoIsNext) {
        Figure result = null;
        for (Figure figure : game.getFigures()) {
            if (figure.getFigureType() == FigureType.KING && figure.getColor() == whoIsNext.getColor()) {
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
            return true;
        }

        result = notEnoughMaterial();

        return result;
    }

    boolean notEnoughMaterial() {

        return maxWithLightOfficer(Color.WHITE) && maxWithLightOfficer(Color.BLACK);
    }

    private boolean maxWithLightOfficer(Color color) {

        int numberOfLightOfficer = 0;

        for (Figure figure : game.getFigures()) {
            if (figure.getColor() == color &&
                    (figure.getFigureType() == FigureType.PAWN ||
                            figure.getFigureType() == FigureType.ROOK ||
                            figure.getFigureType() == FigureType.QUEEN)) {
                return false;
            }
            if (figure.getColor() == color &&
                    (figure.getFigureType() == FigureType.KNIGHT ||
                            figure.getFigureType() == FigureType.BISHOP)) {
                numberOfLightOfficer++;
            }

        }

        if (numberOfLightOfficer > 1) {
            return false;
        }

        return true;

    }

    void nextPlayerSet() {
        if (this.whoIsNext == playerA) {
            this.whoIsNext = playerB;
        } else {
            this.whoIsNext = playerA;
        }
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

    public Game getGame() {
        return game;
    }

    public Player getWhoIsNext() {
        return whoIsNext;
    }

    public boolean isGameIsOn() {
        return gameIsOn;
    }

    public List<MoveHistory> getMoveHistory() {
        return moveHistory;
    }
}
