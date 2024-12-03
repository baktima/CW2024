package com.example.demo.controller;

import com.example.demo.level.LevelParent;
import javafx.fxml.FXML;

import java.io.IOException;

public class WinMenuController extends BaseGameEndMenuController {
    private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.level.LevelOne";

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
