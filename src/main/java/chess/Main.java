
package chess;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;



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
        label.setStyle("-fx-font: 20 arial;");
        bp.setBottom(label);

        Label label2 = new Label();
        label2.setStyle("-fx-font: 15 arial;");
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


        scene1 = new Scene(bp, 775, 700);
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

    private void showValidMoves(Button[][] tiles, Integer x, Integer y, Label label1, Label label2) {
        boolean validMoveSet = false;
        for (ValidMovePair validMovePair : aims) {
            if (validMovePair.getEnd().equals(new Coordinate(x, y))) {
                EndGameType endGameType = moveHumanPlayer(validMovePair);

                if (validMovePair.getFigure().getFigureType() == FigureType.PAWN &&
                        ((governor.getWhoIsNext().getColor() == Color.WHITE && x == 8) ||
                                (governor.getWhoIsNext().getColor() == Color.BLACK && x == 1))) {
                    String promoteSign = PromoteBox.display("Promotion", "Which type do you choose?", governor.getWhoIsNext().getColor());
                    governor.getGame().promote(validMovePair, promoteSign.charAt(0));
                }
                MoveHistory moveHistory = governor.getMoveHistory().get(governor.getMoveHistory().size() - 1);
                moveHistory.setGiveChess(governor.enemyKingInChess(governor.getWhoIsNext().getEnemyColor()));
                governor.nextPlayerSet();
                if (endGameType != EndGameType.NOEND) {
                    label1.setText(endGameType.getDisplayName());
                    governor.setGameIsOn(false);
                }
                printSet(tiles);
                validMoveSet = true;
                label2.setText(printHistory());
                computerEnemyMoving(tiles, label1, label2);


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


    private void computerEnemyMoving(Button[][] tiles, Label label1, Label label2) {
        if (governor.getWhoIsNext().getType() == PlayerType.COMPUTER) {
            Platform.runLater(() -> {
                try {
                    Thread.sleep(1000);
                    EndGameType endGameType = moveComputerPlayer();
                    MoveHistory moveHistory = governor.getMoveHistory().get(governor.getMoveHistory().size() - 1);
                    moveHistory.setGiveChess(governor.enemyKingInChess(governor.getWhoIsNext().getEnemyColor()));
                    governor.nextPlayerSet();
                    printSet(tiles);
                    if (endGameType != EndGameType.NOEND) {
                        label1.setText(endGameType.getDisplayName());
                        governor.setGameIsOn(false);
                    }
                    label2.setText(printHistory());
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

    private EndGameType moveHumanPlayer(ValidMovePair validMovePair) {
        boolean wasMove = governor.actualPlayerIsMoving(governor.getWhoIsNext(), validMovePair);
        governor.getGame().finalValidMoves(true);
        governor.getGame().cleanFromChessRelatedMoves();
        return checkGameStatus();
    }

    private EndGameType moveComputerPlayer() {
        boolean wasMove = governor.actualPlayerIsMoving(governor.getWhoIsNext(), null);
        governor.getGame().finalValidMoves(true);
        governor.getGame().cleanFromChessRelatedMoves();
        return checkGameStatus();
    }

    private EndGameType checkGameStatus() {
        EndGameType endGameType = EndGameType.NOEND;
        if (governor.enemyInMate(governor.getWhoIsNext().getEnemyColor())) {
            endGameType = EndGameType.MATE;
        } else if (governor.enemyHasNoValidMoves(governor.getWhoIsNext().getEnemyColor())) {
            endGameType = EndGameType.NOVALIDMOVES;
        } else if (governor.thisIsADraw()) {
            endGameType = EndGameType.PASSIVGAME;
        } else if (governor.notEnoughMaterial()) {
            endGameType = EndGameType.UNSUFFICIENTMATERIAL;
        }
        return endGameType;
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
