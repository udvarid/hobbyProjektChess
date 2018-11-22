package chess;

public class Main {

    public static void main(String[] args) {

        Board board = new Board();

        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < 8; j++) {
                if (board.getCells()[i][j].getColor() == 'W')
                    System.out.print("W ");
                else
                    System.out.print("B ");
            }
            System.out.println();
        }

        Queen pawn = new Queen('W', new Cell(new Coordinate(8, 1), 'B'),1);
        for (ValidMove validmove : pawn.getValidMoves()) {
            System.out.println(validmove.getCoordinate().getX() + " - " + validmove.getCoordinate().getY() + " - " + validmove.getEmptyCells().size());

        }

    }
}
