package com.example.demo.display;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameOverImage extends ImageView {
	
	private static final String IMAGE_NAME = "/com/example/demo/images/gameover.png";
	private static final int HEIGHT = 500;
	private static final int WIDTH = 600;

	public GameOverImage(double xPosition, double yPosition) {
		setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()) );
		setLayoutX(xPosition);
		setLayoutY(yPosition);
		setFitHeight(HEIGHT);
		setFitWidth(WIDTH);
	}
	//delete
}
