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
                int indexReflector = Character.toUpperCase(substituted) - 'A'; 

                // fra plugboard, gjennom rotorene
                int index1pass = rotor3Mek.firstRotorPassage(indexReflector);
                int index2pass = rotor2Mek.firstRotorPassage(index1pass);
                int index3pass = rotor1Mek.firstRotorPassage(index2pass);

                // reflektor
                int reflectedIndex = reflector.reflect(index3pass);

                // fra reflektor gjennom rotorene igjen
                int index1rev = rotor1Mek.secondRotorPassage(reflectedIndex);
                int index2rev = rotor2Mek.secondRotorPassage(index1pass);
                int index3rev = rotor3Mek.secondRotorPassage(index2rev);
        
              
                // fra index tilbake til bokstav
                char encryptedLetter = (char) ('A' + index3rev); // konverterer index til bokstav

                keyboard.highlightKey(encryptedLetter); // highlighter den oversatte bokstaven

                // roterer rotoren lengst til høyre for hvert tastetrykk, bare GUI-en
                rotors.rotateUp(2); 

                //roteringsmekanisme for krypteringen
                rotor3Mek.step(); // roterer den første rotoren
                // sjekk om rotor 3 har gått rundt
                if (rotor3Mek.getPosition() == 0) {
                    rotor2Mek.step();
                    rotors.rotateUp(1);
                }
                // sjekk om rotor 2 har gått rundt
                if (rotor2Mek.getPosition() == 0) {
                rotor1Mek.step();
                rotors.rotateUp(0);
                }
                      

                // samlet oversikt av bosktav flow
                System.out.println("Original trykk: " + Character.toUpperCase(letter) + " ->  Etter pluggboard: " + substituted);
                System.out.println("Etter 1.rotor: " + index1pass + " -> etter 2.rotor: " + index2pass + " -> etter 3.rotor: " + index3pass);
                System.out.println("Gjennom reflektor: " + reflectedIndex);
                System.out.println("Etter 3.rotor: " + reflectedIndex + " -> Etter 2.rotor: " + index2rev + " -> Etter 3.rotor: " + index3rev);
                System.out.println("Kryptert bokstav: " + encryptedLetter);

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