package com.example.demo.plane;

import com.example.demo.actor.ActiveActor;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class TankerPlane extends FighterPlane {
    private static final int HORIZONTAL_VELOCITY = -10;
    private int VERTICAL_VELOCITY = -1; // Initial direction is upward
    private static final String IMAGE_NAME = "TankerPlane.png";
    private static final int IMAGE_HEIGHT = 90;
    private static final int INITIAL_HEALTH = 8;

    private final Timeline zigZagTimer;

    public TankerPlane(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);

        // Initialize a Timeline to toggle vertical velocity every second
        zigZagTimer = new Timeline(
                new KeyFrame(Duration.seconds(10), event -> toggleVerticalVelocity())
        );
        zigZagTimer.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely
        zigZagTimer.play(); // Start the zig-zag motion
    }

    /**
     * Toggles the vertical velocity between positive and negative.
     */
    private void toggleVerticalVelocity() {
        VERTICAL_VELOCITY *= -1;
    }

    @Override
    public ActiveActor fireProjectile() {
        return null; // TankerPlane doesn't fire
    }

    @Override
    public void updatePosition() {
        super.updatePosition();
        moveVertically(VERTICAL_VELOCITY);
    }

    @Override
    public double GetHorizontalVelocity() {
        return HORIZONTAL_VELOCITY;
    }

    /**
     * Stops the zig-zag motion when the plane is destroyed or removed.
     */
//    public void stopZigZagMotion() {
//        zigZagTimer.stop();
//    }

}
