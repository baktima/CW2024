package com.example.demo.projectile;

/**
 * Represents a projectile launched by enemy units in the game.
 * This class defines the specific properties of enemy projectiles,
 * such as their size, image, and horizontal velocity.
 */
public class EnemyProjectile extends Projectile {
	
	private static final String IMAGE_NAME = "EnemyMissileSlug.png";
	private static final int IMAGE_HEIGHT = 30;
	private static final int HORIZONTAL_VELOCITY = -10;

	/**
	 * Constructs a new EnemyProjectile with a specified initial position.
	 *
	 * @param initialXPos The initial x-coordinate of the projectile.
	 * @param initialYPos The initial y-coordinate of the projectile.
	 */
	public EnemyProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	/**
	 * Retrieves the horizontal velocity of the enemy projectile.
	 *
	 * @return The horizontal velocity of the projectile.
	 */
	@Override
	public double getHorizontalVelocity() {
		return HORIZONTAL_VELOCITY;
	}
}
