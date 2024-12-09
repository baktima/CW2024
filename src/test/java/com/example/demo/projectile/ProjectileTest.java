package com.example.demo.projectile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectileTest {

    // A simple implementation of Projectile for testing
    private static class TestProjectile extends Projectile {
        public TestProjectile(double initialXPos, double initialYPos) {
            //use any projectile for the testing
            super("EnemyMissileSlug.png", 30, initialXPos, initialYPos);
        }

        @Override
        public double getHorizontalVelocity() {
            return 0; // TestProjectile has no horizontal velocity
        }
    }

    private TestProjectile projectile;

    @BeforeEach
    void setUp() {
        projectile = new TestProjectile(100, 200);
    }

    @Test
    void testInitialPosition() {
        assertEquals(100, projectile.getLayoutX(), "Initial X position should match the constructor value.");
        assertEquals(200, projectile.getLayoutY(), "Initial Y position should match the constructor value.");
    }

    @Test
    void testTakeDamageAndDestroy() {
        assertFalse(projectile.getIsDestroyed(), "Projectile should not be destroyed initially.");

        projectile.takeDamage(); // Simulate taking damage

        assertTrue(projectile.getIsDestroyed(), "Projectile should be destroyed after taking damage.");
    }

}
