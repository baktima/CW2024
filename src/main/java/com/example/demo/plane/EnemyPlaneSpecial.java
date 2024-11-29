package com.example.demo.plane;

import com.example.demo.projectile.EnemySpecialProjectile;
import com.example.demo.actor.ActiveActor;

public class EnemyPlaneSpecial extends EnemyPlane {

    private static final String IMAGE_NAME = "EnemyAtomCarrier.png";
    private static final int HEALTH = 2;
    private static final int HORIZONTAL_MOVEMENT = -5;
    private static final double FIRE_RATE = 0.0125;
    private static final int PROJECTILE_OFFSET_X = 25;
    private static final int PROJECTILE_OFFSET_Y = 35;

    public EnemyPlaneSpecial(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, initialXPos, initialYPos, HEALTH);
    }

    @Override
    public ActiveActor fireProjectile() {

        if (Math.random() < FIRE_RATE) {
            double projectileXPosition = getProjectileXPosition(PROJECTILE_OFFSET_X);
            double projectileYPosition = getProjectileYPosition(PROJECTILE_OFFSET_Y);
            return new EnemySpecialProjectile(projectileXPosition, projectileYPosition);
        }
        return null;
    }

    @Override
    public double getHorizontalVelocity(){
        return HORIZONTAL_MOVEMENT;
    }

}
