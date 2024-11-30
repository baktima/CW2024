package com.example.demo.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class LevelMenuController {

    private Stage stage;
    private Controller gameController;

    public void initialize(Stage stage, Controller gameController) {
        this.stage = stage;
        this.gameController = gameController;
    }

    @FXML
    private void levelOne(){
        try {
            gameController.launchGame();
            System.out.println("this is the LevelMenuController stage" + stage);
        } catch (Exception e) {
            e.printStackTrace();  // Handle or log exception as appropriate for your application
        }
    }

    @FXML
    private void levelTwo(){
        try {
            gameController.goToLevel("com.example.demo.level.LevelTwo");
        } catch (Exception e) {
            e.printStackTrace();  // Handle or log exception as appropriate for your application
        }

    }

    @FXML
    private void levelThree(){
        try {
            gameController.goToLevel("com.example.demo.level.LevelThree");
        } catch (Exception e) {
            e.printStackTrace();  // Handle or log exception as appropriate for your application
        }
    }

    @FXML
    private void levelEndless(){
        try {
            gameController.goToLevel("com.example.demo.level.LevelEndless");
        } catch (Exception e) {
            e.printStackTrace();  // Handle or log exception as appropriate for your application
        }

    }

}
