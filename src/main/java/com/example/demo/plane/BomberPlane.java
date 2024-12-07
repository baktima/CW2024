package com.example.demo.plane;

import com.example.demo.actor.ActiveActor;
import com.example.demo.projectile.BomberProjectile;

/**
 * Represents a BomberProjectile in the game.
 * <p>
 * The BomberProjectile is a specialized type of enemy that moves horizontally and
 * occasionally fires projectiles. It has specific health, fire rate, and movement
 * properties, making it a challenging opponent for the player.
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
     * Constructs a BomberProjectile with specified initial positions.
     *
     * @param initialXPos The initial X position of the BomberProjectile.
     * @param initialYPos The initial Y position of the BomberProjectile.
     */
    public BomberPlane(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT,initialXPos, initialYPos, HEALTH);
    }

    /**
     * Fires a projectile from the BomberProjectile.
     * <p>
     * The firing mechanism is probabilistic, determined by the {@code FIRE_RATE}. If the
     * BomberProjectile fires a projectile in the current frame, it returns a new projectile
     * instance with calculated positions.
     * </p>
     *
     * @return An {@link ActiveActor} representing the projectile if fired, or {@code null} if no projectile is fired.
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
     * Retrieves the horizontal velocity of the BomberProjectile.
     * <p>
     * The BomberProjectile moves at a constant horizontal velocity.
     * </p>
     *
     * @return The horizontal velocity of the BomberProjectile.
     */
    @Override
    public double getHorizontalVelocity(){
        return HORIZONTAL_MOVEMENT;
    }

}
