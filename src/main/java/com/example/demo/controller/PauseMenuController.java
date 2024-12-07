package com.example.demo.controller;

import com.example.demo.level.LevelParent;
import com.example.demo.display.menu.MainMenu;
import com.example.demo.sound.GameMusic;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import com.example.demo.sound.SoundEffects;

import java.io.IOException;

/**
 * Controller for the Pause Menu in the game.
 * Manages functionality such as resuming the game, restarting, exiting to the main menu,
 * and controlling volume sliders.
 */
public class PauseMenuController {

    private LevelParent levelParent;
    private static final int DURATION = 300;
    private final GameMusic gameMusic = GameMusic.getInstance();
    @FXML
    private Slider soundEffect;
    @FXML
    private ToolBar volumeSliders;
    @FXML
    private Slider backgroundMusic;
    @FXML
    private Text sFX;
    @FXML
    private Text music;

    /**
     * Initializes the Pause Menu with the current level instance.
     *
     * @param levelParent the current level instance.
     */
    public void initialize(LevelParent levelParent) {
        this.levelParent = levelParent;
        initializingSoundEffect();
        initializeBackgroundMusic();

        gameMusic.playBackgroundMusic();
    }

    /**
     * Handles the "Resume" button action.
     * Resumes the game from the paused state.
     */
    @FXML
    private void resumeButton() {
        levelParent.resumeGame();
    }

    /**
     * Handles the "Restart" button action.
     * Restarts the game from the beginning of the current level.
     */
    @FXML
    private void restartButton() {
    	levelParent.restartGame();
    }

    /**
     * Handles the "Exit" button action.
     * Cleans up the current level and transitions back to the main menu.
     *
     * @throws IOException if there is an issue loading the main menu resources.
     */
    @FXML
    private void exitButton() throws IOException {

        levelParent.cleanup();
        Stage stage = levelParent.getStage();
        Controller controller = new Controller(stage);
        MainMenu.showMainMenu(stage, controller);
    }

    /**
     * Toggles the visibility of the volume sliders.
     * Includes a fade-in and fade-out animation for smoother transitions.
     */
    @FXML
    private void toggleVolumeSliders() {
        boolean currentlyVisible = volumeSliders.isVisible();
        if (!currentlyVisible) {
            volumeSliders.setVisible(true);
            sFX.setVisible(true);
            music.setVisible(true);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(DURATION), volumeSliders);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();
        } else {
            FadeTransition fadeOut = new FadeTransition(Duration.millis(DURATION), volumeSliders);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(e -> {
                volumeSliders.setVisible(false);
                sFX.setVisible(false);
                music.setVisible(false);
            });
            fadeOut.play();
        }
    }

    /**
     * Initializes the sound effect slider and binds it to the global volume.
     * The slider dynamically updates the global volume level as it is adjusted.
     */
    private void initializingSoundEffect(){
        // Set the slider value to the current global volume
        soundEffect.setValue(SoundEffects.getInitialSfxVolume() * 100); // Convert 0-1 to 0-100

        // Add a listener to update the global volume when the slider changes
        soundEffect.valueProperty().addListener((obs, oldVal, newVal) -> {
            double newVolume = newVal.doubleValue() / 100; // Convert 0-100 to 0-1 range
            SoundEffects.setInitialSfxVolume(newVolume);
        });
    }

    /**
     * Initializes the sound effect slider and binds it to the global volume.
     * The slider dynamically updates the global volume level as it is adjusted.
     */
    private void initializeBackgroundMusic() {
        backgroundMusic.setValue(gameMusic.getVolume() * 100); // Convert 0-1 to 0-100

        backgroundMusic.valueProperty().addListener((obs, oldVal, newVal) -> {
            double newVolume = newVal.doubleValue() / 100; // Convert 0-100 to 0-1 range
            gameMusic.setVolume(newVolume);
        });
    }
}
