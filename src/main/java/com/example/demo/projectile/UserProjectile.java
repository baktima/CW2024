package com.example.demo.projectile;

public class UserProjectile extends Projectile {

	private static final String IMAGE_NAME = "BulletUser.png";
	private static final int IMAGE_HEIGHT = 15;
	private static final int HORIZONTAL_VELOCITY = 15;

	public UserProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	@Override
	public double getHorizontalVelocity() {
		return HORIZONTAL_VELOCITY;
	}
	
}
