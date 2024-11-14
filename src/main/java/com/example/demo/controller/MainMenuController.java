package com.example.demo.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class MainMenuController {
	private Stage stage; 
	private Controller gameController; 
	
	/**
     * Initializes the main menu with the given stage and game controller.
     *
     * @param stage         The primary stage of the application.
     * @param gameController The main game controller to manage the game transitions.
     */
	public void initialize(Stage stage, Controller gameController) {
		this.stage = stage; 
		this.gameController = gameController; 
		
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
	private void buttonSettings() {
		
	}
	
	@FXML
	private void buttonExit() { 
		stage.close(); 
	}
	
}
