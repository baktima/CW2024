package com.example.demo.display.menu;

import com.example.demo.controller.PauseMenuController;
import com.example.demo.level.LevelParent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Utility class for managing the Pause Menu in the game.
 * Provides methods to load and display the Pause Menu.
 */
public class PauseMenu {

    /**
     * Private constructor to prevent instantiation of the utility class.
     *
     * @throws IllegalStateException Always thrown to enforce utility class behavior.
     */
    private PauseMenu() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Displays the Pause Menu.
     * Loads the Pause Menu FXML file, initializes the {@link PauseMenuController},
     * and attaches it to the specified {@link LevelParent}.
     *
     * @param levelParent The current {@link LevelParent} instance to associate with the Pause Menu.
     * @return A {@link Parent} object representing the Pause Menu.
     * @throws IOException If there is an issue loading the FXML file.
     */
    public static Parent showPauseMenu(LevelParent levelParent) throws IOException {
        FXMLLoader loader = new FXMLLoader(PauseMenu.class.getResource("/com/example/demo/fxml/PauseMenu.fxml"));
        Parent root = loader.load();

        PauseMenuController controller = loader.getController();
        controller.initialize(levelParent); // Pass the current LevelParent to the controller

        root.setUserData(controller); // Store the controller in the Parent for later use
        return root;
    }
}
