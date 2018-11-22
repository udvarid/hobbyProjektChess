package chess;

public class Board {

    private Cell[][] cells;

    public Board() {
        this.cells = new Cell[8][8];
        char color;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0)
                    color = 'B';
                else
                    color = 'W';
                this.cells[i][j] = new Cell(new Coordinate(i + 1, j + 1), color);
            }
        }
    }

    public Cell[][] getCells() {
        return cells;
    }


}
