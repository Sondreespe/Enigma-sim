package enigma;

import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.geometry.Pos;

public class GUIRotor implements IRotor {

    private int position = 0;
    private final Label rotorLabel;
    private final VBox rotorBox;
    private Runnable onPositionChanged; // callback

    //Oppretter GUIRotor med en label og knapper for opp og ned
    public GUIRotor() {
        rotorLabel = new Label();
        rotorLabel.setStyle(
            "-fx-font-size: 32px;" +
            "-fx-padding: 10px;" +
            "-fx-background-color: black;" +
            "-fx-text-fill: lightgrey;" +
            "-fx-min-width: 60px;" +
            "-fx-min-height: 60px;" +
            "-fx-alignment: center;"
        );

        Button upButton = new Button("↑");
        upButton.setOnAction(e -> stepUp());

        Button downButton = new Button("↓");
        downButton.setOnAction(e -> stepDown());

        rotorBox = new VBox(10, upButton, rotorLabel, downButton);
        rotorBox.setAlignment(Pos.CENTER);

        updateDisplay();
    }

    @Override
    public void stepUp() {
        position = (position + 1) % 26;
        updateDisplay();
        if (onPositionChanged != null) onPositionChanged.run();
    }

    @Override
    public void stepDown() {
        position = (position - 1 + 26) % 26;
        updateDisplay();
        if (onPositionChanged != null) onPositionChanged.run();
    }

    @Override
    public void setPos(int pos) {
        position = pos % 26;
        updateDisplay();
        if (onPositionChanged != null) onPositionChanged.run();
    }

    @Override
    public int getPos() {
        return position;
    }

    @Override
    public void reset() {
        setPos(0);
        updateDisplay();
    }

    private void updateDisplay() {
        char letter = (char) ('A' + position);
        rotorLabel.setText(Character.toString(letter));
    }

    public VBox getView() {
        return rotorBox;
    }

    public void setOnPositionChanged(Runnable callback) {
        this.onPositionChanged = callback;
    }
}