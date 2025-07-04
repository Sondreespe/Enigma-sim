package enigma;

import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import java.util.HashMap;
import java.util.Map;

public class PlugboardPane extends GridPane {

    private Map<Character, Character> plugMap = new HashMap<>();
    private Button[] buttons = new Button[26];

    private Character firstSelected = null; // instans for første valgte bokstav, enkel mekanisme for å lage par

    private final String[] colors = { // 10 farger for 10 mulige par i plugboardet
            "orange", "cyan", "lightgreen", "plum", "yellow", "lightpink",
            "lightblue", "gold", "lightcoral", "khaki"
    };
    private int colorIndex = 0;

    public PlugboardPane() {
        setHgap(10);
        setVgap(10);
        setAlignment(Pos.CENTER);

        // lager kanpper for bokstavene
        for (int i = 0; i < 26; i++) {
            char c = (char) ('A' + i);
            Button btn = new Button(Character.toString(c));
            btn.setPrefWidth(40);
            btn.setPrefHeight(40);

            btn.setOnAction(e -> handlePlugClick(c));

            buttons[i] = btn;
            add(btn, i % 13, i / 13); // 2 rader
        }
    }


    private void handlePlugClick(char letter) {
        if (firstSelected == null) {
            // første bokstav valgt
            if (plugMap.containsKey(letter)) { // allerede koblet
                System.out.println("Allerede koblet!");
                return;
            }
            firstSelected = letter;
            highlightButton(letter, "gray");
        } else {
            if (firstSelected == letter) { // dobbelklikk fjerner markering
                // samme bokstav, fjern markering
                highlightButton(letter, "");
                firstSelected = null;
                return;
            }
            // håndtering av antall koblinger
            if (plugMap.size() / 2 >= 10) {
                System.out.println("Maks 10 koblinger!");
                highlightButton(firstSelected, "");
                firstSelected = null;
                return;
            }

            // koble sammen, legger inn begge veier
            plugMap.put(firstSelected, letter);
            plugMap.put(letter, firstSelected);

            // fargelegger parrene
            String pairColor = colors[colorIndex % colors.length];
            highlightButton(firstSelected, pairColor);
            highlightButton(letter, pairColor);

            colorIndex++; // itererer fargeindeksen for neste par

            System.out.println("Koblet " + firstSelected + " ↔ " + letter);

            // nullstiller førstevalget og er klar for et nytt par
            firstSelected = null;
        }
    }

    // funksjon som fargelegger gitt knapp med gitt farge
    private void highlightButton(char letter, String color) {
        int index = letter - 'A';
        if (color.isEmpty()) {
            buttons[index].setStyle("");
        } else {
            buttons[index].setStyle("-fx-background-color: " + color + ";");
        }
    }

    /**
     * Dersom en bokstav er koblet til en annen bokstav i plugboard vil de byttes om. Dette gjelder begge veier. 
     * Trykk av en bokstav som ikke er koblet til noe vil returnere den samme bokstaven.
     * @param input bokstaven som trykkes
     * @return bokstaven som kommer ut etter plugboard
     */
    public char substitute(char input) {
        char upper = Character.toUpperCase(input);
        return plugMap.getOrDefault(upper, upper);
    }

    /**
     * nulstiller plugboardet, fjerner alle alle koblinger og farger.
     */
    public void reset() {
        plugMap.clear();
        firstSelected = null;
        colorIndex = 0;
        for (Button b : buttons) {
            b.setStyle("");
        }
    }
}
