package com.example.demo.projectile;

/**
 * Represents a projectile launched by a boss enemy in the game.
 * This projectile has specific properties such as size, image, velocity,
 * and initial position.
 */
public class BossProjectile extends Projectile {
	private static final String IMAGE_NAME = "RocketBoss.png";
	private static final int IMAGE_HEIGHT = 75;
	private static final int HORIZONTAL_VELOCITY = -15;
	private static final int INITIAL_X_POSITION = 950;

	/**
	 * Constructs a new BossProjectile with a specified initial y-coordinate.
	 *
	 * @param initialYPos The initial y-coordinate of the projectile.
	 */
	public BossProjectile(double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos);
	}

	/**
	 * Retrieves the horizontal velocity of the projectile.
	 *
	 * @return The horizontal velocity of the projectile.
	 */
	@Override
	public double getHorizontalVelocity() {
		return HORIZONTAL_VELOCITY;
	}
	
}
