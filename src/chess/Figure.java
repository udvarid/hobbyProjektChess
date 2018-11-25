package chess;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Figure {

    private String name;
    private char sign;
    private char color;
    private boolean isAlive;
    private boolean stillInStartingPosition;
    private Coordinate startingPosition;
    private Coordinate actualPosition;
    private boolean promoted;
    private int value;
    private HashSet<ValidMove> validMoves;

    public Figure(String name, char sign, char color, Coordinate startingPosition, int value, boolean promoted) {
        this.name = name;
        this.sign = sign;
        this.color = color;
        this.isAlive = true;
        this.stillInStartingPosition = true;
        this.startingPosition = startingPosition;
        this.actualPosition = startingPosition;
        this.value = value;
        this.promoted = promoted;
        validMoves = new HashSet<ValidMove>();
    }

    public Figure(String name, char sign, char color, Coordinate startingPosition, int value) {
        this(name, sign, color, startingPosition, value, false);
    }

    public String getName() {
        return name;
    }

    public char getColor() {
        return color;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public char getSign() {
        return sign;
    }

    public boolean isStillInStartingPosition() {
        return stillInStartingPosition;
    }

    public boolean isPromoted() {
        return promoted;
    }

    public Coordinate getStartingPosition() {
        return startingPosition;
    }

    public void setAlive(boolean alive) {
        if (isAlive && !alive)
            isAlive = alive;
    }

    public int getValue() {
        return value;
    }

    public Coordinate getActualPosition() {
        return actualPosition;
    }

    public void setActualPosition(Coordinate actualPosition) {
        this.actualPosition = actualPosition;
    }


    public void setStillInStartingPosition(boolean stillInStartingPosition) {
        if (this.stillInStartingPosition && !stillInStartingPosition)
            this.stillInStartingPosition = stillInStartingPosition;
    }



    public void setValidMoves(ValidMove validMove) {
        this.validMoves.add(validMove);
    }

    public HashSet<ValidMove> getValidMoves() {

        HashSet<ValidMove> modifiedSet = new HashSet<>();
        for (ValidMove validmove : this.validMoves) {
            if (stayOnBoard(validmove)) {
                //giving real validmoves
                int actX = getActualPosition().getX();
                int actY = getActualPosition().getY();
                int moveX = validmove.getCoordinate().getX();
                int moveY = validmove.getCoordinate().getY();
                Coordinate modifiedCoordinate = new Coordinate(actX + moveX, actY + moveY);
                List<Coordinate> modifiedEmptyCells = new ArrayList<>();
                for (Coordinate modCor : validmove.getEmptyCells()) {
                    Coordinate modEmpty = new Coordinate(actX + modCor.getX(), actY + modCor.getY());
                    modifiedEmptyCells.add(modEmpty);
                }
                ValidMove modifiedValidMove = new ValidMove(modifiedCoordinate, modifiedEmptyCells, validmove.isSpecialMove(), validmove.getSpecialMoveType());
                modifiedSet.add(modifiedValidMove);
            }
        }

        return modifiedSet;
    }

    private boolean stayOnBoard(ValidMove validmove) {

        int actX = getActualPosition().getX();
        int actY = getActualPosition().getY();
        int moveX = validmove.getCoordinate().getX();
        int moveY = validmove.getCoordinate().getY();

        if (actX + moveX >= 1 && actX + moveX <= 8 &&
                actY + moveY >= 1 && actY + moveY <= 8)
            return true;

        return false;
    }

}
