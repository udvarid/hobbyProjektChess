package chess;

public class ValidMovePair {

    private Coordinate start;
    private Coordinate end;

    public ValidMovePair(Coordinate start, Coordinate end) {
        this.start = start;
        this.end = end;
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }
}
