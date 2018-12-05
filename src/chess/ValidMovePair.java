package chess;

public class ValidMovePair {

    private Coordinate start;
    private Coordinate end;
    private boolean chessTest;

    public ValidMovePair(Coordinate start, Coordinate end) {
        this.start = start;
        this.end = end;
        this.chessTest = false;
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }

    public boolean isChessTest() {
        return chessTest;
    }

    public void setChessTest(boolean chessTest) {
        this.chessTest = chessTest;
    }
}
