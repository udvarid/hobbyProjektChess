
package chess;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static javafx.scene.text.TextAlignment.RIGHT;


public class Main extends Application {
    private Stage window;
    private Scene scene1, scene2;
    private Governor governor = null;
    private List<ValidMovePair> aims = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;

        HBox topMenu = new HBox();
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().add("Human vs. Computer");
        choiceBox.getItems().add("Human vs. Human");
        choiceBox.setValue("Human vs. Computer");

        Button buttonStart = new Button("Start game");
        topMenu.getChildren().addAll(choiceBox, buttonStart);


        BorderPane bp = new BorderPane();
        bp.setTop(topMenu);

        Label label = new Label();
        bp.setBottom(label);

        Label label2 = new Label();
        label2.setTextAlignment(TextAlignment.LEFT);
        bp.setRight(label2);

        Button[][] tiles = new Button[8][8];

        GridPane root = new GridPane();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                tiles[row][col] = new Button();
                String color;
                if ((row + col) % 2 == 0) {
                    color = "white";
                } else {
                    color = "grey";
                }
                tiles[row][col].setStyle("-fx-background-color: " + color + ";");
                root.add(tiles[row][col], col, row);
                tiles[row][col].setPrefSize(75, 75);
                Integer intX = 8 - row;
                Integer intY = col + 1;
                tiles[row][col].setOnAction(e -> {
                    if (governor != null && governor.isGameIsOn()) {
                        showValidMoves(tiles, intX, intY, label, label2);
                    }
                });
            }
        }

        bp.setCenter(root);


        scene1 = new Scene(bp, 700, 700);
        window.setScene(scene1);
        window.setTitle("Chessboard");
        window.show();

        buttonStart.setOnAction(e -> {
            if (choiceBox.getValue().equals("Human vs. Human")) {
                governor = new Governor();
            } else {
                governor = new Governor(PlayerType.HUMAN, PlayerType.COMPUTER);
            }
            printSet(tiles);
            label.setText("");
            label2.setText("");

        });


    }

    private void showValidMoves(Button[][] tiles, Integer x, Integer y, Label label, Label label2) {
        boolean validMoveSet = false;
        for (ValidMovePair validMovePair : aims) {
            if (validMovePair.getEnd().equals(new Coordinate(x, y))) {
                moveHumanPlayer(validMovePair);

                if (validMovePair.getFigure().getFigureType() == FigureType.PAWN &&
                        ((governor.getWhoIsNext().getColor() == Color.WHITE && x == 8) ||
                                (governor.getWhoIsNext().getColor() == Color.BLACK && x == 1))) {
                    String promoteSign = PromoteBox.display("Promotion", "Which type do you choose?", governor.getWhoIsNext().getColor());
                    governor.getGame().promote(validMovePair, promoteSign.charAt(0));
                }
                MoveHistory moveHistory = governor.getMoveHistory().get(governor.getMoveHistory().size() - 1);
                moveHistory.setGiveChess(enemyKingInChess(governor.getWhoIsNext().getEnemyColor()));
                governor.nextPlayerSet();
                printSet(tiles);
                validMoveSet = true;
                //TODO game end chess
                String movements = printHistory();
                label2.setText(movements);
                computerEnemyMoving(tiles, label2);


                //TODO game end chess
                break;
            }
        }
        aims.clear();
        if (!validMoveSet) {
            highLightValidMove(tiles, x, y);
        }

    }

    private String printHistory() {
        StringBuilder result = new StringBuilder();
        int roundNumber = 1;
        for (MoveHistory moveHistory : governor.getMoveHistory()) {
            if (roundNumber % 2 == 1) {
                result.append(moveHistory.getRound()).append(". ");
            }
            result.append(moveHistory.toString());
            roundNumber++;
            if (roundNumber %2 == 0) {
                result.append(" - ");
            } else {
                result.append("\r");
            }


        }
        return result.toString();
    }

    private boolean enemyKingInChess(Color color) {
        King king = null;
        for (Figure figure : governor.getGame().getFigures()) {
            if (figure.getFigureType() == FigureType.KING && figure.getColor() == color) {
                king = (King) figure;
                return king.isInChess();
            }
        }
        return false;
    }

    private void computerEnemyMoving(Button[][] tiles, Label label2) {
        if (governor.getWhoIsNext().getType() == PlayerType.COMPUTER) {
            Platform.runLater(() -> {
                try {
                    Thread.sleep(1000);
                    moveComputerPlayer();
                    MoveHistory moveHistory = governor.getMoveHistory().get(governor.getMoveHistory().size() - 1);
                    moveHistory.setGiveChess(enemyKingInChess(governor.getWhoIsNext().getEnemyColor()));
                    governor.nextPlayerSet();
                    printSet(tiles);
                    String movements = printHistory();
                    label2.setText(movements);
                    int x = moveHistory.getStartCoordinate().getX();
                    int y = moveHistory.getStartCoordinate().getY();
                    tiles[8 - x][y - 1].setStyle(" -fx-background-color:rgba(29,252,220,0.86);");

                    x = moveHistory.getEndCoordinate().getX();
                    y = moveHistory.getEndCoordinate().getY();
                    tiles[8 - x][y - 1].setStyle(" -fx-background-color:rgba(76,46,150,0.86);");

                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            });
        }
    }

    private void highLightValidMove(Button[][] tiles, Integer x, Integer y) {
        clearFormat(tiles);
        for (ValidMovePair validMovePair : governor.getGame().getValidmoves()) {
            if (validMovePair.getStart().getX() == x && validMovePair.getStart().getY() == y &&
                    validMovePair.getFigure().getColor() == governor.getWhoIsNext().getColor()) {
                tiles[8 - x][y - 1].setStyle(" -fx-background-color:rgba(252,135,8,0.86);");

                int aimX = 8 - validMovePair.getEnd().getX();
                int aimY = validMovePair.getEnd().getY() - 1;
                aims.add(validMovePair);
                tiles[aimX][aimY].setStyle(" -fx-background-color:rgba(152,251,43,0.88);");
            }
        }
    }

    private void moveHumanPlayer(ValidMovePair validMovePair) {
        boolean wasMove = governor.actualPlayerIsMoving(governor.getWhoIsNext(), validMovePair);
        String message = governor.endGameEvaluating(wasMove, governor.getWhoIsNext());
        governor.getGame().finalValidMoves(true);
        governor.getGame().cleanFromChessRelatedMoves();

    }

    private void moveComputerPlayer() {
        boolean wasMove = governor.actualPlayerIsMoving(governor.getWhoIsNext(), null);
        String message = governor.endGameEvaluating(wasMove, governor.getWhoIsNext());
        governor.getGame().finalValidMoves(true);
        governor.getGame().cleanFromChessRelatedMoves();

    }

    private void printSet(Button[][] tiles) {
        clearImage(tiles);
        clearFormat(tiles);
        for (Figure figure : governor.getGame().getFigures()) {
            tiles[7 - (figure.getActualPosition().getX() - 1)][figure.getActualPosition().getY() - 1].setGraphic(new ImageView(figure.getPicture()));
        }
    }

    private void clearFormat(Button[][] tiles) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String color;
                if ((i + j) % 2 == 0) {
                    color = "white";
                } else {
                    color = "grey";
                }
                tiles[i][j].setStyle("-fx-background-color: " + color + ";");
            }
        }
    }

    private void clearImage(Button[][] tiles) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tiles[i][j].setGraphic(null);
            }
        }
    }

}
