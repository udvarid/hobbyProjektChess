package chess;

public class Board {

    private Coordinate[][] cells;

    public Board() {
        this.cells = new Coordinate[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.cells[i][j] = new Coordinate(i + 1, j + 1);
            }
        }
    }

    public Coordinate[][] getCells() {
        return cells;
    }


}
