package com.example.demo.display;

import com.example.demo.controller.PauseMenuController;
import com.example.demo.level.LevelParent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class PauseMenu {
    private static Parent cachedPauseMenu = null;

    private PauseMenu() {
        throw new IllegalStateException("Utility class");
    }

    public static Parent showPauseMenu(LevelParent levelParent) throws IOException {
        if (cachedPauseMenu == null) {
            FXMLLoader loader = new FXMLLoader(PauseMenu.class.getResource("/com/example/demo/fxml/PauseMenu.fxml"));
            cachedPauseMenu = loader.load();

            // Access the controller to pass necessary references
            PauseMenuController pauseController = loader.getController();
            pauseController.initialize(levelParent);

            double centerX = (MainMenu.GetScreenwidth() - cachedPauseMenu.getLayoutBounds().getWidth()) / 4;
            double centerY = (MainMenu.GetScreenHeight() - cachedPauseMenu.getLayoutBounds().getHeight()) / 4;
            cachedPauseMenu.setLayoutX(centerX);
            cachedPauseMenu.setLayoutY(centerY);
        }
        return cachedPauseMenu;
    }
}
