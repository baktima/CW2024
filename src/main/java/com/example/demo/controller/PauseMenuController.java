package com.example.demo.controller;

import com.example.demo.LevelParent;
import com.example.demo.MainMenu;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import java.io.IOException;

public class PauseMenuController {

    private LevelParent levelParent;

    public void initialize(LevelParent levelParent) {

        this.levelParent = levelParent;
    }

    @FXML
    private void resumeButton() {
        levelParent.resumeGame();
    }

    //still kinda funky need to fix this instantly
    @FXML
    private void restartButton() {
    	levelParent.restartGame();
    }

    //it works now
    @FXML
    private void exitButton() throws IOException {

        //the cleanup is only semi
        levelParent.cleanup();

        Stage stage = levelParent.getStage();
        Controller controller = new Controller(stage);
        MainMenu.showMainMenu(stage, controller);
    }
}
