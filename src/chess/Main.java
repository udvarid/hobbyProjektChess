package chess;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        Game game = new Game();
        game.cleanTable();
        //game.addFigures(new King('W', new Coordinate(1, 5), 0));
        game.addFigures(new King('B', new Coordinate(8, 5), 0));
        game.addFigures(new Knight('B', new Coordinate(8, 2), 3));
        game.addFigures(new Rook('W', new Coordinate(8, 8), 5));

        game.printBoard();

        game.finalValidMoves(true);
        game.cleanFromChessRelatedMoves();

        game.printValidMoves();




    }
}
