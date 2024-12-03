package com.example.demo.display;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Class representing a display of hearts, used to visually indicate health or lives in the game.
 * The display consists of a horizontal box (HBox) containing heart icons.
 */
public class HeartDisplay {
	
	private static final String HEART_IMAGE_NAME = "/com/example/demo/images/heart.png";
	private static final int HEART_HEIGHT = 50;
	private static final int INDEX_OF_FIRST_ITEM = 0;
	private HBox container;
	private final double containerXPosition;
	private final double containerYPosition;
	private int numberOfHeartsToDisplay;

	/**
	 * Constructs a HeartDisplay with a specified position and number of hearts to display.
	 *
	 * @param xPosition       The x-coordinate for the heart display.
	 * @param yPosition       The y-coordinate for the heart display.
	 * @param heartsToDisplay The initial number of hearts to display.
	 */
	public HeartDisplay(double xPosition, double yPosition, int heartsToDisplay) {
		containerXPosition = xPosition;
		containerYPosition = yPosition;
		numberOfHeartsToDisplay = heartsToDisplay;
		initializeContainer();
		initializeHearts();
	}

	/**
	 * Initializes the HBox container for the heart display.
	 * Sets the container's position based on the provided x and y coordinates.
	 */
	private void initializeContainer() {
		container = new HBox();
		container.setLayoutX(containerXPosition);
		container.setLayoutY(containerYPosition);		
	}

	/**
	 * Initializes the hearts in the display.
	 * Creates and adds the specified number of heart images to the container.
	 */
	public void initializeHearts() {
		for (int i = 0; i < numberOfHeartsToDisplay; i++) {
			ImageView heart = new ImageView(new Image(getClass().getResource(HEART_IMAGE_NAME).toExternalForm()));

			heart.setFitHeight(HEART_HEIGHT);
			heart.setPreserveRatio(true);
			container.getChildren().add(heart);
		}
	}

	/**
	 * Removes one heart from the display, starting from the first heart in the container.
	 * Does nothing if the container is already empty.
	 */
	public void removeHeart() {
		if (!container.getChildren().isEmpty())
			container.getChildren().remove(INDEX_OF_FIRST_ITEM);
	}

	/**
	 * Gets the container for the heart display.
	 *
	 * @return The HBox container holding the heart icons.
	 */
	public HBox getContainer() {
		return container;
	}

	/**
	 * Sets the number of hearts to display.
	 * This value determines how many hearts will be initialized when `initializeHearts` is called.
	 *
	 * @param numberOfHeartsToDisplay The number of hearts to display.
	 */
	public void setNumberOfHeartsToDisplay(int numberOfHeartsToDisplay) {
		this.numberOfHeartsToDisplay = numberOfHeartsToDisplay;
	}
}
