package com.example.demo.display.menu;

import com.example.demo.controller.Controller;
import com.example.demo.controller.LevelMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Represents the Level Menu in the game.
 * Provides functionality to load and display the Level Menu using an FXML layout.
 */
public class LevelMenu {

    /**
     * Loads and displays the Level Menu.
     * Sets up the associated {@link LevelMenuController} and initializes the game controller.
     *
     * @param stage The primary stage where the Level Menu will be displayed.
     * @param gameController The game controller to be passed to the {@link LevelMenuController}.
     * @throws IOException If there is an issue loading the FXML file for the Level Menu.
     */
    public static void showLevelMenu(Stage stage, Controller gameController)throws IOException {
        FXMLLoader loader = new FXMLLoader(MainMenu.class.getResource("/com/example/demo/fxml/LevelMenu.fxml"));
        Parent root = loader.load();

        LevelMenuController levelMenuController = loader.getController();
        levelMenuController.initialize(gameController);

        Scene scene = new Scene(root, MainMenu.getScreenWidth(), MainMenu.getScreenHeight());
        stage.setTitle(MainMenu.getTitle());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setWidth(MainMenu.getScreenWidth());
        stage.setHeight(MainMenu.getScreenHeight());
        stage.show();
    }
}
