package chess;

public class Player {

    private Color color;
    private PlayerType type;

    public Player(Color color, PlayerType type) {
        this.color = color;
        this.type = type;
    }

    public Color getColor() {
        return color;
    }

    public PlayerType getType() {
        return type;
    }
}
