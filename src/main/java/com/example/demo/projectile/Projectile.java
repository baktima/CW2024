package com.example.demo.projectile;

import com.example.demo.actor.ActiveActor;
import com.example.demo.actor.ActiveActorDestructible;

public abstract class Projectile extends ActiveActor {

	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
	}

	@Override
	public void takeDamage() {
		this.destroy();
	}

	@Override
	public abstract void updatePosition();

	//testing
//	@Override
//	public void UpdatePosition(){
//		moveHorizontally((getHorizontalVelocity()));
//	}

	protected abstract double getHorizontalVelocity();

}
