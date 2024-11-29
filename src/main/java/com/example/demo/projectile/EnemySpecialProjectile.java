package com.example.demo.projectile;

public class EnemySpecialProjectile extends EnemyProjectile {
    private static final String IMAGE_NAME = "EnemyAtom.png";
    private static final int HORIZONTAL_VELOCITY = -8;
    private static final double VERTICAL_VELOCITY = 1.5; // need to be something small so the bullet is actually really annoying

    public EnemySpecialProjectile(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, initialXPos, initialYPos);
    }

    @Override
    public void updatePosition(){
        super.updatePosition();
        moveVertically(VERTICAL_VELOCITY);
    }

    //testing
    @Override
    public double getHorizontalVelocity() {
        return HORIZONTAL_VELOCITY;
    }

}

