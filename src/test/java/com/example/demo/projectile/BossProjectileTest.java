package com.example.demo.projectile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BossProjectileTest {

    private BossProjectile bossProjectile;

    @BeforeEach
    void setUp() {
        // Initialize the BossProjectile with a specific initial Y position
        bossProjectile = new BossProjectile(300);
    }

    @Test
    void testInitialPosition() {
        assertEquals(950, bossProjectile.getLayoutX(), "Initial X position should match the defined constant.");
        assertEquals(300, bossProjectile.getLayoutY(), "Initial Y position should match the constructor value.");
    }

    @Test
    void testHorizontalVelocity() {
        assertEquals(-15, bossProjectile.getHorizontalVelocity(), "Horizontal velocity should match the defined constant.");
    }

    @Test
    void testUpdatePosition() {
        // Record the initial X position
        double initialX = bossProjectile.getLayoutX() + bossProjectile.getTranslateX();

        // Update position
        bossProjectile.updatePosition();

        // Check the new X position
        double updatedX = bossProjectile.getLayoutX() + bossProjectile.getTranslateX();

        assertEquals(initialX + bossProjectile.getHorizontalVelocity(), updatedX, "BossProjectile should move horizontally.");
    }

    @Test
    void testTakeDamageAndDestroy() {
        assertFalse(bossProjectile.getIsDestroyed(), "BossProjectile should not be destroyed initially.");

        bossProjectile.takeDamage();

        assertTrue(bossProjectile.getIsDestroyed(), "BossProjectile should be destroyed after taking damage.");
    }
}
