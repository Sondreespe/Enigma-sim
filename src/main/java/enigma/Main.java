package enigma;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Keyboard keyboard = new Keyboard();
        var kbPane = keyboard.createKeyboard();

        // VBox for fleksibilitet
        VBox vbox = new VBox(kbPane);
        vbox.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(vbox);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 800, 600);

        // legg inn tastetrykk
        scene.setOnKeyPressed(event -> {
            String key = event.getText();
            if (key.matches("[a-zA-Z]")) {
                keyboard.highlightKey(key.charAt(0));
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Enigma UI");
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