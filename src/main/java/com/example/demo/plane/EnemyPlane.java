package com.example.demo.plane;

import com.example.demo.projectile.EnemyProjectile;
import com.example.demo.actor.ActiveActor;
import com.example.demo.sound.SoundEffects;

public class EnemyPlane extends FighterPlane {

	private static final String IMAGE_NAME = "EnemyPlane1.png";
	private static final int IMAGE_HEIGHT = 70;
	private static final int HORIZONTAL_VELOCITY = -6;
	private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
	private static final int INITIAL_HEALTH = 1;
	private static final double FIRE_RATE = .01;

	public EnemyPlane(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
	}

	//only added the sound effect for testing but considering it
	@Override
	public void takeDamage() {
		super.takeDamage();
		SoundEffects soundEffects = new SoundEffects();
		soundEffects.playSound();
	}

	@Override
	public void destroy() {
		super.destroy();
		SoundEffects soundEffects = new SoundEffects();
		soundEffects.playSound();
	}

	//for the EnemySpecialPlane
	public EnemyPlane(String imageName, double initialXPos, double initialYPos, int health) {
		super(imageName, IMAGE_HEIGHT, initialXPos, initialYPos, health);
	}

	public static double getProjectileXPositionOffset() {
		return PROJECTILE_X_POSITION_OFFSET;
	}

	public static double getProjectileYPositionOffset() {
		return PROJECTILE_Y_POSITION_OFFSET;
	}

	@Override
	public ActiveActor fireProjectile() {
			if (Math.random() < FIRE_RATE) {
				double projectileXPosition = getProjectileXPosition(getProjectileXPositionOffset());
				double projectileYPosition = getProjectileYPosition(getProjectileYPositionOffset());
				return new EnemyProjectile(projectileXPosition, projectileYPosition);
			}
		return null;
	}

	@Override
	public double getHorizontalVelocity() {
		return HORIZONTAL_VELOCITY;
	}

}
