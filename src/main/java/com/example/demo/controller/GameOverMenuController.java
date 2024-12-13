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
public class GameOverMenuController extends BaseGameEndMenuController {

    /**
     * @hidden
     * Default constructor
     */
    public GameOverMenuController(){
        // No functionality needed
    }

    /**
     * Handles the "Restart" button click event.
     * Restarts the current game level.
     */
    @FXML
    private void restartButton() {
        levelParent.restartGame();
    }

    /**
     * Exit to main menu.
     * @throws IOException Throws an error
     */
    @FXML
    private void exitButton() throws IOException {
        exitToMainMenu();
    }

}
