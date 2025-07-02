package enigma;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import java.util.HashMap;
import java.util.Map;


public class Keyboard {
    private Map<Character, Button> keyMap = new HashMap<>();

    public VBox createKeyboard() { // Vbox for å style tastaturet
    VBox keyboardVBox = new VBox(20);  // spacing mellom rader, 20 px
    keyboardVBox.setAlignment(Pos.CENTER);// midtstill tastaturet

    String[] rows = { //lager radene på et engelsk tastatur
        "QWERTYUIOP",
        "ASDFGHJKL",
        "ZXCVBNM"
    };

    for (String row : rows) {
        HBox rowBox = new HBox(10);  // lager an box for hver radspacing mellom bokstaver
        rowBox.setAlignment(Pos.CENTER);

        for (char c : row.toCharArray()) {
            Button btn = new Button(Character.toString(c));// en hver knapp for hver bokstav i hver row
            btn.setPrefWidth(60);// str på knappen
            btn.setPrefHeight(60);
            btn.setStyle( // styling
                "-fx-background-radius: 50%; " +
                "-fx-background-color: lightgray; " +
                "-fx-border-width: 2px; " +
                "-fx-font-size: 18px;"
            );
            keyMap.put(c, btn);// legger til knappen i keyMap for å kunne referere til den senere
            rowBox.getChildren().add(btn);// legger til knappen i rowBox
        }
        keyboardVBox.getChildren().add(rowBox); // legger alle radene til i parent boxen.
    }

    return keyboardVBox;
    }
   

    // nytt:
    public void highlightKey(char letter) {
        Button btn = keyMap.get(Character.toUpperCase(letter));
        if (btn != null) {
            btn.setStyle(
                "-fx-background-radius: 50%; " +
                "-fx-background-color: yellow; " +
                "-fx-border-width: 2px; " +
                "-fx-font-size: 18px;"
            );
            // skru av “lyset” etter 300 ms
            new Thread(() -> {
                try { Thread.sleep(500); } catch (InterruptedException ignored) {}
                javafx.application.Platform.runLater(() -> btn.setStyle(
                    "-fx-background-radius: 50%; " +
                    "-fx-background-color: lightgray; " +
                    "-fx-border-width: 2px; " +
                    "-fx-font-size: 18px;"
                ));
            }).start();
        }
    }
}