package com.example.demo.actor;

import com.example.demo.implementation.Destructible;
import javafx.scene.image.*;

import java.util.Objects;

/**
 * Represents an active actor in the game, which is a graphical element with a position, movement,
 * and the ability to be destroyed and respawned.
 * <p>
 * This class extends {@link ImageView} and implements {@link Destructible}, providing base functionality
 * for actors that can move and interact within the game world.
 * </p>
 */
public abstract class ActiveActor extends ImageView implements Destructible {

	//base path for the image name
	private static final String IMAGE_LOCATION = "/com/example/demo/images/";
	private boolean isDestroyed;

	/**
	 * Constructs an {@code ActiveActor} with the specified image, size, and initial position.
	 *
	 * @param imageName    the name of the image file for the actor.
	 * @param imageHeight  the height of the image to be displayed.
	 * @param initialXPos  the initial X-coordinate of the actor.
	 * @param initialYPos  the initial Y-coordinate of the actor.
	 */
	protected ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {

		//set the base path name for the images of the actors
		this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_LOCATION + imageName)).toExternalForm()));
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);

		isDestroyed = false;
	}

	/**
	 * Moves the actor horizontally by the specified amount.
	 *
	 * @param horizontalMove the amount to move the actor horizontally.
	 */
	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	/**
	 * Moves the actor vertically by the specified amount.
	 *
	 * @param verticalMove the amount to move the actor vertically.
	 */
	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}

	/**
	 * Updates the actor's state by calling {@link #updatePosition()}.
	 * This method can be overridden for more complex behaviors.
	 */
	public void updateActor(){
		updatePosition();
	}

	/**
	 * Marks the actor as destroyed by setting the {@code isDestroyed} flag to true.
	 */
	public void destroy() {
		this.isDestroyed = true;
	}

	/**
	 * Respawns the actor by resetting the {@code isDestroyed} flag to false.
	 */
	public void respawn(){
		this.isDestroyed = false;
	}

	/**
	 * Checks whether the actor is destroyed.
	 *
	 * @return {@code true} if the actor is destroyed, {@code false} otherwise.
	 */
	public boolean getIsDestroyed() {
		return isDestroyed;
	}

	/**
	 * Updates the actor's position based on its velocity.
	 * This method moves the actor horizontally according to {@link #getHorizontalVelocity()}.
	 */
	public void updatePosition(){
		moveHorizontally(getHorizontalVelocity());
	}

	/**
	 * Returns the horizontal velocity of the actor.
	 * Subclasses must provide an implementation for this method to define how fast the actor moves horizontally.
	 *
	 * @return the horizontal velocity of the actor.
	 */
	public abstract double getHorizontalVelocity();
}
