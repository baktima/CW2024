package com.example.demo.actor;

import javafx.scene.image.*;

import java.util.Objects;

//this is the source where a lot of class extends from here for the player and the enemy projectile

public abstract class ActiveActor extends ImageView implements Destructible {

	private static final String IMAGE_LOCATION = "/com/example/demo/images/";
	private boolean isDestroyed;

	protected ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_LOCATION + imageName)).toExternalForm()));
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);

		isDestroyed = false;
	}

	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}

	public void updateActor(){
		updatePosition();
	}

	//can be changed to this.getIsDestroyed = true since there's no other application of the setter
	public void destroy() {
		setDestroyed(true);
	}

	public void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	public boolean getIsDestroyed() {
		return isDestroyed;
	}

	public void updatePosition(){
		moveHorizontally(GetHorizontalVelocity());
	}
	public abstract double GetHorizontalVelocity();
}
