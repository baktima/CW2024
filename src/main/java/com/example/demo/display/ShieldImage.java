package com.example.demo.display;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ShieldImage extends ImageView {
	
	private static final String IMAGE_NAME = "/com/example/demo/images/DarkShield.png";
	private static final int SHIELD_SIZE = 330;
	private static final double SHIELD_OPACITY = 0.3;
	
	public ShieldImage(double xPosition, double yPosition) {
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
		this.setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
		this.setVisible(false);
		this.setFitHeight(SHIELD_SIZE);
		this.setFitWidth(SHIELD_SIZE);
		this.setOpacity(SHIELD_OPACITY);
	}

	public void showShield() {
		this.setVisible(true);
		this.setOpacity(SHIELD_OPACITY);
	}
	
	public void hideShield() {
		this.setVisible(false);
	}

}
