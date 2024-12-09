package com.example.demo.plane;

import com.example.demo.actor.ActiveActor;
import com.example.demo.projectile.BomberProjectile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BomberPlaneTest {

    private BomberPlane bomberPlane;

    @BeforeEach
    void setUp() {
        // Initialize a BomberPlane at a specific position
        bomberPlane = new BomberPlane(500, 300);
    }

    @Test
    void testInitialPosition() {
        assertEquals(500, bomberPlane.getLayoutX(), "BomberPlane should have the correct initial X position.");
        assertEquals(300, bomberPlane.getLayoutY(), "BomberPlane should have the correct initial Y position.");
    }

    @Test
    void testFireProjectileProbability() {
        boolean firedProjectile = false;

        // Simulate multiple attempts to fire a projectile
        for (int i = 0; i < 1000; i++) {
            ActiveActor projectile = bomberPlane.fireProjectile();
            if (projectile != null && projectile instanceof BomberProjectile) {
                firedProjectile = true;
                break;
            }
        }

        assertTrue(firedProjectile, "BomberPlane should fire a projectile at least once over multiple attempts.");
    }

    @Test
    void testProjectilePosition() {
        ActiveActor projectile = bomberPlane.fireProjectile();

        if (projectile != null) {
            double expectedX = bomberPlane.getProjectileXPosition(25);
            double expectedY = bomberPlane.getProjectileYPosition(35);

            assertEquals(expectedX, projectile.getLayoutX(), "Projectile should have the correct X position.");
            assertEquals(expectedY, projectile.getLayoutY(), "Projectile should have the correct Y position.");
        } else {
            assertNull(projectile, "Projectile should be null if not fired.");
        }
    }

    @Test
    void testHorizontalVelocity() {
        assertEquals(-5, bomberPlane.getHorizontalVelocity(), "BomberPlane should have the correct horizontal velocity.");
    }

    @Test
    void testHealthReductionAndDestroyed() {
        // Reduce health by 1
        bomberPlane.takeDamage();

        assertEquals(1, bomberPlane.getHealth(), "BomberPlane health should reduce correctly after taking damage.");

        //reduce again since the health of the bomber plane is 2
        bomberPlane.takeDamage();
        assertTrue(bomberPlane.getIsDestroyed(), "EnemyPlane should be destroyed when health reaches zero.");
    }
}
