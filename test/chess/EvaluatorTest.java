package chess;

import org.junit.jupiter.api.Test;

import javax.swing.text.html.HTMLDocument;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class EvaluatorTest {

    @Test
    public void testMaterial() {
        Governor governor = new Governor();
        int materialWhite = Evaluator.evaluateBasedValue(Color.WHITE, governor.getGame());
        int materialBlack = Evaluator.evaluateBasedValue(Color.BLACK, governor.getGame());
        assertEquals(38, materialWhite);
        assertEquals(38, materialBlack);

        Iterator<Figure> iterator = governor.getGame().getFigures().iterator();
        while (iterator.hasNext()) {
            Figure figure = iterator.next();
            if (figure.getColor() == Color.WHITE && figure.getFigureType() == FigureType.ROOK) {
                iterator.remove();
            }
            if (figure.getColor() == Color.BLACK && figure.getFigureType() == FigureType.BISHOP) {
                iterator.remove();
            }
        }


        materialWhite = Evaluator.evaluateBasedValue(Color.WHITE, governor.getGame());
        materialBlack = Evaluator.evaluateBasedValue(Color.BLACK, governor.getGame());
        assertEquals(28, materialWhite);
        assertEquals(32, materialBlack);

    }

}
