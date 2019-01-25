package chess;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PromoteBox {

    static String answer;

    public static String display(String title, String message, Color color) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        Label label = new Label();
        label.setText(message);

        window.setOnCloseRequest(e -> {
            e.consume(); //Ezzel átvettem a Close event kezelését, enélkül mindenképpen bezárná az ablakot
        });

        Button queenButton = new Button();
        if (color == Color.WHITE) {
            queenButton.setGraphic(new ImageView("QueenWhite.png"));
        } else {
            queenButton.setGraphic(new ImageView("QueenBlack.png"));
        }

        Button rookButton = new Button();
        if (color == Color.WHITE) {
            rookButton.setGraphic(new ImageView("RookWhite.png"));
        } else {
            rookButton.setGraphic(new ImageView("RookBlack.png"));
        }

        Button bishopButton = new Button();
        if (color == Color.WHITE) {
            bishopButton.setGraphic(new ImageView("BishopWhite.png"));
        } else {
            bishopButton.setGraphic(new ImageView("BishopBlack.png"));
        }

        Button knightButton = new Button();
        if (color == Color.WHITE) {
            knightButton.setGraphic(new ImageView("KnightWhite.png"));
        } else {
            knightButton.setGraphic(new ImageView("KnightBlack.png"));
        }

        queenButton.setOnAction(e -> {
            answer = "q";
            window.close();
        });
        rookButton.setOnAction(e -> {
            answer = "r";
            window.close();
        });
        bishopButton.setOnAction(e -> {
            answer = "b";
            window.close();
        });
        knightButton.setOnAction(e -> {
            answer = "k";
            window.close();
        });


        VBox layout = new VBox(10);

        layout.getChildren().addAll(label, queenButton, rookButton, bishopButton, knightButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}
