package chess;


import com.rits.cloning.Cloner;

import java.util.*;

public class Governor {

    private Player playerA;
    private Player playerB;
    private Player whoIsNext;
    private boolean gameIsOn;


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
                //in the JavaFX version this can happen only, when the player is computer
                markMateMoves(whoIsNext.getColor(), game);
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


    boolean makeMove(ValidMovePair moveActual) {

        boolean result = false;

        if (game.deleteFigure(moveActual.getEnd())) {
            this.figureCapturedLastTime = round;
            result = true;
        } else {
            Coordinate capturedDueToEnpassan = giveVictimDueToEnPassan(moveActual);
            if (capturedDueToEnpassan != null && game.deleteFigure(capturedDueToEnpassan)) {
                this.figureCapturedLastTime = round;
                result = true;
            }
        }


        moveActual.getFigure().getActualPosition().setX(moveActual.getEnd().getX());
        moveActual.getFigure().getActualPosition().setY(moveActual.getEnd().getY());

        moveActual.getFigure().setStillInStartingPosition(false);

        setLastDoubleMovesToFalse(moveActual.getFigure().getColor());

        if (moveActual.getFigure().getFigureType() == FigureType.PAWN) {
            Pawn pawn = (Pawn) moveActual.getFigure();
            if (Math.abs(moveActual.getEnd().getX() - moveActual.getStart().getX()) == 2) {
                pawn.setLastMoveIsDoubleOpening(true);
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

    private Coordinate giveVictimDueToEnPassan(ValidMovePair moveActual) {
        Coordinate result = null;

        if (moveActual.getFigure().getFigureType() == FigureType.PAWN &&
                moveActual.getStart().getY() != moveActual.getEnd().getY()) {
            if (moveActual.getFigure().getColor() == Color.WHITE &&
                    moveActual.getStart().getX() == 5) {
                result = new Coordinate(5, moveActual.getEnd().getY());
            } else if (moveActual.getFigure().getColor() == Color.BLACK &&
                    moveActual.getStart().getX() == 4) {
                result = new Coordinate(4, moveActual.getEnd().getY());
            }

        }
        return result;
    }

    private void setLastDoubleMovesToFalse(Color color) {
        for (Figure figure : game.getFigures()) {
            if (figure.getFigureType() == FigureType.PAWN && figure.getColor() == color) {
                Pawn pawnToCheck = (Pawn) figure;
                if (pawnToCheck.isLastMoveIsDoubleOpening()) {
                    pawnToCheck.setLastMoveIsDoubleOpening(false);
                }
            }
        }
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


    boolean thisIsADraw() {

        boolean result = false;

        if (this.figureCapturedLastTime - this.round >= 50 ||
                this.pawnMovedLastTime - this.round >= 50) {
            return true;
        }

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

    boolean enemyKingInChess(Color color) {
        King king = null;
        for (Figure figure : game.getFigures()) {
            if (figure.getFigureType() == FigureType.KING && figure.getColor() == color) {
                king = (King) figure;
                return king.isInChess();
            }
        }
        return false;
    }

    public boolean enemyHasNoValidMoves(Color enemyColor) {
        return filterValidMoves(game.getValidmoves(), enemyColor).isEmpty();
    }

    public boolean enemyInMate(Color enemyColor) {
        return enemyKingInChess(enemyColor) && enemyHasNoValidMoves(enemyColor);
    }

    public static void markMateMoves(Color color, Game game) {


        Cloner cloner = new Cloner();


        for (ValidMovePair validMovePair : game.getValidmoves()) {
            if (validMovePair.getFigure().getColor() == color) {
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

                gameCopy.finalValidMoves(true);
                gameCopy.cleanFromChessRelatedMoves();

                //megnézni, hogy az ellenfél királya sakkban van e és hogy van e valid lépése
                Color enemyColor = color == Color.WHITE ? Color.BLACK : Color.WHITE;
                //kikeresni az ellenfél királyát
                Figure enemyKing = null;
                for (Figure figure : gameCopy.getFigures()) {
                    if (figure.getColor() == enemyColor && figure.getFigureType() == FigureType.KING) {
                        enemyKing = figure;
                        break;
                    }
                }
                //megszámolni, hogy az ellenfélnek mennyi valid lépése van
                //és megnézni, hogy a saját bábuink valamelyike támadja e a királyt
                int numberOfEnemyValidMoves = 0;
                boolean attackingEnemyKing = false;
                for (ValidMovePair vpToCheck : gameCopy.getValidmoves()) {
                    if (vpToCheck.getFigure().getColor() == enemyColor) {
                        numberOfEnemyValidMoves++;
                    } else {
                        if (vpToCheck.getEnd().equals(enemyKing.getActualPosition())) {
                            attackingEnemyKing = true;
                        }
                    }
                }

                if (numberOfEnemyValidMoves == 0 && attackingEnemyKing) {
                    validMovePair.setMateTest(true);
                }


            }
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

    public void setGameIsOn(boolean gameIsOn) {
        this.gameIsOn = gameIsOn;
    }
}
