package enigma;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
      
        // øvre del - rotorene
        Rotors rotors = new Rotors();
        var rotor = rotors.createRotors();

        // midtdelen - tastaturet
        Keyboard keyboard = new Keyboard();
        var keyboardPane = keyboard.createKeyboard();

        // nedre del - plugboard panel (placeholder)
        Label plugboardPlaceholder = new Label("Plugboard (kommer senere)");
        plugboardPlaceholder.setStyle("-fx-font-size: 24px; -fx-padding: 20px;");

        // BorderPane layout
        BorderPane root = new BorderPane();

        root.setTop(rotor);

        root.setCenter(keyboardPane);
        BorderPane.setMargin(keyboardPane, new Insets(10, 0, 0, 0));

        root.setBottom(plugboardPlaceholder);

        root.setStyle("-fx-background-image: url('/enigma/bg1.jpg'); " +
              "-fx-background-size: cover;");

        Scene scene = new Scene(root, 1000, 800); // lager vinduet som blir UI

        // Opplysning av tastetrykk
        scene.setOnKeyPressed(event -> {
            String key = event.getText();
            if (key.matches("[a-zA-Z]")) {//skiller ikke mellom store og små bokstaver
                keyboard.highlightKey(key.charAt(0));
                rotors.rotateFirstRotor();// roterer den første rotoren for hvert tastetrykk
            }
        });

        // spesifikasjoner for vinduet
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