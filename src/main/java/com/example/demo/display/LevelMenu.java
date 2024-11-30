package com.example.demo.display;

import com.example.demo.controller.Controller;
import com.example.demo.controller.LevelMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LevelMenu {
    public static void showLevelMenu(Stage stage, Controller gameController)throws IOException {
        FXMLLoader loader = new FXMLLoader(MainMenu.class.getResource("/com/example/demo/fxml/LevelMenu.fxml"));
        Parent root = loader.load();

        LevelMenuController levelMenuController = loader.getController();
        levelMenuController.initialize(stage, gameController);

        Scene scene = new Scene(root, MainMenu.getScreenWidth(), MainMenu.getScreenHeight());
        stage.setTitle(MainMenu.getTitle());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setWidth(MainMenu.getScreenWidth());
        stage.setHeight(MainMenu.getScreenHeight());
        stage.show();
    }
}
