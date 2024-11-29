package com.example.demo.display;

import com.example.demo.controller.GameOverMenuController;
import com.example.demo.level.LevelParent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class GameOverMenu extends ImageView {
    private static Parent cachedGameOverMenu = null;
    private static final String IMAGE_NAME = "/com/example/demo/images/gameover.png";
    private static final String FXML_PATH = "/com/example/demo/fxml/GameOverMenu.fxml";
    private static final int HEIGHT = 500;
    private static final int WIDTH = 600;

    public static Parent showGameOverMenu(LevelParent levelParent) throws IOException {
        if (cachedGameOverMenu == null) {
            FXMLLoader loader = new FXMLLoader(GameOverMenu.class.getResource(FXML_PATH));
            cachedGameOverMenu = loader.load();

            // Access the controller to pass necessary references
            GameOverMenuController gameOverMenuController = loader.getController();
            gameOverMenuController.initialize(levelParent);

            double centerX = (MainMenu.getScreenWidth() - cachedGameOverMenu.getLayoutBounds().getWidth()) / 4;
            double centerY = (MainMenu.getScreenHeight() - cachedGameOverMenu.getLayoutBounds().getHeight()) / 4;
            cachedGameOverMenu.setLayoutX(centerX);
            cachedGameOverMenu.setLayoutY(centerY);
        }
        return cachedGameOverMenu;
    }

    public GameOverMenu(double xPosition, double yPosition) {

        //display the image
        setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()) );
        setLayoutX(xPosition);
        setLayoutY(yPosition);
        setFitHeight(HEIGHT);
        setFitWidth(WIDTH);
    }

}
