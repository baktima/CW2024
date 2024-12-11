package com.example.demo.controller;

import javafx.fxml.FXML;

import java.io.IOException;

/**
 * Controller for the "Game Over" menu in the game.
 * <p>
 * This class handles user interactions within the "Game Over" menu,
 * such as restarting the current level or exiting to the main menu.
 * It extends {@link BaseGameEndMenuController} to inherit common functionality.
 * </p>
 */
public class WinMenuController extends BaseGameEndMenuController {
    private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.level.LevelOne";


    /**
     * Default constructor is private to prevent instantiation.
     */
    private WinMenuController(){
        // not intended for instantiation
    }

    /**
     * Handles the "Restart from Beginning" button click event.
     * Restarts the game from the first level.
     */
    @FXML
    private void restartBeginningButton() {
        levelParent.goToNextLevel(LEVEL_ONE_CLASS_NAME);
    }

    /**
     * Exit to the main menu.
     * @throws IOException Throws an error
     */
    @FXML
    private void exitButton() throws IOException {
        exitToMainMenu();
    }

}
