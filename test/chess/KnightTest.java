package chess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KnightTest {

    @Test
    public void testValidMoves() {

        Knight knight;

        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                knight = new Knight('W', new Coordinate(i, j), 1);

                for (ValidMove validmove : knight.getValidMoves()) {
                    int getX = validmove.getCoordinate().getX();
                    int getY = validmove.getCoordinate().getY();
                    Assertions.assertEquals(true, getX >= 1 && getX <= 8);
                    Assertions.assertEquals(true, getY >= 1 && getY <= 8);
                    for (Coordinate coordinate : validmove.getEmptyCells()) {
                        int getEmptyX = coordinate.getX();
                        int getEmptyY = coordinate.getY();
                        Assertions.assertEquals(true, getEmptyX >= 1 && getEmptyX <= 8);
                        Assertions.assertEquals(true, getEmptyY >= 1 && getEmptyY <= 8);
                    }
                }

            }
        }


    }
}
