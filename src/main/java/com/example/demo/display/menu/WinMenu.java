package com.example.demo.display.menu;

import com.example.demo.controller.WinMenuController;
import com.example.demo.level.LevelParent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.Objects;

/**
 * Class representing the Win Menu in the game.
 * This class displays a win image and provides functionality to load and manage the Win Menu FXML.
 */
public class WinMenu extends ImageView {

    private static Parent cachedWinMenu = null;
    private static final String IMAGE_NAME = "/com/example/demo/images/youwin.png";
    private static final String FXML_PATH = "/com/example/demo/fxml/WinMenu.fxml";
    private static final int HEIGHT = 500;
    private static final int WIDTH = 600;

    /**
     * Loads and displays the Win Menu FXML.
     * If the menu is already cached, it reuses the cached instance.
     *
     * @param levelParent The current {@link LevelParent} instance to associate with the Win Menu.
     * @return A {@link Parent} object representing the Win Menu.
     * @throws IOException If there is an issue loading the FXML file.
     */
    public static Parent showWinMenu(LevelParent levelParent) throws IOException {
        if (cachedWinMenu == null) {
            FXMLLoader loader = new FXMLLoader(WinMenu.class.getResource(FXML_PATH));
            cachedWinMenu = loader.load();

            // Access the controller to pass necessary references
            WinMenuController winMenuController = loader.getController();
            winMenuController.initialize(levelParent);

            double centerX = (MainMenu.getScreenWidth() - cachedWinMenu.getLayoutBounds().getWidth()) / 4;
            double centerY = (MainMenu.getScreenHeight() - cachedWinMenu.getLayoutBounds().getHeight()) / 4;
            cachedWinMenu.setLayoutX(centerX);
            cachedWinMenu.setLayoutY(centerY);
        }
        return cachedWinMenu;
    }

    /**
     * Constructs a WinMenu instance with the specified position and default dimensions.
     *
     * @param xPosition The x-coordinate of the Win Menu.
     * @param yPosition The y-coordinate of the Win Menu.
     */
    public WinMenu(double xPosition, double yPosition) {

        //display the image
        this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()));
        this.setVisible(false);
        this.setFitHeight(HEIGHT);
        this.setFitWidth(WIDTH);
        this.setLayoutX(xPosition);
        this.setLayoutY(yPosition);
    }

    /**
     * Displays the win image.
     */
    public void showWinImage() {
        this.setVisible(true);
    }

    /**
     * Hides the win image.
     */
    public void hideWinImage(){
        this.setVisible(false);
    }


}
