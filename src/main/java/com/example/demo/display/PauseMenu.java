package com.example.demo.display;

import com.example.demo.controller.PauseMenuController;
import com.example.demo.level.LevelParent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class PauseMenu {

    private PauseMenu() {
        throw new IllegalStateException("Utility class");
    }

    public static Parent showPauseMenu(LevelParent levelParent) throws IOException {
        FXMLLoader loader = new FXMLLoader(PauseMenu.class.getResource("/com/example/demo/fxml/PauseMenu.fxml"));
        Parent root = loader.load();

        PauseMenuController controller = loader.getController();
        controller.initialize(levelParent); // Pass the current LevelParent to the controller

        root.setUserData(controller); // Store the controller in the Parent for later use
        return root;
    }
}
