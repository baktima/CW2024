package com.example.demo;

import java.io.IOException;

import com.example.demo.controller.Controller;
import com.example.demo.display.menu.MainMenu;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The entry point for the Sky Battle game application.
 * This class initializes the main stage and launches the game's main menu.
 */
public class Main extends Application {

	private static final String TITLE = "Sky Battle";

	/**
	 * @hidden
	 * default constructor
	 */
	public Main(){
		// No functionality needed
	}

	/**
	 * The entry point of the JavaFX application.
	 * Sets up the main game window, initializes the {@link Controller}, and displays the main menu.
	 *
	 * @param stage The primary stage for the JavaFX application.
	 * @throws SecurityException        If there is an issue with security configurations.
	 * @throws IllegalArgumentException If invalid arguments are passed during stage initialization.
	 */
    @Override
	public void start(Stage stage) throws SecurityException, IllegalArgumentException {
		stage.setTitle(TITLE);
		stage.setResizable(false);
        Controller myController = new Controller(stage);

		try {
			MainMenu.showMainMenu(stage, myController);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The main method to launch the application.
	 *
	 * @param args Command-line arguments (not used).
	 */
	public static void main(String[] args) {
		launch();
	}
}
