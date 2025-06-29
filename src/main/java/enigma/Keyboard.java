package enigma;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import java.util.HashMap;
import java.util.Map;


public class Keyboard {
    private Map<Character, Button> keyMap = new HashMap<>();

    public GridPane createKeyboard() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        String alphabet = "QWERTYUIOPASDFGHJKLZXCVBNM";
        for (int i = 0; i < alphabet.length(); i++) {
            char c = alphabet.charAt(i);
            Button btn = new Button(Character.toString(c));
            btn.setPrefWidth(60);
            btn.setPrefHeight(60);
            btn.setStyle(
                "-fx-background-radius: 50%; " +
                "-fx-background-color: lightgray; " +
                "-fx-border-width: 2px; " +
                "-fx-font-size: 18px;"
            );
            keyMap.put(c, btn);
            int row = i / 9;
            int col = i % 9;
            grid.add(btn, col, row);
        }
        return grid;
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
                try { Thread.sleep(3000); } catch (InterruptedException ignored) {}
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