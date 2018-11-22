package chess;

public class Figure {

    private String name;
    private char sign;
    private char color;
    private boolean isAlive;
    private boolean stillInStartingPosition;
    private Cell startingPosition;
    private Cell actualPosition;
    private boolean promoted;
    private int value;

    public Figure(String name, char sign, char color, Cell startingPosition, int value, boolean promoted) {
        this.name = name;
        this.sign = sign;
        this.color = color;
        this.isAlive = true;
        this.stillInStartingPosition = true;
        this.startingPosition = startingPosition;
        this.actualPosition = startingPosition;
        this.value = value;
        this.promoted = promoted;
    }

    public Figure(String name, char sign, char color, Cell startingPosition, int value) {
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

    public Cell getStartingPosition() {
        return startingPosition;
    }

    public void setAlive(boolean alive) {
        if (isAlive && !alive)
            isAlive = alive;
    }

    public int getValue() {
        return value;
    }

    public Cell getActualPosition() {
        return actualPosition;
    }

    public void setActualPosition(Cell actualPosition) {
        this.actualPosition = actualPosition;
    }

    public void setStillInStartingPosition(boolean stillInStartingPosition) {
        if (this.stillInStartingPosition && !stillInStartingPosition)
            this.stillInStartingPosition = stillInStartingPosition;
    }
}
