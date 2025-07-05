package enigma;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.geometry.Pos;

public class Rotors {
    private Label[] rotorLabels = new Label[3];

    // intansen med veridene til rotorene
    private int rotor1Value = 0;
    private int rotor2Value = 0;
    private int rotor3Value = 0;

    public HBox createRotors() { // lager en HBox for rotorene
        HBox rotorBox = new HBox(40); // avstand mellom rotorene
        rotorBox.setAlignment(Pos.CENTER);
        rotorBox.setStyle("-fx-padding: 60 0 0 0;"); // 60px ned fra toppen

        for (int i = 0; i < 3; i++) {
            int rotorIndex = i;

            VBox singleRotor = new VBox(10); // vertikalt layout for hver rotor
            singleRotor.setAlignment(Pos.CENTER);

            Label rotorLabel = new Label("1"); // starter på 1 for visning
            rotorLabel.setStyle(
                "-fx-font-size: 32px;" +
                "-fx-padding: 10px;" +
                "-fx-background-color: black;" +
                "-fx-text-fill: lightgrey;" +
                "-fx-min-width: 60px;" +
                "-fx-min-height: 60px;" +
                "-fx-alignment: center;"
            );
            rotorLabels[i] = rotorLabel;

            Button upButton = new Button("↑");
            Button downButton = new Button("↓");

            upButton.setOnAction(e -> rotateUp(rotorIndex));
            downButton.setOnAction(e -> rotateDown(rotorIndex));

            singleRotor.getChildren().addAll(upButton, rotorLabel, downButton);
            rotorBox.getChildren().add(singleRotor);
        }

        return rotorBox;
    }

    /**
     * Roterer opp rotoren angitt ved index. 
     * Har også en mekanisme for å rotere opp neste rotor dersom forrige rotor har gått en hel runde
     * @param index
     */
    public void rotateUp(int index) {
        switch (index) {
            case 2:
                rotor3Value = (rotor3Value + 1) % 26;
                rotorLabels[2].setText(Integer.toString(rotor3Value + 1)); // vis 1–26

                if (rotor3Value == 0) {
                    rotateUp(1); // steg rotor 2
                }
                break;

            case 1:
                rotor2Value = (rotor2Value + 1) % 26;
                rotorLabels[1].setText(Integer.toString(rotor2Value + 1));

                if (rotor2Value == 0) {
                    rotateUp(0); // steg rotor 1
                }
                break;

            case 0:
                rotor1Value = (rotor1Value + 1) % 26;
                rotorLabels[0].setText(Integer.toString(rotor1Value + 1));
                break;
        }
        // for kontroll og oversikt 
        System.out.println("Rotor1: " + (rotor1Value + 1) +
                " | Rotor2: " + (rotor2Value + 1) +
                " | Rotor3: " + (rotor3Value + 1));
    }

    // roterer ned, grei å ha for å slippe å gå en hel runde dersom man trykker feil. Derfor er den også private.
    private void rotateDown(int index) {
        switch (index) {
            case 0:
                rotor1Value = (rotor1Value - 1 + 26) % 26;
                rotorLabels[0].setText(Integer.toString(rotor1Value + 1));
                break;
            case 1:
                rotor2Value = (rotor2Value - 1 + 26) % 26;
                rotorLabels[1].setText(Integer.toString(rotor2Value + 1));
                break;
            case 2:
                rotor3Value = (rotor3Value - 1 + 26) % 26;
                rotorLabels[2].setText(Integer.toString(rotor3Value + 1));
                break;
        }
    }

    // getters for enkel tilgang

    /**
     * henter verdien til rotor 1
     * @return verdien til rotor 1, 1-26
     */
    public int getRotor1Value() {
        return rotor1Value + 1; // 1–26
    }

    /**
     * henter verdien til rotor 2
     * @return verdien til rotor 2, 1-26
     */
    public int getRotor2Value() {
        return rotor2Value + 1;
    }
    /**
     * henter verdien til rotor 3
     * @return verdien til rotor 3, 1-26
     */
    public int getRotor3Value() {
        return rotor3Value + 1;
    }

    /**
     * sender verien til den siste rotoren 
     * @return
     */
    public int reflector(){
        return getRotor1Value();
    }
}