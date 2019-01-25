package chess;

public enum EndGameType {
    MATE("This is a Check-Mate"),
    NOVALIDMOVES("There is no more valid moves"),
    PASSIVGAME("The game is end due to passive game (in the last 50 turn there were no capture or pawn moves"),
    UNSUFFICIENTMATERIAL("Unsufficient material, neither of the player can win"),
    NOEND("Show must go on!");

    private String displayName;

    EndGameType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
