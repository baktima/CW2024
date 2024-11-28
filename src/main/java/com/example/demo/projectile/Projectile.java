package com.example.demo.projectile;

import com.example.demo.actor.ActiveActor;

public abstract class Projectile extends ActiveActor {

	protected Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
	}

	@Override
	public void takeDamage() {
		this.destroy();
	}

}
