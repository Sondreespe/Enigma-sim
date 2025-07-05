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

        //reflektor
        Reflector reflector = new Reflector();

        // kryptering mekanismen
        CryptoRotor rotor1Mek = new CryptoRotor(CryptoRotor.ROTOR_I, 0);
        CryptoRotor rotor2Mek = new CryptoRotor(CryptoRotor.ROTOR_I, 0);
        CryptoRotor rotor3Mek = new CryptoRotor(CryptoRotor.ROTOR_I, 0);


        // pluggboard + reset-knapp i samme panel
        VBox resetBox = new VBox(resetButton);
        resetBox.setAlignment(Pos.CENTER);

        VBox plugboardPanel = new VBox(10);
        plugboardPanel.getChildren().addAll(plugboardPane, resetBox);
        plugboardPanel.setPadding(new Insets(10));
        plugboardPanel.setStyle("-fx-background-color: rgba(255,255,255,0.2);");

        // Layoutet for selve vinduet-----------------------------------------------
        BorderPane root = new BorderPane();

        root.setTop(rotor);

        root.setCenter(keyboardPane);
        BorderPane.setMargin(keyboardPane, new Insets(10, 0, 0, 0));

        root.setBottom(plugboardPanel);

        root.setStyle("-fx-background-image: url('/enigma/bg1.jpg'); -fx-background-size: cover;");

        Scene scene = new Scene(root, 1000, 800);

        //-----------------------------------------------------------------------------

        //Selve mekanismen i prosjektet : tastetrykk
        scene.setOnKeyPressed(event -> {
            String key = event.getText();
            if (key.matches("[a-zA-Z]")) {

                //faktisk tastetrykk
                char letter = key.charAt(0); // henter tegnet på trykket knapp
        
                // fra tastetrykk gjennom plugboardet
                char substituted = plugboardPane.substitute(letter); // oversetter bokstaven via pluggboardet

                // fra bokastav til index, 0-25
                int index = Character.toUpperCase(substituted) - 'A'; 

                // fra plugboard, gjennom rotorene
                index = rotor3Mek.firstRotorPassage(index);
                index = rotor2Mek.firstRotorPassage(index);
                index = rotor1Mek.firstRotorPassage(index);

                // reflektor
                int reflectedIndex = reflector.reflect(index);

                // fra reflektor gjennom rotorene igjen
                index = rotor1Mek.secondRotorPassage(reflectedIndex);
                index = rotor2Mek.secondRotorPassage(index);
                index = rotor3Mek.secondRotorPassage(index);
        
              
                // fra index tilbake til bokstav
                char encryptedLetter = (char) ('A' + index);

                keyboard.highlightKey(encryptedLetter); // highlighter den oversatte bokstaven

                // roterer rotoren lengst til høyre for hvert tastetrykk
                rotors.rotateUp(2); 

                // samlet oversikt av bosktav flow
                System.out.println("Original trykk: " + Character.toUpperCase(letter) + " ->  Etter pluggboard: " + substituted + 
                                    " -> Etter reflektor: " + index);

                // oversitk over rotorene 
                System.out.println("Rotor1: " + (rotors.getRotor1Value() ) +
                " | Rotor2: " + (rotors.getRotor2Value()) +
                " | Rotor3: " + (rotors.getRotor3Value()));

                System.out.println("Koblinger:");
                System.out.println(plugboardPane.getPlugMap().toString());

                System.out.println("-------------------------------------------");
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