package com.example.demo.controller;

import com.example.demo.LevelParent;
import javafx.fxml.FXML;

public class PauseMenuController {

    private LevelParent levelParent;

    public void initialize(LevelParent levelParent) {
        this.levelParent = levelParent;
    }

    @FXML
    private void resumeButton() {
        levelParent.resumeGame();
    }
    
    @FXML
    private void restartButton() {
    	//
    }

    @FXML
    private void exitButton() {
        // Optionally, go back to the main menu by notifying observers
        //levelParent.goToNextLevel("MainMenu");
    }
}
