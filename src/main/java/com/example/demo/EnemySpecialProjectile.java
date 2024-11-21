package com.example.demo;

public class EnemySpecialProjectile extends EnemyProjectile{
    private static final int HORIZONTAL_VELOCITY = -8;
    private static final double VERTICAL_VELOCITY = 1; // need to be something small so the bullet is actually really annoying

    public EnemySpecialProjectile(double initialXPos, double initialYPos) {
        super(initialXPos, initialYPos);

    }

    @Override
    public void updatePosition(){
        moveHorizontally(HORIZONTAL_VELOCITY);
        moveVertically(VERTICAL_VELOCITY);
    }
}

