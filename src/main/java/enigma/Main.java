package enigma;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {

        // === GUI-rotorer ===
        GUIRotor guiRotor1 = new GUIRotor();
        GUIRotor guiRotor2 = new GUIRotor();
        GUIRotor guiRotor3 = new GUIRotor();

        HBox rotorBox = new HBox(40, guiRotor1.getView(), guiRotor2.getView(), guiRotor3.getView());
        rotorBox.setAlignment(Pos.CENTER);
        rotorBox.setStyle("-fx-padding: 60 0 0 0;");

        // === Tastatur ===
        Keyboard keyboard = new Keyboard();
        var keyboardPane = keyboard.createKeyboard();

        // === Plugboard ===
        Plugboard plugboardPane = new Plugboard();
        plugboardPane.setPrefHeight(150);

        // reset-knapp
        Button resetButton = new Button("Reset Plugboard");
        resetButton.setOnAction(e -> {
            plugboardPane.reset();
            System.out.println("Plugboard nullstilt.");
            guiRotor1.reset();
            guiRotor2.reset();
            guiRotor3.reset();
        });

        // === Reflektor ===
        Reflector reflector = new Reflector();

        // === Crypto-rotorer ===
        CryptoRotor cryptoRotor1 = new CryptoRotor(CryptoRotor.ROTOR_I, 0);
        CryptoRotor cryptoRotor2 = new CryptoRotor(CryptoRotor.ROTOR_II, 0);
        CryptoRotor cryptoRotor3 = new CryptoRotor(CryptoRotor.ROTOR_III, 0);

        // === pluggboard + reset-knapp ===
        VBox plugboardPanel = new VBox(10, plugboardPane, resetButton);
        plugboardPanel.setAlignment(Pos.CENTER);
        plugboardPanel.setPadding(new Insets(10));
        plugboardPanel.setStyle("-fx-background-color: rgba(255,255,255,0.2);");

        // === Layout ===
        BorderPane root = new BorderPane();
        root.setTop(rotorBox);
        root.setCenter(keyboardPane);
        BorderPane.setMargin(keyboardPane, new Insets(10, 0, 0, 0));
        root.setBottom(plugboardPanel);
        root.setStyle("-fx-background-image: url('/enigma/bg1.jpg'); -fx-background-size: cover;");

        Scene scene = new Scene(root, 1000, 800);

        // === koble GUI-rotor til crypto-rotor ===
        guiRotor1.setOnPositionChanged(() -> cryptoRotor1.setPos(guiRotor1.getPos()));
        guiRotor2.setOnPositionChanged(() -> cryptoRotor2.setPos(guiRotor2.getPos()));
        guiRotor3.setOnPositionChanged(() -> cryptoRotor3.setPos(guiRotor3.getPos()));

        // === tastetrykk-håndtering ===
        scene.setOnKeyPressed(event -> {
            String key = event.getText();
            if (key.matches("[a-zA-Z]")) {
                char letter = key.charAt(0);

                // gjennom plugboard
                char substituted = plugboardPane.substitute(letter);

                // til index 0–25
                int idx = Character.toUpperCase(substituted) - 'A';

                // gjennom rotorene
                int p1 = cryptoRotor3.firstRotorPassage(idx);
                int p2 = cryptoRotor2.firstRotorPassage(p1);
                int p3 = cryptoRotor1.firstRotorPassage(p2);

                // reflektor
                int reflected = reflector.reflect(p3);

                // tilbake gjennom rotorene
                int b1 = cryptoRotor1.secondRotorPassage(reflected);
                int b2 = cryptoRotor2.secondRotorPassage(b1);
                int b3 = cryptoRotor3.secondRotorPassage(b2);

                // til bokstav
                char enc = (char) ('A' + b3);
                
                char substitutedBack = plugboardPane.substitute(enc);

                // highlight
                keyboard.highlightKey(substitutedBack);

                // steg rotorene (GUI + crypto)
                handleRotorStepping(cryptoRotor1, cryptoRotor2, cryptoRotor3,
                                    guiRotor1, guiRotor2, guiRotor3);

                // log
                System.out.println("=== Enigma Flow ===");
                System.out.println("Trykket: " + Character.toUpperCase(letter));
                System.out.println("Etter plugboard: " + substituted);

                // Første vei gjennom rotorene
                System.out.println("Rotor 3 frem: " + idx + " → " + p1);
                System.out.println("Rotor 2 frem: " + p1 + " → " + p2);
                System.out.println("Rotor 1 frem: " + p2 + " → " + p3);

                // Reflektor
                System.out.println("Reflektor: " + p3 + " → " + reflected);

                // Retur gjennom rotorene
                System.out.println("Rotor 1 tilbake: " + reflected + " → " + b1);
                System.out.println("Rotor 2 tilbake: " + b1 + " → " + b2);
                System.out.println("Rotor 3 tilbake: " + b2 + " → " + b3);

                // Resultat
                System.out.println("Etter plugboard tilbake: " + enc + " → " + substitutedBack);
                System.out.println("Resultatbokstav: " + substitutedBack);
                System.out.println("Rotorposisjonene:");
                System.out.println("  Rotor1: " + (guiRotor1.getPos()+1));
                System.out.println("  Rotor2: " + (guiRotor2.getPos()+1));
                System.out.println("  Rotor3: " + (guiRotor3.getPos()+1));
                System.out.println("Plugboard-mapping: " + plugboardPane.getPlugMap());
                System.out.println("=========================");
            }
        });

        // === vindu ===
        primaryStage.setScene(scene);
        primaryStage.setTitle("Enigma Simulator");
        primaryStage.show();
        primaryStage.toFront();
        primaryStage.requestFocus();
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
    }

    /**
     * Stepping-metoden som sikrer korrekt Enigma-logikk.
     * Kun aktivert for tastetrykk, ikke for knappene på GUI.
     */
    private void handleRotorStepping(CryptoRotor rotor1, CryptoRotor rotor2, CryptoRotor rotor3,
                                     GUIRotor gui1, GUIRotor gui2, GUIRotor gui3) {
        int prev3 = rotor3.getPos();

        rotor3.stepUp();
        gui3.setPos(rotor3.getPos());

        if (prev3 == 25) {
            int prev2 = rotor2.getPos();
            rotor2.stepUp();
            gui2.setPos(rotor2.getPos());

            if (prev2 == 25) {
                rotor1.stepUp();
                gui1.setPos(rotor1.getPos());
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}