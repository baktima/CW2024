package com.example.demo.display.menu;

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

    /**
     * Utility class for managing the Main Menu of the game.
     * Provides methods to display the Main Menu and retrieve screen properties.
     */
    private MainMenu(){
        throw new IllegalStateException("Utility class");
    }

    /**
     * Displays the Main Menu.
     * Loads the Main Menu FXML file, initializes the {@link MainMenuController},
     * and sets the menu on the specified {@link Stage}.
     *
     * @param stage         The primary stage where the Main Menu will be displayed.
     * @param gameController The game controller to pass to the {@link MainMenuController}.
     * @throws IOException If there is an issue loading the FXML file.
     */
    public static void showMainMenu(Stage stage, Controller gameController)throws IOException {
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

    /**
     * Retrieves the screen width for the Main Menu.
     *
     * @return The screen width in pixels.
     */

    public static int getScreenWidth(){
        return SCREEN_WIDTH;
    }

    /**
     * Retrieves the screen height for the Main Menu.
     *
     * @return The screen height in pixels.
     */
    public static int getScreenHeight(){
        return SCREEN_HEIGHT;
    }

    /**
     * Retrieves the title for the Main Menu.
     *
     * @return The title of the Main Menu.
     */
    public static String getTitle(){
        return TITLE;
    }
}
