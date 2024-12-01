package com.example.demo.display;

import javafx.scene.Group;
import javafx.scene.control.Label;

public class TextDisplay {
    private int count;

    private final Label counterLabel;
    private final Label pauseInstructionLabel;
    private final Label skillTimerLabel;

    private static final int X_POSITION_COUNTER_LABEL = 1150;
    private static final int Y_POSITION_COUNTER_LABEL = 30;

    private static final int X_POSITION_INSTRUCTION_LABEL = 5; // Position for the instruction text
    private static final int Y_POSITION_INSTRUCTION_LABEL = 80;

    private static final int X_POSITION_SKILL_TIMER_LABEL = 400;
    private static final int Y_POSITION_SKILL_TIMER_LABEL = 50;

    public TextDisplay() {
        count = 0;
        counterLabel = new Label("Kill" + ": " + count);
        counterLabel.setStyle("-fx-font-size: 30; -fx-text-fill: black;");
        counterLabel.setLayoutX(X_POSITION_COUNTER_LABEL);
        counterLabel.setLayoutY(Y_POSITION_COUNTER_LABEL);

        pauseInstructionLabel = new Label("Press ESC button to toggle pause");
        pauseInstructionLabel.setStyle("-fx-font-size: 20; -fx-text-fill: gray;");
        pauseInstructionLabel.setLayoutX(X_POSITION_INSTRUCTION_LABEL);
        pauseInstructionLabel.setLayoutY(Y_POSITION_INSTRUCTION_LABEL);

        skillTimerLabel = new Label("Skill Ready");
        skillTimerLabel.setStyle("-fx-font-size: 20; -fx-fill: white;");
        skillTimerLabel.setLayoutX(X_POSITION_SKILL_TIMER_LABEL); // Position X (adjust as needed)
        skillTimerLabel.setLayoutY(Y_POSITION_SKILL_TIMER_LABEL); // Position Y (adjust as needed)
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

    public void updateSkillTimer(String text) {
        skillTimerLabel.setText(text);
    }

    public Label getSkillTimerLabel(){
        return skillTimerLabel;
    }

    public void addToRoot(Group root) {
        root.getChildren().addAll(counterLabel, pauseInstructionLabel, skillTimerLabel);
    }
}
