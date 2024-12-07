package com.example.demo.display.menu;

import com.example.demo.controller.GameOverMenuController;
import com.example.demo.level.LevelParent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.Objects;

/**
 * Represents the Game Over menu in the game.
 * The menu includes a background image and provides functionality for displaying
 * an interactive menu using an FXML layout.
 */
public class GameOverMenu extends ImageView {
    private static final String IMAGE_NAME = "/com/example/demo/images/gameover.png";
    private static final String FXML_PATH = "/com/example/demo/fxml/GameOverMenu.fxml";
    private static final int HEIGHT = 500;
    private static final int WIDTH = 600;

    /**
     * Displays the Game Over menu.
     *
     * @param levelParent The current level parent to pass to the controller.
     * @return A {@link Parent} object representing the Game Over menu layout.
     * @throws IOException If there is an issue loading the FXML file.
     */
    public static Parent showGameOverMenu(LevelParent levelParent) throws IOException {
            Parent cachedGameOverMenu;
            FXMLLoader loader = new FXMLLoader(GameOverMenu.class.getResource(FXML_PATH));
            cachedGameOverMenu = loader.load();

            // Access the controller to pass necessary references
            GameOverMenuController gameOverMenuController = loader.getController();
            gameOverMenuController.initialize(levelParent);

            double centerX = (MainMenu.getScreenWidth() - cachedGameOverMenu.getLayoutBounds().getWidth()) / 4;
            double centerY = (MainMenu.getScreenHeight() - cachedGameOverMenu.getLayoutBounds().getHeight()) / 4;
            cachedGameOverMenu.setLayoutX(centerX);
            cachedGameOverMenu.setLayoutY(centerY);
            return cachedGameOverMenu;
    }

    /**
     * Constructs a {@code GameOverMenu} with the specified position and default image dimensions.
     *
     * @param xPosition The x-coordinate for the menu's position.
     * @param yPosition The y-coordinate for the menu's position.
     */
    public GameOverMenu(double xPosition, double yPosition) {

        //display the image
        setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()) );
        setLayoutX(xPosition);
        setLayoutY(yPosition);
        setFitHeight(HEIGHT);
        setFitWidth(WIDTH);
    }
}
