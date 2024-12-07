package com.example.demo.projectile;

/**
 * Represents a special projectile launched by special enemy units in the game.
 * This projectile is unique because it has both horizontal and vertical velocity,
 * making it more challenging for the player to avoid.
 */
public class BomberProjectile extends Projectile {

    private static final String IMAGE_NAME = "EnemyAtom.png";
    private static final int IMAGE_HEIGHT = 30;
    private static final int HORIZONTAL_VELOCITY = -8;
    private static final double VERTICAL_VELOCITY = 1.5; // need to be something small so the bullet is actually really annoying

    /**
     * Constructs a new EnemySpecialProjectile with a specified initial position.
     *
     * @param initialXPos The initial x-coordinate of the projectile.
     * @param initialYPos The initial y-coordinate of the projectile.
     */
    public BomberProjectile(double initialXPos, double initialYPos) {
        super(IMAGE_NAME,IMAGE_HEIGHT, initialXPos, initialYPos);
    }

    /**
     * Updates the position of the projectile, applying both horizontal and vertical movement.
     * This overrides the default behavior to include a vertical velocity.
     */
    @Override
    public void updatePosition(){
        super.updatePosition();
        moveVertically(VERTICAL_VELOCITY);
    }

    /**
     * Retrieves the horizontal velocity of the special enemy projectile.
     *
     * @return The horizontal velocity of the projectile.
     */
    @Override
    public double getHorizontalVelocity() {
        return HORIZONTAL_VELOCITY;
    }
}

