package com.example.demo.projectile;

/**
 * Represents a projectile launched by the user in the game.
 * This class defines the specific properties of user projectiles,
 * including their image, size, and horizontal velocity.
 */
public class UserProjectile extends Projectile {

	private static final String IMAGE_NAME = "BulletUser.png";
	private static final int IMAGE_HEIGHT = 15;
	private static final int HORIZONTAL_VELOCITY = 15;

	/**
	 * Constructs a new UserProjectile with a specified initial position.
	 *
	 * @param initialXPos The initial x-coordinate of the projectile.
	 * @param initialYPos The initial y-coordinate of the projectile.
	 */
	public UserProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	/**
	 * Retrieves the horizontal velocity of the user projectile.
	 *
	 * @return The horizontal velocity of the projectile.
	 */
	@Override
	public double getHorizontalVelocity() {
		return HORIZONTAL_VELOCITY;
	}
	
}
