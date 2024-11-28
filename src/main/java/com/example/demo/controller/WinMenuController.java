package com.example.demo.controller;

import com.example.demo.display.MainMenu;
import com.example.demo.level.LevelParent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;

public class WinMenuController {
    private LevelParent levelParent;
    private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.level.LevelOne";

    public void initialize(LevelParent levelParent) {
        this.levelParent = levelParent;
    }

    @FXML
    private void restartBeginningButton() {
        levelParent.goToNextLevel(LEVEL_ONE_CLASS_NAME);
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
