package enigma;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        // midtdelen - tastaturet
        Keyboard keyboard = new Keyboard();
        var keyboardPane = keyboard.createKeyboard();

        // øvre del - rotor panel (placeholder)
        Label rotorPlaceholder = new Label("Rotorer (kommer senere)");
        rotorPlaceholder.setStyle("-fx-font-size: 24px; -fx-padding: 20px;");

        // nedre del - plugboard panel (placeholder)
        Label plugboardPlaceholder = new Label("Plugboard (kommer senere)");
        plugboardPlaceholder.setStyle("-fx-font-size: 24px; -fx-padding: 20px;");

        // BorderPane layout
        BorderPane root = new BorderPane();
        root.setTop(rotorPlaceholder);
        root.setCenter(keyboardPane);
        root.setBottom(plugboardPlaceholder);

        Scene scene = new Scene(root, 1000, 800);

        // tastatur-lysstyring
        scene.setOnKeyPressed(event -> {
            String key = event.getText();
            if (key.matches("[a-zA-Z]")) {
                keyboard.highlightKey(key.charAt(0));
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Enigma Simulator");
        primaryStage.show();
        primaryStage.toFront();
        primaryStage.requestFocus();
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
    }

    public static void main(String[] args) {
        launch(args);
    }
}