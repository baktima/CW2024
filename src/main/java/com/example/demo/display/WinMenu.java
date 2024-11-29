package com.example.demo.display;

import com.example.demo.controller.GameOverMenuController;
import com.example.demo.controller.WinMenuController;
import com.example.demo.level.LevelParent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class WinMenu extends ImageView {

    private static Parent cachedWinMenu = null;
    private static final String IMAGE_NAME = "/com/example/demo/images/youwin.png";
    private static final String FXML_PATH = "/com/example/demo/fxml/WinMenu.fxml";
    private static final int HEIGHT = 500;
    private static final int WIDTH = 600;

    public static Parent showWinMenu(LevelParent levelParent) throws IOException {
        if (cachedWinMenu == null) {
            FXMLLoader loader = new FXMLLoader(WinMenu.class.getResource(FXML_PATH));
            cachedWinMenu = loader.load();

            // Access the controller to pass necessary references
            WinMenuController winMenuController = loader.getController();
            winMenuController.initialize(levelParent);

            double centerX = (MainMenu.GetScreenwidth() - cachedWinMenu.getLayoutBounds().getWidth()) / 4;
            double centerY = (MainMenu.GetScreenHeight() - cachedWinMenu.getLayoutBounds().getHeight()) / 4;
            cachedWinMenu.setLayoutX(centerX);
            cachedWinMenu.setLayoutY(centerY);
        }
        return cachedWinMenu;
    }

    public WinMenu(double xPosition, double yPosition) {
        this.setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
        this.setVisible(false);
        this.setFitHeight(HEIGHT);
        this.setFitWidth(WIDTH);
        this.setLayoutX(xPosition);
        this.setLayoutY(yPosition);
    }

    public void showWinImage() {
        this.setVisible(true);
    }

    public void hideWinImage(){
        this.setVisible(false);
    }


}
