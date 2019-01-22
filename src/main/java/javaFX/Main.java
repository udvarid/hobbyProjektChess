
package javaFX;

import chess.Governor;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class Main extends Application {
    Stage window;
    Scene scene1;
    ExecutorService threadPool = Executors.newFixedThreadPool(1);

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;


        HBox topMenu = new HBox();
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().add("Human vs. Human");
        choiceBox.getItems().add("Human vs. Computer");
        choiceBox.setValue("Human vs. Human");

        Button buttonStart = new Button("Start game");
        topMenu.getChildren().addAll( choiceBox,buttonStart);



        BorderPane bp = new BorderPane();
        bp.setTop(topMenu);

        Button[][] tiles = new Button[8][8];
        GridPane root = new GridPane();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                tiles[row][col] = new Button();
                String color;
                if ((row + col) % 2 == 0) {
                    color = "white";
                } else {
                    color = "black";
                }
                tiles[row][col].setStyle("-fx-background-color: " + color + ";");
                root.add(tiles[row][col], col, row);
                tiles[row][col].setPrefSize(75, 75);
            }
        }



        bp.setCenter(root);




        scene1 = new Scene(bp, 200, 200);
        window.setScene(scene1);
        window.setTitle("Title Here");
        window.show();







        buttonStart.setOnAction(e -> {
            GameStarter gameStarter = new GameStarter(tiles, choiceBox.getValue());
            threadPool.submit(gameStarter);
        });


    }




    public static void main(String[] args) {
        launch(args);
    }
}
