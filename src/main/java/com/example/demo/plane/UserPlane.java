package com.example.demo.plane;

import com.example.demo.sound.SoundEffects;
import com.example.demo.projectile.UserProjectile;
import com.example.demo.actor.ActiveActor;

/**
 * Represents the user-controlled plane in the game.
 * Extends the {@link FighterPlane} class, providing movement and shooting capabilities.
 */
public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = "UserPlaneSlug2.png";
	private static final double Y_UPPER_BOUND = 0;
	private static final double Y_LOWER_BOUND = 650.0;
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = 100;
	private static final int VERTICAL_VELOCITY = 12;
	private static final double HORIZONTAL_VELOCITY = 0;
	private static final int PROJECTILE_X_POSITION = 110;
	private static final int PROJECTILE_Y_POSITION_OFFSET = 60;
	private double velocityMultiplier;
	private int numberOfKills;

	/**
	 * Constructs a UserPlane object with the specified initial health.
	 *
	 * @param initialHealth The initial health of the user plane.
	 */
	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		velocityMultiplier = 0;
	}

	//movement
	/**
	 * Updates the position of the user plane based on its velocity.
	 * Ensures the plane stays within vertical bounds.
	 */
	@Override
	public void updatePosition() {
		if (isMoving()) {
			double initialTranslateY = getTranslateY();
			this.moveVertically(VERTICAL_VELOCITY * velocityMultiplier);
			double newPosition = getLayoutY() + getTranslateY();
			if (newPosition < Y_UPPER_BOUND || newPosition > Y_LOWER_BOUND) {
				this.setTranslateY(initialTranslateY);
			}
		}
	}

	/**
	 * Checks whether the user plane is currently moving.
	 *
	 * @return {@code true} if the plane is moving, {@code false} otherwise.
	 */
	private boolean isMoving() {
		return velocityMultiplier != 0;
	}

	/**
	 * Initiates upward movement of the user plane.
	 */
	public void moveUp() {
		velocityMultiplier = -1;
	}

	/**
	 * Initiates downward movement of the user plane.
	 */
	public void moveDown() {
		velocityMultiplier = 1;
	}

	/**
	 * Stops the vertical movement of the user plane.
	 */
	public void stop() {
		velocityMultiplier = 0;
	}

	/**
	 * Fires a projectile from the user plane and plays the shooting sound effect.
	 *
	 * @return A new {@link UserProjectile} instance representing the projectile fired.
	 */
    @Override
	public ActiveActor fireProjectile() {
		userShootingSoundEffect();
		return new UserProjectile(PROJECTILE_X_POSITION, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
	}

	/**
	 * Plays the shooting sound effect for the user plane.
	 */
	private void userShootingSoundEffect(){
		SoundEffects soundEffects = new SoundEffects();
		soundEffects.playGunShotSound();
	}

	/**
	 * Increments the kill count of the user plane.
	 */
	public void incrementKillCount() {
		numberOfKills++;
	}

	//getter and setter
	/**
	 * Gets the total number of kills by the user plane.
	 *
	 * @return The number of kills.
	 */
	public int getNumberOfKills() {
		return numberOfKills;
	}

	/**
	 * Sets the kill count of the user plane.
	 *
	 * @param numberOfKills The number of kills to set.
	 */
	public void setNumberOfKills(int numberOfKills){
		this.numberOfKills = numberOfKills;
	}

	/**
	 * Gets the horizontal velocity of the user plane.
	 *
	 * @return The horizontal velocity, which is constant at {@code 0}.
	 */
	@Override
	public double getHorizontalVelocity() {
		return HORIZONTAL_VELOCITY;
	}
}
