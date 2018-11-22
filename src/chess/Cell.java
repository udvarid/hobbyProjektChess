package chess;

public class Cell {

    private Coordinate coordinate;
    private char color;

    public Cell(Coordinate coordinate, char color) {
        this.coordinate = coordinate;
        this.color = color;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public char getColor() {
        return color;
    }
}
