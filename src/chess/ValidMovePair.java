package chess;

public class ValidMovePair {

    private Coordinate start;
    private Coordinate end;
    private boolean chessTest;
    private Figure figure;

    public ValidMovePair(Coordinate start, Coordinate end, Figure figure) {
        this.start = start;
        this.end = end;
        this.figure = figure;
        this.chessTest = false;
    }

    public Figure getFigure() {
        return figure;
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

    public void setFigure(Figure figure) {
        this.figure = figure;
    }
}
