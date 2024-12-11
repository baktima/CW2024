package com.example.demo.plane;

import com.example.demo.actor.ActiveActor;
import com.example.demo.projectile.BomberProjectile;

/**
 * Represents a BomberPlane in the game.
 * <p>
 * The BomberPlane is a specialized type of enemy plane with moderate health, horizontal movement,
 * and the ability to fire projectiles at regular intervals. It presents a greater challenge to the player
 * due to its firing mechanism and movement properties.
 * </p>
 */
public class BomberPlane extends FighterPlane {

    private static final String IMAGE_NAME = "EnemyAtomCarrier.png";
    private static final int HEALTH = 2;
    private static final int IMAGE_HEIGHT = 70;
    private static final int HORIZONTAL_MOVEMENT = -5;
    private static final double FIRE_RATE = 0.0125;
    private static final int PROJECTILE_OFFSET_X = 25;
    private static final int PROJECTILE_OFFSET_Y = 35;

    /**
     * Constructs a BomberPlane with specified initial positions.
     *
     * @param initialXPos The initial X position of the BomberPlane.
     * @param initialYPos The initial Y position of the BomberPlane.
     */
    public BomberPlane(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT,initialXPos, initialYPos, HEALTH);
    }

    /**
     * Fires a projectile from the BomberPlane.
     * <p>
     * The firing mechanism is probabilistic, determined by the {@code FIRE_RATE}. If the
     * BomberPlane fires a projectile in the current frame, it returns a new projectile
     * instance with calculated positions.
     * </p>
     *
     * @return A {@link BomberProjectile} representing the projectile if fired, or {@code null} if no projectile is fired.
     */
    @Override
    public ActiveActor fireProjectile() {
        if (Math.random() < FIRE_RATE) {
            double projectileXPosition = getProjectileXPosition(PROJECTILE_OFFSET_X);
            double projectileYPosition = getProjectileYPosition(PROJECTILE_OFFSET_Y);
            return new BomberProjectile(projectileXPosition, projectileYPosition);
        }
        return null;
    }

    /**
     * Retrieves the horizontal velocity of the BomberPlane.
     * <p>
     * The BomberPlane moves at a constant horizontal velocity.
     * </p>
     *
     * @return The horizontal velocity of the BomberPlane.
     */
    @Override
    public double getHorizontalVelocity(){
        return HORIZONTAL_MOVEMENT;
    }

}
