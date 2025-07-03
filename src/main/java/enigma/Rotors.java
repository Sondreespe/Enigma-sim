package enigma;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.geometry.Pos;

public class Rotors {
    private Label[] rotorLabels = new Label[3];
    private int rotor1Value = 0;
    private int rotor2Value = 0;
    private int rotor3Value = 0;

    public HBox createRotors() {

        HBox rotorBox = new HBox(40); // avstand mellom rotorene
        rotorBox.setAlignment(Pos.CENTER);
        rotorBox.setStyle("-fx-padding: 60 0 0 0;"  ); // 30px padding på toppen
        
        

        for (int i = 0; i < 3; i++) {
            int rotorIndex = i;

            VBox singleRotor = new VBox(10); // vertikal oppsett med 3 rotorer
            singleRotor.setAlignment(Pos.CENTER);
            

            Label rotorLabel = new Label("0"); // initialiserer med 0,
            rotorLabel.setStyle( // styling
                "-fx-font-size: 32px;" + 
                "-fx-padding: 10px;" + 
                "-fx-background-color: black;" +
                "-fx-text-fill: lightgrey;" + 
                "-fx-min-width: 60px; " +
                "-fx-min-height: 60px; " +
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
    
   
    
    private void rotateUp(int index) {
        switch (index) {
            case 0:
                rotor1Value = (rotor1Value + 1) % 26;
                rotorLabels[0].setText(Integer.toString(rotor1Value));
                break;
            case 1:
                rotor2Value = (rotor2Value + 1) % 26;
                rotorLabels[1].setText(Integer.toString(rotor2Value));
                break;
            case 2:
                rotor3Value = (rotor3Value + 1) % 26;
                rotorLabels[2].setText(Integer.toString(rotor3Value));
                break;
        }

        //sjekk for å se om instansene lagres ordentlig
        System.out.println(getRotor1Value());
        System.out.println(getRotor2Value());
        System.out.println(getRotor3Value());
        System.out.println("____________");
        
    }

    private void rotateDown(int index) {
        switch (index) {
            case 0:
                rotor1Value = (rotor1Value - 1 + 26) % 26;
                rotorLabels[0].setText(Integer.toString(rotor1Value));
                break;
            case 1:
                rotor2Value = (rotor2Value - 1 + 26) % 26;
                rotorLabels[1].setText(Integer.toString(rotor2Value));
                break;
            case 2:
                rotor3Value = (rotor3Value - 1 + 26) % 26;
                rotorLabels[2].setText(Integer.toString(rotor3Value));
                break;
        }
    }

    public int getRotor1Value(){
        return rotor1Value;
    }

    public int getRotor2Value(){
        return rotor2Value;
    }

    public int getRotor3Value(){
        return rotor3Value;
    }   


    
   
}