package com.example.demo.projectile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BomberProjectileTest {

    private BomberProjectile bomberProjectile;

    @BeforeEach
    void setUp() {
        // Initialize the BomberProjectile with a specific initial position
        bomberProjectile = new BomberProjectile(100, 200);
    }

    @Test
    void testInitialPosition() {
        assertEquals(100, bomberProjectile.getLayoutX(), "Initial X position should match the constructor value.");
        assertEquals(200, bomberProjectile.getLayoutY(), "Initial Y position should match the constructor value.");
    }

    @Test
    void testHorizontalVelocity() {
        assertEquals(-8, bomberProjectile.getHorizontalVelocity(), "Horizontal velocity should match the expected value.");
    }

    @Test
    void testUpdatePosition() {
        // Record initial position
        double initialX = bomberProjectile.getLayoutX() + bomberProjectile.getTranslateX();
        double initialY = bomberProjectile.getLayoutY() + bomberProjectile.getTranslateY();

        // Update position
        bomberProjectile.updatePosition();

        // Check new position
        double updatedX = bomberProjectile.getLayoutX() + bomberProjectile.getTranslateX();
        double updatedY = bomberProjectile.getLayoutY() + bomberProjectile.getTranslateY();

        assertEquals(initialX + bomberProjectile.getHorizontalVelocity(), updatedX, "BomberProjectile should move horizontally.");
        assertEquals(initialY + 1.5, updatedY, "BomberProjectile should move vertically.");
    }

    @Test
    void testTakeDamageAndDestroy() {
        assertFalse(bomberProjectile.getIsDestroyed(), "BomberProjectile should not be destroyed initially.");

        bomberProjectile.takeDamage();

        assertTrue(bomberProjectile.getIsDestroyed(), "BomberProjectile should be destroyed after taking damage.");
    }
}
