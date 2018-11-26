package chess;

public class Player {

    private char color;
    private String type;

    public Player(char color, String type) {
        this.color = color;
        this.type = type;
    }

    public char getColor() {
        return color;
    }

    public String getType() {
        return type;
    }
}
