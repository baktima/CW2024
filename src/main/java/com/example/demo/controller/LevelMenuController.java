package com.example.demo.controller;

import javafx.fxml.FXML;

/**
 * Controller class for managing the level selection menu.
 * Provides functionality to launch and navigate between specific game levels.
 */
public class LevelMenuController {

    private Controller gameController;

    /**
     * Initializes the level menu controller with the main game controller.
     *
     * @param gameController The main game controller instance.
     */
    public void initialize(Controller gameController) {
        this.gameController = gameController;
    }

    /**
     * Handles the button click event to launch Level One.
     * Starts the game from the first level.
     */
    @FXML
    private void levelOne(){
        try {
            gameController.launchGame();
        } catch (Exception e) {
            e.printStackTrace();  // Handle or log exception as appropriate for your application
        }
    }

    /**
     * Handles the button click event to navigate to Level Two.
     * Loads and transitions to the second level.
     */
    @FXML
    private void levelTwo(){
        try {
            gameController.goToLevel("com.example.demo.level.LevelTwo");
        } catch (Exception e) {
            e.printStackTrace();  // Handle or log exception as appropriate for your application
        }

    }

    /**
     * Handles the button click event to navigate to Level Three.
     * Loads and transitions to the third level.
     */
    @FXML
    private void levelThree(){
        try {
            gameController.goToLevel("com.example.demo.level.LevelThree");
        } catch (Exception e) {
            e.printStackTrace();  // Handle or log exception as appropriate for your application
        }
    }

    /**
     * Handles the button click event to navigate to the Endless Level.
     * Loads and transitions to the endless level mode.
     */
    @FXML
    private void levelEndless(){
        try {
            gameController.goToLevel("com.example.demo.level.LevelEndless");
        } catch (Exception e) {
            e.printStackTrace();  // Handle or log exception as appropriate for your application
        }

    }

}
