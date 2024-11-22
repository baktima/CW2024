package com.example.demo.controller;

import com.example.demo.sound.SoundEffects;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.util.Duration;


public class MainMenuController {

	private Stage stage; 
	private Controller gameController;
	@FXML
	private Slider soundEffect;
	@FXML
	ToolBar volumeSliders;
	private SoundEffects soundEffects;

	/**
     * Initializes the main menu with the given stage and game controller.
     *
     * @param stage         The primary stage of the application.
     * @param gameController The main game controller to manage the game transitions.
     */

	public void initialize(Stage stage, Controller gameController) {
		this.stage = stage;
		this.gameController = gameController;
		soundEffects = new SoundEffects();

		// Set the slider value to the current global volume
		soundEffect.setValue(SoundEffects.getGlobalVolume() * 100); // Convert 0-1 to 0-100

		// Add a listener to update the global volume when the slider changes
		soundEffect.valueProperty().addListener((obs, oldVal, newVal) -> {
			double newVolume = newVal.doubleValue() / 100; // Convert 0-100 to 0-1 range
			SoundEffects.setGlobalVolume(newVolume);
		});
	}
	
	@FXML
	private void buttonPlay() { 
		try {
            gameController.launchGame();
        } catch (Exception e) {
            e.printStackTrace();  // Handle or log exception as appropriate for your application
        }
		
	}

	@FXML
	private void buttonExit() {
		javafx.application.Platform.exit();
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
}
