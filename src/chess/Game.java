package chess;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Game {

    private Set<Figure> figures;
    private Set<ValidMovePair> validmoves;
    private Set<ValidMovePair> validmovesForCalculate;


    public Game() {
        this.figures = new HashSet<>();
        this.validmoves = new HashSet<>();
        this.validmovesForCalculate = new HashSet<>();
        setFigures();
    }


    public Set<Figure> getFigures() {
        return figures;
    }



    private void setFigures() {

        //pawns
        for (int i = 1; i <= 8; i++) {
            figures.add(new Pawn(Color.WHITE, new Coordinate(2, i), 1));
            figures.add(new Pawn(Color.BLACK, new Coordinate(7, i), 1));
        }

        //Rooks
        figures.add(new Rook(Color.WHITE, new Coordinate(1, 1), 5));
        figures.add(new Rook(Color.WHITE, new Coordinate(1, 8), 5));
        figures.add(new Rook(Color.BLACK, new Coordinate(8, 1), 5));
        figures.add(new Rook(Color.BLACK, new Coordinate(8, 8), 5));

        //Knights
        figures.add(new Knight(Color.WHITE, new Coordinate(1, 2), 3));
        figures.add(new Knight(Color.WHITE, new Coordinate(1, 7), 3));
        figures.add(new Knight(Color.BLACK, new Coordinate(8, 2), 3));
        figures.add(new Knight(Color.BLACK, new Coordinate(8, 7), 3));

        //Bishops
        figures.add(new Bishop(Color.WHITE, new Coordinate(1, 3), 3));
        figures.add(new Bishop(Color.WHITE, new Coordinate(1, 6), 3));
        figures.add(new Bishop(Color.BLACK, new Coordinate(8, 3), 3));
        figures.add(new Bishop(Color.BLACK, new Coordinate(8, 6), 3));

        //Queen
        figures.add(new Queen(Color.WHITE, new Coordinate(1, 4), 8));
        figures.add(new Queen(Color.BLACK, new Coordinate(8, 4), 8));

        //King
        figures.add(new King(Color.WHITE, new Coordinate(1, 5), 0));
        figures.add(new King(Color.BLACK, new Coordinate(8, 5), 0));


    }

    public void printBoard() {

        System.out.println(" -------------------------");

        for (int i = 8; i >= 1; i--) {
            System.out.print(i + "|");
            for (int j = 1; j <= 8; j++) {
                String cellCode = lookingForFigures(i, j);
                System.out.print(cellCode + "|");
            }
            System.out.println();
            System.out.println(" -------------------------");

        }
        System.out.println("   A  B  C  D  E  F  G  H  ");

    }


    private String lookingForFigures(int coordinateX, int coordinateY) {
        for (Figure figure : this.figures) {
            if (figure.getActualPosition().getX() == coordinateX &&
                    figure.getActualPosition().getY() == coordinateY) {
                char figureColor = figure.getColor().equals(Color.WHITE) ? 'W' : 'B';
                return "" + figureColor + figure.getFigureType().toString().charAt(0);
            }
        }
        return "  ";
    }

    //TODO this method is only for testing purpuse
    public void printValidMoves() {


        for (ValidMovePair validMovesToPrint : this.validmoves) {
            int xStart = validMovesToPrint.getStart().getX();
            int yStart = validMovesToPrint.getStart().getY();
            int xEnd = validMovesToPrint.getEnd().getX();
            int yEnd = validMovesToPrint.getEnd().getY();
            String figureSign = "";
            for (Figure figureToPrint : this.figures) {
                if (figureToPrint.getActualPosition().equals(validMovesToPrint.getStart())) {
                    char figureColor = figureToPrint.getColor().equals(Color.WHITE) ? 'W' : 'B';
                    figureSign = "" + figureColor + figureToPrint.getFigureType().toString().charAt(0);
                    break;
                }

            }

            System.out.println(figureSign + " : " + translateFromNumber(yStart) + xStart + " -> " +
                    translateFromNumber(yEnd) + xEnd + " : " + validMovesToPrint.isChessTest());
        }
    }

    public char translateFromNumber(int number) {
        switch (number) {
            case 1:
                return 'A';
            case 2:
                return 'B';
            case 3:
                return 'C';
            case 4:
                return 'D';
            case 5:
                return 'E';
            case 6:
                return 'F';
            case 7:
                return 'G';
            case 8:
                return 'H';
            default:
                return 'X';
        }

    }

    public void cleanTable() {
        this.figures = new HashSet<>();
    }


    public void finalValidMoves(boolean isNormal) {

        //for calculation making the reset
        this.validmovesForCalculate = new HashSet<>();

        if (isNormal) {
            this.validmoves = this.validmovesForCalculate;
        }


        for (Figure figure : this.figures) {
            for (ValidMove validMoveOfFigure : figure.getValidMoves()) {
                //in case the movement is a normal type
                if (!validMoveOfFigure.isSpecialMove()) {
                    if (normalCaseAndValid(figure, validMoveOfFigure)) {
                        wrapToValidMovePairs(figure, validMoveOfFigure);
                    }
                } else {
                    if (specialCaseAndValid(figure, validMoveOfFigure))
                        wrapToValidMovePairs(figure, validMoveOfFigure);

                }
            }
        }


    }


    private boolean specialCaseAndValid(Figure figure, ValidMove validMoveOfFigure) {
        switch (validMoveOfFigure.getSpecialMoveType()) {
            case CASTLING:
                return isCastlingOk(figure, validMoveOfFigure);
            case PAWN_HIT:
                return isPawnHitOk(figure, validMoveOfFigure); //this is checking the En Passan case as well
            case PAWN_DOUBLE:
                return isDoubleMoveOk(figure, validMoveOfFigure);
            default:
                return false;
        }

    }

    private boolean isCastlingOk(Figure figure, ValidMove validMoveOfFigure) {
        boolean kingAndRookAreReady = isKingAndRookAreReady(figure, validMoveOfFigure);
        boolean kingAndRookPathEmpty = isKingAndRookPathEmpty(figure, validMoveOfFigure);
        return kingAndRookAreReady && kingAndRookPathEmpty;
    }

    private boolean isKingAndRookPathEmpty(Figure thisFigure, ValidMove validMoveOfFigure) {

        int columnOfRook = validMoveOfFigure.getCoordinate().getY() == 7 ? 8 : 1;
        int rowOfRook = thisFigure.getColor().equals(Color.WHITE) ? 1 : 8;

        boolean foundAnybody = false;
        for (Figure figure : figures) {
            if (figure.getActualPosition().getX() == rowOfRook &&
                    figure.getActualPosition().getY() > Math.min(columnOfRook, 5) &&
                    figure.getActualPosition().getY() < Math.max(columnOfRook, 5)) {
                foundAnybody = true;
                break;
            }

        }

        return !foundAnybody;
    }

    private boolean isKingAndRookAreReady(Figure thisFigure, ValidMove validMoveOfFigure) {

        int columnOfRook = validMoveOfFigure.getCoordinate().getY() == 7 ? 8 : 1;
        int rowOfRook = thisFigure.getColor().equals(Color.WHITE) ? 1 : 8;
        Coordinate coordinateRook = new Coordinate(rowOfRook, columnOfRook);

        for (Figure figure : figures) {
            if (figure.getColor() == thisFigure.getColor() &&
                    figure.getActualPosition().equals(coordinateRook) &&
                    figure.getFigureType() == FigureType.ROOK) {
                King king = (King) thisFigure;
                if (!king.isAlredyCastled() &&
                        !king.isInChess() &&
                        king.isStillInStartingPosition() &&
                        figure.isStillInStartingPosition()) {
                    return true;
                }
            }
        }

        return false;

    }

    private boolean isPawnHitOk(Figure thisFigure, ValidMove validMoveOfFigure) {

        Coordinate goalCoordinate = validMoveOfFigure.getCoordinate();
        //simple hit check
        for (Figure figure : this.figures) {
            if (figure.getActualPosition().equals(goalCoordinate)) {
                return figure.getColor() != thisFigure.getColor();

            }
        }
        //if the goal cell is empty, it can be an En Passan move

        return couldbeEnPassan(thisFigure) && enPassan(goalCoordinate, thisFigure.getColor());
    }

    private boolean couldbeEnPassan(Figure thisFigure) {
        return (thisFigure.getColor().equals(Color.WHITE) && thisFigure.getActualPosition().getX() == 5 ||
                thisFigure.getColor().equals(Color.BLACK) && thisFigure.getActualPosition().getX() == 4);
    }

    private boolean enPassan(Coordinate goalCoordinate, Color color) {
        Color enemyColor = color.equals(Color.WHITE) ? Color.BLACK : Color.WHITE;
        int enemyLine = color.equals(Color.WHITE) ? 5 : 4;
        Coordinate enemyCoordinate = new Coordinate(enemyLine, goalCoordinate.getY());

        for (Figure figure : figures) {
            if (figure.getActualPosition().equals(enemyCoordinate) &&
                    figure.getColor().equals(enemyColor) &&
                    figure.getFigureType() == FigureType.PAWN) {
                Pawn enemyPawn = (Pawn) figure;
                if (enemyPawn.isLastMoveIsDoubleOpening()) {
                    return true;
                }
            }
        }

        return false;

    }

    private boolean isDoubleMoveOk(Figure figure, ValidMove validMoveOfFigure) {

        return normalCaseAndValid(figure, validMoveOfFigure) && figure.isStillInStartingPosition();
    }

    private boolean normalCaseAndValid(Figure thisFigure, ValidMove validMoveOfFigure) {

        Coordinate goalCoordinate = validMoveOfFigure.getCoordinate();
        //validaton of aim cell
        for (Figure figure : this.figures) {
            if (figure.getColor() == thisFigure.getColor() && figure.getActualPosition().equals(goalCoordinate))
                return false;
        }
        //validaton of empty cells
        for (Coordinate emptyCoordinate : validMoveOfFigure.getEmptyCells()) {
            for (Figure figure : this.figures) {
                if (figure.getActualPosition().equals(emptyCoordinate))
                    return false;
            }
        }

        return true;
    }

    private void wrapToValidMovePairs(Figure figure, ValidMove validMoveOfFigure) {
        Coordinate startOfMove = new Coordinate(figure.getActualPosition().getX(), figure.getActualPosition().getY());
        Coordinate endOfMove = new Coordinate(validMoveOfFigure.getCoordinate().getX(), validMoveOfFigure.getCoordinate().getY());
        this.validmovesForCalculate.add(new ValidMovePair(startOfMove, endOfMove, figure));
    }

    public void addFigures(Figure figure) {
        this.figures.add(figure);
    }

    public Set<ValidMovePair> getValidmoves() {
        return validmoves;
    }

    public void cleanFromChessRelatedMoves() {
        for (ValidMovePair validMovePair : validmovesForCalculate) {
            Figure pufferEnd = removeEndFigure(validMovePair);
            Figure pufferStart = moveStartFigure(validMovePair);
            finalValidMoves(false);
            flagValidMove(validMovePair);
            takeBackFigure(pufferEnd, validMovePair.getEnd());
            takeBackFigure(pufferStart, validMovePair.getStart());

            freeCastlingValidation(validMovePair);
        }
        finalCleanFromChessRelatedMoves();

    }

    private void freeCastlingValidation(ValidMovePair validMovePair) {

        //ha ez egy király és sáncol éppen
        Figure thisFigure = getFigureToCheckCastlingValidation(validMovePair);
        if (thisFigure != null) {
            Coordinate coordinatePuffer = new Coordinate(validMovePair.getStart().getX(), (validMovePair.getEnd().getY() + validMovePair.getStart().getY()) / 2);
            for (ValidMovePair validMovePairToCheck : validmoves) {
                if (validMovePairToCheck.getEnd().equals(coordinatePuffer) && getColor(validMovePairToCheck) != thisFigure.getColor() && getColor(validMovePairToCheck) != Color.UNDEFINED) {
                    validMovePair.setChessTest(true);
                }
                //ha éppen a királyt támadják, akkor sem lehet sáncolni
                if (validMovePairToCheck.getEnd().equals(validMovePair.getStart())) {
                    validMovePair.setChessTest(true);
                }
            }
        }


    }

    private Color getColor(ValidMovePair validMovePairToCheck) {
        for (Figure figure : figures) {
            if (figure.getActualPosition().equals(validMovePairToCheck.getStart())) {
                return figure.getColor();
            }
        }
        return Color.UNDEFINED;
    }

    private Figure getFigureToCheckCastlingValidation(ValidMovePair validMovePair) {

        Figure figureResult = null;

        for (Figure figure : figures) {
            if (figure.getActualPosition().equals(validMovePair.getStart())
                    && figure.getFigureType() == FigureType.KING
                    && Math.abs(validMovePair.getStart().getY() - validMovePair.getEnd().getY()) == 2) {
                figureResult = figure;
                break;
            }
        }

        return figureResult;
    }

    private void finalCleanFromChessRelatedMoves() {


        Iterator<ValidMovePair> it = validmoves.iterator();
        while (it.hasNext()) {
            ValidMovePair validMovePair = it.next();
            if (validMovePair.isChessTest()) {
                it.remove();
            }
        }

    }

    private void flagValidMove(ValidMovePair validMovePair) {
        //kikeresni az aktuális figura színét
        Color color = Color.UNDEFINED;
        for (Figure figure : figures) {
            if (figure.getActualPosition().equals(validMovePair.getEnd())) {
                color = figure.getColor();
                break;
            }
        }
        //megkeresni az ehhez a színhez tartozó királyt és annak koordinátáját
        Coordinate kingsCoordinate = null;
        for (Figure figure : figures) {
            if (figure.getColor() == color && figure.getFigureType() == FigureType.KING) {
                kingsCoordinate = figure.getActualPosition();
                break;
            }
        }
        //ha a validmovepair-ok között van olyan, aminél az ellenfél tud ide (a királyhoz) lépni
        //azt jelölni
        for (ValidMovePair validMovePairForCheck : validmovesForCalculate) {
            if (validMovePairForCheck.getEnd().equals(kingsCoordinate)) {
                validMovePair.setChessTest(true);
                break;
            }
        }

    }

    private void takeBackFigure(Figure pufferFigure, Coordinate coordinateBack) {
        if (pufferFigure != null) {
            pufferFigure.getActualPosition().setX(coordinateBack.getX());
            pufferFigure.getActualPosition().setY(coordinateBack.getY());
        }
    }

    private Figure moveStartFigure(ValidMovePair validMovePair) {
        for (Figure figure : figures) {
            if (figure.getActualPosition().equals(validMovePair.getStart())) {
                figure.getActualPosition().setX(validMovePair.getEnd().getX());
                figure.getActualPosition().setY(validMovePair.getEnd().getY());
                return figure;
            }
        }

        return null;
    }

    private Figure removeEndFigure(ValidMovePair validMovePair) {
        for (Figure figure : figures) {
            if (figure.getActualPosition().equals(validMovePair.getEnd())) {
                figure.getActualPosition().setX(100);
                figure.getActualPosition().setY(100);
                return figure;
            }
        }
        return null;
    }

    public boolean deleteFigure(Coordinate end) {

        Iterator<Figure> iterator = this.figures.iterator();
        while (iterator.hasNext()) {
            Figure figure = iterator.next();
            if (figure.getActualPosition().equals(end)) {
                iterator.remove();
                return true;
            }
        }

        return false;
    }

    public void promote(ValidMovePair moveActual, char promoteSign) {

        deleteFigure(moveActual.getEnd());
        Color colorToSet = moveActual.getFigure().getColor();
        Figure figure = null;
        switch (promoteSign) {
            case 'r':
                figure = new Rook(colorToSet, moveActual.getEnd(), 5);
                break;
            case 'k':
                figure = new Knight(colorToSet, moveActual.getEnd(), 3);
                break;
            case 'b':
                figure = new Bishop(colorToSet, moveActual.getEnd(), 3);
                break;
            case 'q':
                figure = new Queen(colorToSet, moveActual.getEnd(), 3);
                break;
        }
        figure.setStillInStartingPosition(false);
        figure.setPromoted(true);

        this.figures.add(figure);


    }


    //TODO actual board evaluation (material, area, weakness/strenght)

    //TODO computer player

    //TODO optimise the weight of change between the different evaluation method


}
