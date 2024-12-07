package com.example.demo.plane;

import com.example.demo.actor.ActiveActor;

/**
 * Abstract class representing a fighter plane in the game.
 * Extends the {@link ActiveActor} class and provides health management and projectile firing capabilities.
 */
public abstract class FighterPlane extends ActiveActor {

	private int health;

	/**
	 * Constructs a new FighterPlane with the specified image, dimensions, initial position, and health.
	 *
	 * @param imageName     The name of the image file representing the plane.
	 * @param imageHeight   The height of the plane's image.
	 * @param initialXPos   The initial X position of the plane.
	 * @param initialYPos   The initial Y position of the plane.
	 * @param health        The initial health of the plane.
	 */
	protected FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	/**
	 * Abstract method to be implemented by subclasses for firing projectiles.
	 *
	 * @return An {@link ActiveActor} representing the fired projectile, or {@code null} if no projectile is fired.
	 */
	public abstract ActiveActor fireProjectile();

	/**
	 * Reduces the health of the plane by 1. If health reaches 0, the plane is destroyed.
	 */
	@Override
	public void takeDamage() {
		health--;
		if (healthAtZero()) {
			this.destroy();
		}
	}

	/**
	 * Calculates the X position of a projectile based on the plane's current position and a specified offset.
	 *
	 * @param xPositionOffset The offset to be added to the plane's X position.
	 * @return The calculated X position of the projectile.
	 */
	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	/**
	 * Calculates the Y position of a projectile based on the plane's current position and a specified offset.
	 *
	 * @param yPositionOffset The offset to be added to the plane's Y position.
	 * @return The calculated Y position of the projectile.
	 */
	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	/**
	 * Checks if the health of the plane has reached zero.
	 *
	 * @return {@code true} if the health is zero, {@code false} otherwise.
	 */
	private boolean healthAtZero() {
		return health == 0;
	}

	/**
	 * Gets the current health of the plane.
	 *
	 * @return The current health of the plane.
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Sets the health of the plane to a specified value.
	 *
	 * @param health The new health value to be set.
	 */
	public void setHealth(int health){
		this.health = health;
	}
		
}
