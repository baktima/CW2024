package com.example.demo;

import com.example.demo.controller.Controller;
import com.example.demo.controller.MainMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainMenu {
    private static final int SCREEN_WIDTH = 1300;
    private static final int SCREEN_HEIGHT = 750;
    private static final String TITLE = "Sky Battle";

    public static void showMainMenu(Stage stage, Controller gameController) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainMenu.class.getResource("/com/example/demo/fxml/MainMenu.fxml"));
        Parent root = loader.load();

        MainMenuController mainMenuController = loader.getController();
        mainMenuController.initialize(stage, gameController);

        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setTitle(TITLE);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setWidth(SCREEN_WIDTH);
        stage.setHeight(SCREEN_HEIGHT);
        stage.show();
    }
}
