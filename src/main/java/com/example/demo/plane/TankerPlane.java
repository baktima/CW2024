package com.example.demo.plane;

import com.example.demo.actor.ActiveActor;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class TankerPlane extends FighterPlane {
    private static final int HORIZONTAL_VELOCITY = -3;
    private static final String IMAGE_NAME = "TankerPlaneSlug.png";
    private static final int IMAGE_HEIGHT = 180;
    private static final int INITIAL_HEALTH = 8;

    private int verticalVelocity = -1; // Initial direction is upward

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
        verticalVelocity *= -1;
    }

    @Override
    public ActiveActor fireProjectile() {
        return null; // TankerPlane doesn't fire
    }

    @Override
    public void updatePosition() {
        super.updatePosition();
        moveVertically(verticalVelocity);
    }

    @Override
    public double getHorizontalVelocity() {
        return HORIZONTAL_VELOCITY;
    }


}
