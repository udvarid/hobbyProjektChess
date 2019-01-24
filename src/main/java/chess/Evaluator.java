package chess;

import com.rits.cloning.Cloner;

public class Evaluator {

    private Evaluator() {

    }

    public static int evaluateBasedValue(Color color, Game game) {

        int result = 0;
        for (Figure figure : game.getFigures()) {
            if (figure.getColor() == color) {
                result += figure.getValue();
            }
        }

        return result;

    }

    public static void markMateMoves(Color color, Game game) {


        Cloner cloner = new Cloner();


        for (ValidMovePair validMovePair : game.getValidmoves()) {
            if (validMovePair.getFigure().getColor() == color) {
                Game gameCopy = cloner.deepClone(game);

                gameCopy.deleteFigure(validMovePair.getEnd());

                Figure figureToMove = null;
                for (Figure figureSearched : gameCopy.getFigures()) {
                    if (figureSearched.getActualPosition().equals(validMovePair.getStart())) {
                        figureToMove = figureSearched;
                        break;
                    }
                }
                figureToMove.getActualPosition().setX(validMovePair.getEnd().getX());
                figureToMove.getActualPosition().setY(validMovePair.getEnd().getY());

                gameCopy.finalValidMoves(true);
                gameCopy.cleanFromChessRelatedMoves();

                //megnézni, hogy az ellenfél királya sakkban van e és hogy van e valid lépése
                Color enemyColor = color == Color.WHITE ? Color.BLACK : Color.WHITE;
                //kikeresni az ellenfél királyát
                Figure enemyKing = null;
                for (Figure figure : gameCopy.getFigures()) {
                    if (figure.getColor() == enemyColor && figure.getFigureType() == FigureType.KING) {
                        enemyKing = figure;
                        break;
                    }
                }
                //megszámolni, hogy az ellenfélnek mennyi valid lépése van
                //és megnézni, hogy a saját bábuink valamelyike támadja e a királyt
                int numberOfEnemyValidMoves = 0;
                boolean attackingEnemyKing = false;
                for (ValidMovePair vpToCheck : gameCopy.getValidmoves()) {
                    if (vpToCheck.getFigure().getColor() == enemyColor) {
                        numberOfEnemyValidMoves++;
                    } else {
                        if (vpToCheck.getEnd().equals(enemyKing.getActualPosition())) {
                            attackingEnemyKing = true;
                        }
                    }
                }

                if (numberOfEnemyValidMoves == 0 && attackingEnemyKing) {
                    validMovePair.setMateTest(true);
                }


            }
        }

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

    public static int evaluateNumberOfControlledCenters(Color color, Game game) {
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
