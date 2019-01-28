package chess;

import java.util.Objects;

public class TrainerNote {

    private EndGameType endGameType;
    private Color colorOfLastMover;
    private int number;
    private int weight;

    public TrainerNote(EndGameType endGameType, Color colorOfLastMover, int number, int weight) {
        this.endGameType = endGameType;
        this.colorOfLastMover = colorOfLastMover;
        this.number = number;
        this.weight = weight;
    }

    public EndGameType getEndGameType() {
        return endGameType;
    }

    public Color getColorOfLastMover() {
        return colorOfLastMover;
    }

    public int getNumber() {
        return number;
    }

    public int getWeight() {
        return weight;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainerNote that = (TrainerNote) o;
        return weight == that.weight &&
                endGameType == that.endGameType &&
                colorOfLastMover == that.colorOfLastMover;
    }

    @Override
    public int hashCode() {
        return Objects.hash(endGameType, colorOfLastMover, weight);
    }

    @Override
    public String toString() {
        return this.colorOfLastMover + " - " + this.endGameType + " - " + this.weight + "% " + this.getNumber();
    }
}
