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


//there's no main menu and there's no ending scene which makes everything confusing and at 22 second it will always crash out for some reason but it will stay running
//and also the amount of bullet can also cause the same error
//the size of the screen is fixed and also the hitbox of the images is really huge for some reason and actually impossible to go to the boss level
//because of the difficulty but i think that doesn't really matter anything'
//in conclusion there is 2 error first the 2 many bullet that will cause a limited error throw but the other one after 22 second it will throw another error 

//first the error is invocation error;

/*	11/2/2024 23:03
 * 
 * update: the error is already fixed (the images pathing name is wrong) and we can play the game, but the new problem is 
 * 
 *  1. java.lang.OutOfMemoryError: Java heap space when playing
 *  2. they don't show the win image 
 *  
 *  solution: dont know yet please find it future me; and don't forget to clone this repo and make it private so i can make regular git commits
 * 
 * */
