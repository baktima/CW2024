package com.example.demo.controller;

import com.example.demo.level.LevelParent;
import com.example.demo.display.MainMenu;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import com.example.demo.sound.SoundEffects;

import java.io.IOException;

public class PauseMenuController {

    private LevelParent levelParent;
    @FXML
    private Slider soundEffect;
    @FXML
    ToolBar volumeSliders;
    private SoundEffects soundEffects;

    public void initialize(LevelParent levelParent) {
        this.levelParent = levelParent;

        initializingSoundEffect();

    }

    @FXML
    private void resumeButton() {
        levelParent.resumeGame();
    }

    //still kinda funky need to fix this instantly
    @FXML
    private void restartButton() {
    	levelParent.restartGame();
    }

    //it works now
    @FXML
    private void exitButton() throws IOException {

        //the cleanup is only semi
        levelParent.cleanup();

        Stage stage = levelParent.getStage();
        Controller controller = new Controller(stage);
        MainMenu.showMainMenu(stage, controller);
    }

    @FXML
    private void toggleVolumeSliders() {
        boolean currentlyVisible = volumeSliders.isVisible();
        if (!currentlyVisible) {
            volumeSliders.setVisible(true);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), volumeSliders);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();
        } else {
            FadeTransition fadeOut = new FadeTransition(Duration.millis(300), volumeSliders);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(e -> volumeSliders.setVisible(false));
            fadeOut.play();
        }
    }
    private void initializingSoundEffect(){
        soundEffects = new SoundEffects();

        // Set the slider value to the current global volume
        soundEffect.setValue(SoundEffects.getGlobalVolume() * 100); // Convert 0-1 to 0-100

        // Add a listener to update the global volume when the slider changes
        soundEffect.valueProperty().addListener((obs, oldVal, newVal) -> {
            double newVolume = newVal.doubleValue() / 100; // Convert 0-100 to 0-1 range
            SoundEffects.setGlobalVolume(newVolume);
        });


    }
}
