package chess.evaluations;

import chess.Color;
import chess.Game;

public interface Evaluate {

    int score(Color colorOwn, Color colorEnemy, Game game);

    int getWeight();


}
