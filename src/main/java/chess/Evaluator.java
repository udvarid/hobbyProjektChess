package chess;


public class Evaluator {

    private Evaluator() {

    }


    public static int evaluateBasedValueWithUndefended(Color color, Game game) {
        return 0;
    }

    public static int evaluateBasedValueWithReattacked(Color color, Game game) {
        return 0;
    }

    public static int evaluateNumberOfKingDefenders(Color color, Game game) {
        return 0;
    }

    public static int evaluateNumberFreeWaysToKing(Color color, Game game) {
        return 0;
    }

    public static int evaluateNumberOfControlledCells(Color color, Game game) {
        return 0;
    }

    public static int evaluateNumberOfControlledCellsByOfficers(Color color, Game game) {
        return 0;
    }

    public static int evaluateNumberOfUnprotectedFigures(Color color, Game game) {
        return 0;
    }

    public static int evaluateNumberOfOverhelmedFigures(Color color, Game game) {
        //túlterhelt védők, olyan esetben, amikor ők az egyedül védelmezők
        return 0;
    }

    public static int evaluateNumberOfControlledDiagonals(Color color, Game game) {
        return 0;
    }

    public static int evaluateNumberOfControlledMainDiagonals(Color color, Game game) {
        return 0;
    }

    public static int evaluateNumberOfControlledSpacesAtEnemeySide(Color color, Game game) {
        return 0;
    }

    public static int evaluateNumberOfDoubledPawns(Color color, Game game) {
        return 0;
    }

    public static int evaluateNumberOfSinglyPawn(Color color, Game game) {
        return 0;
    }

    public static int evaluateNumberOfUnProtectedPawn(Color color, Game game) {
        //azon gyalogokat kell nézni, amiket más gyalogok nem védenek
        return 0;
    }

    public static int evaluateNumberOfProtectedPawn(Color color, Game game) {
        //azon gyalogokat kell nézni, amiket más gyalogok nem védenek
        return 0;
    }

    public static int evaluateNumberOfPassedPawn(Color color, Game game) {
        //azon gyalogokat kell nézni, amik előtt más gyalogok nincsenek
        return 0;
    }


}
