package com.example.demo.controller;

import com.example.demo.display.MainMenu;
import com.example.demo.level.LevelParent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;

public class GameOverMenuController {
    private LevelParent levelParent;

    public void initialize(LevelParent levelParent) {
        this.levelParent = levelParent;
    }

    @FXML
    private void restartButton() {
        levelParent.restartGame();
    }

    @FXML
    private void exitButton() throws IOException {

        levelParent.cleanup();

        Stage stage = levelParent.getStage();

        //this will generate new controller
        Controller controller = new Controller(stage);
        MainMenu.showMainMenu(stage, controller);
    }

}
