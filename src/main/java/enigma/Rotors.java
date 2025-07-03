package enigma;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.geometry.Pos;

public class Rotors {
    private Label[] rotorLabels = new Label[3];
    private int rotor1value = 0;
    private int rotor2value = 0;
    private int rotor3value = 0;

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
    
    // henter ut verdiene fra rotorene i en array
    public int[] getRotorValues() {
        int[] values = new int[3];
        for (int i = 0; i < 3; i++) {
            values[i] = Integer.parseInt(rotorLabels[i].getText());
        }
            return values;
    }

    public void rotateFirstRotor() {
        rotateUp(2);
        int[] values =  getRotorValues();
        if (values[2] == 25){
            rotateSecondRotor();
        }
        
    }

    public void rotateSecondRotor(){
        rotateUp(1);
        int[] values = getRotorValues();
        if (values[1] == 25){
            rotateThirdRotor();
        }
    }

    public void rotateThirdRotor() {
        rotateUp(0);
    }


    private void rotateUp(int index) {
        int current = Integer.parseInt(rotorLabels[index].getText());
        current = (current + 1) % 26;
        rotorLabels[index].setText(Integer.toString(current));
        
    }

    private void rotateDown(int index) {
        int current = Integer.parseInt(rotorLabels[index].getText());
        current = (current - 1 + 26) % 26;
        rotorLabels[index].setText(Integer.toString(current));
    }
}