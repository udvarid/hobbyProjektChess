package chess;

import java.util.HashSet;
import java.util.Set;

public class Game {

    private Set<Figure> figures = new HashSet<>();
    private int round;
    private int chessLastTime;
    private Board board; //TODO meggondolni, hogy ez kell e egyáltalán
    private String version;
    private Player playerWhite;
    private Player playerBlack;

    public Game(String version, Player playerWhite, Player playerBlack) {
        setFigures();
        this.round = 0;
        this.chessLastTime = 0;
        this.board = new Board();
        this.version = version;
        this.playerWhite = playerWhite;
        this.playerBlack = playerBlack;
    }


    public Game(Player playerWhite, Player playerBlack) {
        this("Normal", playerWhite, playerBlack);
    }

    public Game() {
        this("Normal", new Player('W', "Human"), new Player('B', "Human"));
    }

    public Game(String version) {
        this(version, new Player('W', "Human"), new Player('B', "Human"));
    }


    public Set<Figure> getFigures() {
        return figures;
    }

    public int getRound() {
        return round;
    }

    public int getChessLastTime() {
        return chessLastTime;
    }

    public Board getBoard() {
        return board;
    }

    public String getVersion() {
        return version;
    }

    public Player getPlayerWhite() {
        return playerWhite;
    }

    public Player getPlayerBlack() {
        return playerBlack;
    }


    private void setFigures() {

        //pawns
        for (int i = 1; i <= 8; i++) {
            figures.add(new Pawn('W', new Coordinate(2, i), 1));
            figures.add(new Pawn('B', new Coordinate(7, i), 1));
        }

        //Rooks
        figures.add(new Rook('W', new Coordinate(1, 1), 5));
        figures.add(new Rook('W', new Coordinate(1, 8), 5));
        figures.add(new Rook('B', new Coordinate(8, 1), 5));
        figures.add(new Rook('B', new Coordinate(8, 8), 5));

        //Knights
        figures.add(new Knight('W', new Coordinate(1, 2), 3));
        figures.add(new Knight('W', new Coordinate(1, 7), 3));
        figures.add(new Knight('B', new Coordinate(8, 2), 3));
        figures.add(new Knight('B', new Coordinate(8, 7), 3));

        //Bishops
        figures.add(new Bishop('W', new Coordinate(1, 3), 3));
        figures.add(new Bishop('W', new Coordinate(1, 6), 3));
        figures.add(new Bishop('B', new Coordinate(8, 3), 3));
        figures.add(new Bishop('B', new Coordinate(8, 6), 3));

        //Queen
        figures.add(new Queen('W', new Coordinate(1, 4), 8));
        figures.add(new Queen('B', new Coordinate(8, 4), 8));

        //King
        figures.add(new King('W', new Coordinate(1, 5)));
        figures.add(new King('B', new Coordinate(8, 5)));

    }

    public void printBoard() {
        System.out.println(" -------------------------");

        for (int i = 8; i >= 1; i--) {
            System.out.print(i + "|");
            for (int j = 1; j <= 8; j++) {
                String cellCode = lookingForFigures(i, j);
                System.out.print(cellCode + "|");
            }
            System.out.println();
            System.out.println(" -------------------------");

        }
        System.out.println("   A  B  C  D  E  F  G  H  ");

    }

    private String lookingForFigures(int coordinateX, int coordinateY) {
        for (Figure figure : this.figures) {
            if (figure.getActualPosition().getX() == coordinateX &&
                    figure.getActualPosition().getY() == coordinateY)
                return "" + figure.getColor() + figure.getSign();
        }
        return "  ";
    }

    //TODO this method is only for testing purpuse
    public void printValidMoves() {

        //Set<FinalValidMove> finalValidMoves = getFinalValidMoves();

    }


    //TODO two player give order (from->to), this has to translate to coordinates with a initial validation

    //TODO chess checking

    //TODO order evaluation, valid movement check and special cases (en-passan, double moving, castling, avoiding chess)

    //TODO Draw checking (similar board-set/no check within X round/no possible valid move)

    //TODO actual board evaluation (material, area, weakness/strenght)

    //TODO computer player

    //TODO optimise the weight of change between the different evaluation method


}
