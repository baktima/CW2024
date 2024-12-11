package com.example.demo.display;

import javafx.scene.Group;
import javafx.scene.control.Label;

/**
 * The {@code TextDisplay} class manages the on-screen text elements for the game,
 * such as the kill counter, pause instructions, and skill timer.
 * It provides methods to update and reset these elements dynamically during gameplay.
 */
public class TextDisplay {
    private int count;

    private final Label bossHealthLabel;
    private final Label counterLabel;
    private final Label pauseInstructionLabel;
    private final Label skillTimerLabel;

    private static final int X_POSITION_COUNTER_LABEL = 1150;
    private static final int Y_POSITION_COUNTER_LABEL = 30;

    private static final int X_POSITION_INSTRUCTION_LABEL = 5; // Position for the instruction text
    private static final int Y_POSITION_INSTRUCTION_LABEL = 80;

    private static final int X_POSITION_SKILL_TIMER_LABEL = 400;
    private static final int Y_POSITION_SKILL_TIMER_LABEL = 50;

    private static final int X_POSITION_BOSS_HIT_LABEL = 1050;
    private static final int Y_POSITION_BOSS_HIT_LABEL = 30;

    /**
     * Constructs a {@code TextDisplay} instance and initializes the text elements
     * with default values and positions.
     */
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

        skillTimerLabel = new Label("Skill Ready Press S To Clear All Projectile");
        skillTimerLabel.setStyle("-fx-font-size: 20; -fx-fill: white;");
        skillTimerLabel.setLayoutX(X_POSITION_SKILL_TIMER_LABEL); // Position X (adjust as needed)
        skillTimerLabel.setLayoutY(Y_POSITION_SKILL_TIMER_LABEL); // Position Y (adjust as needed)

        bossHealthLabel = new Label("Boss Health: 0");
        bossHealthLabel.setStyle("-fx-font-size: 30; -fx-text-fill: black;");
        bossHealthLabel.setLayoutX(X_POSITION_BOSS_HIT_LABEL);
        bossHealthLabel.setLayoutY(Y_POSITION_BOSS_HIT_LABEL);
    }

    /**
     * Increments the kill counter by 1 and updates the kill counter label.
     */
    public void increment() {
        count++;
        updateLabel();
    }

    /**
     * Resets the kill counter to 0 and updates the kill counter label.
     */
    public void reset() {
        count = 0;
        updateLabel();
    }

    /**
     * Updates the kill counter label to reflect the current count.
     */
    private void updateLabel() {
        counterLabel.setText("Kills: " + count);
    }

    /**
     * Updates the text of the skill timer label.
     *
     * @param text the new text for the skill timer
     */
    public void updateSkillTimer(String text) {
        skillTimerLabel.setText(text);
    }

    /**
     * Returns the skill timer label.
     *
     * @return the {@link Label} representing the skill timer
     */
    public Label getSkillTimerLabel(){
        return skillTimerLabel;
    }

    /**
     * Updates the boss hit label to reflect the current hit count.
     *
     *  @param health the current health of the boss to be displayed on the label.
     */
    public void updateBossHealth(int health) {
        bossHealthLabel.setText("Boss Health: " + health);
    }

    /**
     * Adds all text elements (kill counter, pause instructions, skill timer) to the given root.
     *
     * @param root the root {@link Group} to which the text elements will be added
     */
    public void addToRoot(Group root) {
        root.getChildren().addAll(counterLabel, pauseInstructionLabel, skillTimerLabel, bossHealthLabel);
    }

    /**
     * Setting the visibility of the boss hit text.
     *
     * @param visible true or false
     */
    public void setBossHealthVisible(boolean visible) {
        bossHealthLabel.setVisible(visible);
    }

    /**
     * Setting the visibility of the counter kill text.
     *
     * @param visible true or false
     */
    public void setCounterVisible(boolean visible){
        counterLabel.setVisible(visible);
    }
}
