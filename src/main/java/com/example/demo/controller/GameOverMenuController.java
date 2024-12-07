package com.example.demo.controller;

import javafx.fxml.FXML;

import java.io.IOException;

public class GameOverMenuController extends BaseGameEndMenuController {
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
