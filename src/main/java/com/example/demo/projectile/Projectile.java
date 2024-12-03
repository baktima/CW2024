package com.example.demo.projectile;

import com.example.demo.actor.ActiveActor;

/**
 * Represents an abstract base class for all projectiles in the game.
 * Projectiles are specialized {@link ActiveActor} objects with predefined behaviors,
 * such as being destroyed upon taking damage.
 *
 * Subclasses must define specific properties like velocity or unique behaviors.
 */
public abstract class Projectile extends ActiveActor {

	/**
	 * Constructs a new Projectile object with the specified parameters.
	 *
	 * @param imageName     The name of the image file representing the projectile.
	 * @param imageHeight   The height of the projectile's image.
	 * @param initialXPos   The initial x-coordinate of the projectile.
	 * @param initialYPos   The initial y-coordinate of the projectile.
	 */
	protected Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
	}

	/**
	 * Handles the behavior when the projectile takes damage.
	 * The projectile is destroyed upon taking any damage.
	 */
	@Override
	public void takeDamage() {
		this.destroy();
	}

}
