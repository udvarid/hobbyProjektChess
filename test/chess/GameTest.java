package chess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    public void testLookingForFigures(){
        Game newGame = new Game();
        assertEquals("WB", newGame.lookingForFigures(new Coordinate(1, 3)));
        assertEquals("WP", newGame.lookingForFigures(new Coordinate(2, 4)));
        assertEquals("BK", newGame.lookingForFigures(new Coordinate(8, 5)));
        assertEquals("BR", newGame.lookingForFigures(new Coordinate(8, 8)));
    }
}
