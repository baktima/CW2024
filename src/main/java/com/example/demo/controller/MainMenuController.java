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

/**
 * Controller for the main menu of the game.
 * Handles interactions with the main menu's UI components,
 * such as buttons and sliders, and manages background music and sound effects.
 */
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
	 * Default constructor is private to prevent instantiation.
	 */
	private MainMenuController(){
		// not intended for instantiation
	}
	/**
	 * Initializes the main menu with the given stage and game controller.
	 * Sets up sound effect and background music sliders and starts playing background music.
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

	/**
	 * Navigates to the level menu when the "Play" button is clicked.
	 */
	@FXML
	private void buttonPlay() { 
		try {
			LevelMenu.showLevelMenu(stage,gameController);
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}

	/**
	 * Exits the game when the "Exit" button is clicked.
	 * Stops background music before exiting the application.
	 */
	@FXML
	private void buttonExit() {
		gameMusic.stopMusic();
		javafx.application.Platform.exit();
	}

	/**
	 * Toggles the visibility of the volume sliders when the settings icon is clicked.
	 * Uses a fade transition for smooth visibility changes.
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
			fadeOut.setOnFinished(e ->
			{
				volumeSliders.setVisible(false);
				sFX.setVisible(false);
				music.setVisible(false);
			});
			fadeOut.play();
		}
	}

	/**
	 * Initializes the sound effect slider and sets its default value.
	 * Adds a listener to update the global sound effect volume when the slider value changes.
	 */
	private void initializeSoundEffect(){
		soundEffect.setValue(SoundEffects.getInitialSfxVolume() * 100); // Convert 0-1 to 0-100

		// Add a listener to update the global volume when the slider changes
		soundEffect.valueProperty().addListener((obs, oldVal, newVal) -> {
			double newVolume = newVal.doubleValue() / 100; // Convert 0-100 to 0-1 range
			SoundEffects.setInitialSfxVolume(newVolume);
		});
	}

	/**
	 * Initializes the background music slider and sets its default value.
	 * Adds a listener to update the background music volume when the slider value changes.
	 */
	private void initializeBackgroundMusic() {
		backgroundMusic.setValue(gameMusic.getVolume() * 100); // Convert 0-1 to 0-100

		backgroundMusic.valueProperty().addListener((obs, oldVal, newVal) -> {
			double newVolume = newVal.doubleValue() / 100; // Convert 0-100 to 0-1 range
			gameMusic.setVolume(newVolume);
		});
	}
}
