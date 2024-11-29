package com.example.demo.display;

import javafx.scene.Group;
import javafx.scene.control.Label;

public class KillDisplay {
    private int count;
    private final Label counterLabel;
    private static final int X_POSITION = 1150;
    private static final int Y_POSITION = 30;

    public KillDisplay() {
        count = 0;
        counterLabel = new Label("Kill" + ": " + count);
        counterLabel.setStyle("-fx-font-size: 30; -fx-text-fill: black;");
        counterLabel.setLayoutX(X_POSITION);
        counterLabel.setLayoutY(Y_POSITION);
    }

    public void increment() {
        count++;
        updateLabel();
    }

    public void reset() {
        count = 0;
        updateLabel();
    }

    private void updateLabel() {
        counterLabel.setText("Kills: " + count);
    }

    public void addToRoot(Group root) {
        root.getChildren().add(counterLabel);
    }
}
