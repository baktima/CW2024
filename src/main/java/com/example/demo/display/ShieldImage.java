package com.example.demo.display;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The {@code ShieldImage} class represents a visual shield icon in the game.
 * This class provides functionality to display or hide the shield and manages its visual properties, such as size, position, and opacity.
 */
public class ShieldImage extends ImageView {
	
	private static final String IMAGE_NAME = "/com/example/demo/images/DarkShield.png";
	private static final int SHIELD_SIZE = 330;
	private static final double SHIELD_OPACITY = 0.3;

	/**
	 * Constructs a new {@code ShieldImage} at the specified position.
	 *
	 * @param xPosition the x-coordinate of the shield's position
	 * @param yPosition the y-coordinate of the shield's position
	 */
	public ShieldImage(double xPosition, double yPosition) {
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
		this.setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
		this.setVisible(false);
		this.setFitHeight(SHIELD_SIZE);
		this.setFitWidth(SHIELD_SIZE);
		this.setOpacity(SHIELD_OPACITY);
	}

	/**
	 * Makes the shield visible on the screen with the predefined opacity.
	 */
	public void showShield() {
		this.setVisible(true);
		this.setOpacity(SHIELD_OPACITY);
	}

	/**
	 * Hides the shield from the screen.
	 */
	public void hideShield() {
		this.setVisible(false);
	}

}
