package com.example.demo.display;

//need to finish this asap today
//need to refactor also the levelParent

import com.example.demo.controller.GameOverMenuController;
import com.example.demo.controller.PauseMenuController;
import com.example.demo.level.LevelParent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

//make counter and endless
public class GameOverMenu {
    private static Parent cachedGameOverMenu = null;
    public static Parent showGameOverMenu(LevelParent levelParent) throws IOException {
        if (cachedGameOverMenu == null) {
            FXMLLoader loader = new FXMLLoader(PauseMenu.class.getResource("/com/example/demo/fxml/GameOverMenu.fxml"));
            cachedGameOverMenu = loader.load();

            // Access the controller to pass necessary references
            GameOverMenuController gameOverMenuController = loader.getController();
            gameOverMenuController.initialize(levelParent);

            double centerX = (MainMenu.GetScreenwidth() - cachedGameOverMenu.getLayoutBounds().getWidth()) / 4;
            double centerY = (MainMenu.GetScreenHeight() - cachedGameOverMenu.getLayoutBounds().getHeight()) / 4;
            cachedGameOverMenu.setLayoutX(centerX);
            cachedGameOverMenu.setLayoutY(centerY);
        }
        return cachedGameOverMenu;
    }


}
