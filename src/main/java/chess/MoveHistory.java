package chess;

public class MoveHistory {
    private int round;
    private Color color;
    private Coordinate startCoordinate;
    private Coordinate endCoordinate;
    private boolean hitEnemy;
    private boolean giveChess;

    public MoveHistory() {
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Coordinate getStartCoordinate() {
        return startCoordinate;
    }

    public void setStartCoordinate(Coordinate startCoordinate) {
        this.startCoordinate = startCoordinate;
    }

    public Coordinate getEndCoordinate() {
        return endCoordinate;
    }

    public void setEndCoordinate(Coordinate endCoordinate) {
        this.endCoordinate = endCoordinate;
    }

    public boolean isHitEnemy() {
        return hitEnemy;
    }

    public void setHitEnemy(boolean hitEnemy) {
        this.hitEnemy = hitEnemy;
    }

    public boolean isGiveChess() {
        return giveChess;
    }

    public void setGiveChess(boolean giveChess) {
        this.giveChess = giveChess;
    }

    private String convertCoordinate(Coordinate coordinate) {

        return "" + ((char)(coordinate.getY() + 96)) + coordinate.getX();

    }
    @Override
    public String toString() {
        String chessString = this.giveChess ? "+" : "";
        String hitString = this.hitEnemy ? "x" : "-";
        return convertCoordinate(this.startCoordinate) + hitString + convertCoordinate(this.endCoordinate) + chessString;
    }


}
