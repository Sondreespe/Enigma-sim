package enigma;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
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

        // nedre del - pluggboard
        Plugboard plugboardPane = new Plugboard();
        plugboardPane.setPrefHeight(150);

        // reset-knapp
        Button resetButton = new Button("Reset Plugboard");
        resetButton.setOnAction(e -> {
            plugboardPane.reset();
            System.out.println("Plugboard nullstilt.");
        });

        // pluggboard + reset-knapp i samme panel
        VBox resetBox = new VBox(resetButton);
        resetBox.setAlignment(Pos.CENTER);

        VBox plugboardPanel = new VBox(10);
        plugboardPanel.getChildren().addAll(plugboardPane, resetBox);
        plugboardPanel.setPadding(new Insets(10));
        plugboardPanel.setStyle("-fx-background-color: rgba(255,255,255,0.2);");

        // Layoutet for selve vinduet
        BorderPane root = new BorderPane();
        root.setTop(rotor);
        root.setCenter(keyboardPane);
        BorderPane.setMargin(keyboardPane, new Insets(10, 0, 0, 0));
        root.setBottom(plugboardPanel);

        root.setStyle("-fx-background-image: url('/enigma/bg1.jpg'); -fx-background-size: cover;");

        Scene scene = new Scene(root, 1000, 800);

        // tastetrykk
        scene.setOnKeyPressed(event -> {
            String key = event.getText();
            if (key.matches("[a-zA-Z]")) {
                char letter = key.charAt(0); // henter tegnet på trykket knapp
                char substituted = plugboardPane.substitute(letter); // oversetter bokstaven via pluggboardet
                keyboard.highlightKey(substituted); // highlighter den oversatte bokstaven
                rotors.rotateUp(2); // roterer den siste rotoren opp for hver tastetrykk

                System.out.println(Character.toUpperCase(letter) + " → Etter pluggboard: " + substituted);
            }
        });

        

        // vindusinnstillinger
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