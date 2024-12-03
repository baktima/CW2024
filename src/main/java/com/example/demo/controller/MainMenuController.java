package com.example.demo.controller;

import com.example.demo.display.menu.LevelMenu;
import com.example.demo.sound.GameMusic;
import com.example.demo.sound.SoundEffects;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.util.Duration;


public class MainMenuController {

	private Stage stage;
	private Controller gameController;
	private static final int DURATION = 300;
	private final GameMusic gameMusic = GameMusic.getInstance();

	@FXML
	private Slider soundEffect;
	@FXML
	private Slider backgroundMusic;
	@FXML
	private ToolBar volumeSliders;
	@FXML
	private Text sFX;
	@FXML
	private Text music;

	/**
     * Initializes the main menu with the given stage and game controller.
     *
     * @param stage         The primary stage of the application.
     * @param gameController The main game controller to manage the game transitions.
     */

	public void initialize(Stage stage, Controller gameController) {
		this.stage = stage;
		this.gameController = gameController;

		initializeSoundEffect();
		initializeBackgroundMusic();

		gameMusic.playBackgroundMusic();
	}
	
	@FXML
	private void buttonPlay() { 
		try {
			LevelMenu.showLevelMenu(stage,gameController);
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}

	@FXML
	private void buttonExit() {
		gameMusic.stopMusic();
		javafx.application.Platform.exit();
	}

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
			fadeOut.setOnFinished(e ->
			{
				volumeSliders.setVisible(false);
				sFX.setVisible(false);
				music.setVisible(false);
			});
			fadeOut.play();
		}
	}

	private void initializeSoundEffect(){
		soundEffect.setValue(SoundEffects.getInitialSfxVolume() * 100); // Convert 0-1 to 0-100

		// Add a listener to update the global volume when the slider changes
		soundEffect.valueProperty().addListener((obs, oldVal, newVal) -> {
			double newVolume = newVal.doubleValue() / 100; // Convert 0-100 to 0-1 range
			SoundEffects.setInitialSfxVolume(newVolume);
		});
	}

	private void initializeBackgroundMusic() {
		backgroundMusic.setValue(gameMusic.getVolume() * 100); // Convert 0-1 to 0-100

		backgroundMusic.valueProperty().addListener((obs, oldVal, newVal) -> {
			double newVolume = newVal.doubleValue() / 100; // Convert 0-100 to 0-1 range
			gameMusic.setVolume(newVolume);
		});
	}
}
