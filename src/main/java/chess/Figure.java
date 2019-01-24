package chess;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Figure implements Cloneable{

    private FigureType figureType;
    private Color color;
    private boolean stillInStartingPosition;
    private Coordinate startingPosition;
    private Coordinate actualPosition;
    private boolean promoted;
    private int value;
    private HashSet<ValidMove> validMoves;
    private String picture;

    public Figure(FigureType figureType, Color color, Coordinate startingPosition, int value, boolean promoted) {
        this.color = color;
        this.stillInStartingPosition = true;
        this.startingPosition = startingPosition;
        this.actualPosition = startingPosition;
        this.value = value;
        this.promoted = promoted;
        validMoves = new HashSet<>();
        this.figureType = figureType;
    }

    public Figure(FigureType figureType, Color color, Coordinate startingPosition, int value) {
        this(figureType, color, startingPosition, value, false);
    }


    public void setPromoted(boolean promoted) {
        this.promoted = promoted;
    }

    public Color getColor() {
        return color;
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
            this.stillInStartingPosition = false;
    }

    public void setValidMoves(ValidMove validMove) {
        this.validMoves.add(validMove);
    }

    public Set<ValidMove> getValidMoves() {

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

        return (actX + moveX >= 1 && actX + moveX <= 8 &&
                actY + moveY >= 1 && actY + moveY <= 8);

    }

    public FigureType getFigureType() {
        return figureType;
    }

    @Override
    public String toString() {
        char figureColor = this.color.equals(Color.WHITE) ? 'W' : 'B';
        return "" + figureColor + this.figureType.toString().charAt(0);
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
