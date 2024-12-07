package com.example.demo.plane;

import com.example.demo.actor.ActiveActor;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Represents a TankerPlane, a type of {@link FighterPlane} with high health and a zigzag movement pattern.
 * TankerPlane does not fire projectiles but moves horizontally with a zigzag vertical motion.
 */
public class TankerPlane extends FighterPlane {
    private static final int HORIZONTAL_VELOCITY = -3;
    private static final String IMAGE_NAME = "TankerPlaneSlug.png";
    private static final int IMAGE_HEIGHT = 180;
    private static final int INITIAL_HEALTH = 8;
    private static final int DURATION = 5;
    private int verticalVelocity = -1; // Initial direction is upward

    /**
     * Constructs a new TankerPlane at the specified position.
     *
     * @param initialXPos The initial X position of the TankerPlane.
     * @param initialYPos The initial Y position of the TankerPlane.
     */
    public TankerPlane(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);

        // Initialize a Timeline to toggle vertical velocity every second
        Timeline zigZagTimer;
        zigZagTimer = new Timeline(
                new KeyFrame(Duration.seconds(DURATION), event -> toggleVerticalVelocity())
        );
        zigZagTimer.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely
        zigZagTimer.play();
    }

    /**
     * Toggles the vertical velocity between positive and negative to create a zigzag movement pattern.
     */
    private void toggleVerticalVelocity() {
        verticalVelocity *= -1;
    }

    /**
     * TankerPlane does not fire projectiles. This method always returns {@code null}.
     *
     * @return {@code null} since TankerPlane does not fire projectiles.
     */
    @Override
    public ActiveActor fireProjectile() {
        return null; // TankerPlane doesn't fire
    }

    /**
     * Updates the position of the TankerPlane by moving it vertically in a zigzag pattern.
     */
    @Override
    public void updatePosition() {
        super.updatePosition();
        moveVertically(verticalVelocity);
    }

    /**
     * Gets the horizontal velocity of the TankerPlane.
     *
     * @return The horizontal velocity as a negative constant, indicating leftward movement.
     */
    @Override
    public double getHorizontalVelocity() {
        return HORIZONTAL_VELOCITY;
    }


}
