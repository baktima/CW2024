package com.example.demo.projectile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnemyProjectileTest {

    private EnemyProjectile enemyProjectile;

    @BeforeEach
    void setUp() {
        // Initialize the EnemyProjectile with specific initial positions
        enemyProjectile = new EnemyProjectile(200, 300);
    }

    @Test
    void testInitialPosition() {
        // Verify the initial X and Y positions
        assertEquals(200, enemyProjectile.getLayoutX(), "Initial X position should match the constructor value.");
        assertEquals(300, enemyProjectile.getLayoutY(), "Initial Y position should match the constructor value.");
    }

    @Test
    void testHorizontalVelocity() {
        // Verify the horizontal velocity of the projectile
        assertEquals(-10, enemyProjectile.getHorizontalVelocity(), "Horizontal velocity should match the defined constant.");
    }

    @Test
    void testUpdatePosition() {
        // Record the initial position
        double initialX = enemyProjectile.getLayoutX() + enemyProjectile.getTranslateX();

        // Update the position
        enemyProjectile.updatePosition();

        // Get the updated position
        double updatedX = enemyProjectile.getLayoutX() + enemyProjectile.getTranslateX();

        // Verify the X position after movement
        assertEquals(initialX + enemyProjectile.getHorizontalVelocity(), updatedX, "EnemyProjectile should move correctly horizontally.");
    }

    @Test
    void testTakeDamageAndDestroyed() {
        // Verify that the projectile is not destroyed initially
        assertFalse(enemyProjectile.getIsDestroyed(), "EnemyProjectile should not be destroyed initially.");

        // Apply damage
        enemyProjectile.takeDamage();

        // Verify that the projectile is destroyed after taking damage
        assertTrue(enemyProjectile.getIsDestroyed(), "EnemyProjectile should be destroyed after taking damage.");
    }
}