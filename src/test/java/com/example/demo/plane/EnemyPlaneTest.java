package com.example.demo.plane;

import com.example.demo.projectile.EnemyProjectile;
import com.example.demo.actor.ActiveActor;
import com.example.demo.util.JavaFXTestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnemyPlaneTest {

    private EnemyPlane enemyPlane;

    @BeforeAll
    static void initFX() throws InterruptedException {
        JavaFXTestUtils.initJavaFX();
    }

    @BeforeEach
    void setUp() {
        // Initialize an EnemyPlane at a specific position
        enemyPlane = new EnemyPlane(500, 300);
    }

    @Test
    void testInitialPosition() {
        assertEquals(500, enemyPlane.getLayoutX(), "EnemyPlane should have the correct initial X position.");
        assertEquals(300, enemyPlane.getLayoutY(), "EnemyPlane should have the correct initial Y position.");
    }

    @Test
    void testFireProjectileProbability() {
        boolean firedProjectile = false;

        // Simulate multiple attempts to fire a projectile
        for (int i = 0; i < 1000; i++) {
            ActiveActor projectile = enemyPlane.fireProjectile();
            if (projectile instanceof EnemyProjectile) {
                firedProjectile = true;
                break;
            }
        }

        assertTrue(firedProjectile, "EnemyPlane should fire a projectile at least once over multiple attempts.");
    }

    @Test
    void testProjectilePositionOffsets() {
        double xOffset = EnemyPlane.getProjectileXPositionOffset();
        double yOffset = EnemyPlane.getProjectileYPositionOffset();

        assertEquals(-100.0, xOffset, "Projectile X offset should match the defined constant.");
        assertEquals(50.0, yOffset, "Projectile Y offset should match the defined constant.");
    }

    @Test
    void testHorizontalVelocity() {
        assertEquals(-5, enemyPlane.getHorizontalVelocity(), "EnemyPlane should have the correct horizontal velocity.");
    }

    @Test
    void testHealthReductionAndDestroyed() {
        // Reduce health by 1
        enemyPlane.takeDamage();

        assertEquals(0, enemyPlane.getHealth(), "EnemyPlane health should reduce correctly after taking damage.");
        assertTrue(enemyPlane.getIsDestroyed(), "EnemyPlane should be destroyed when health reaches zero.");
    }
}
