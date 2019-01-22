package javaFX;

import chess.Governor;
import chess.PlayerType;
import javafx.scene.control.Button;

public class GameStarter implements Runnable {

    private Button[][] tiles;
    private String typeOfGame;

    public GameStarter(Button[][] tiles, String typeOfGame) {
        this.tiles = tiles;
        this.typeOfGame = typeOfGame;
    }

    @Override
    public void run() {

        Governor governor = null;
        if (this.typeOfGame.equals("Human vs. Human")) {
            governor = new Governor();
        } else {
            governor = new Governor(PlayerType.HUMAN, PlayerType.COMPUTER);
        }
        governor.startGame();
        governor.setTiles(this.tiles);

    }
}
