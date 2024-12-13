package com.example.demo.plane;

import com.example.demo.actor.ActiveActor;
import com.example.demo.util.JavaFXTestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Test class for FighterPlane
class FighterPlaneTest {

    private FighterPlane fighterPlane;

    @BeforeAll
    static void initFX() throws InterruptedException {
        JavaFXTestUtils.initJavaFX();
    }

    @BeforeEach
    void setUp() {
        // Create an anonymous subclass of FighterPlane for testing.
        // Using random images that's on the asset (this case using the user plane).
        fighterPlane = new FighterPlane("UserPlaneSlug2.png", 50, 100.0, 200.0, 3) {
            @Override
            public double getHorizontalVelocity() {
                return 0;
            }

            @Override
            public ActiveActor fireProjectile() {
                return null; // No projectile logic needed for this test
            }
        };
    }

    @Test
    void testTakeDamage() {
        assertEquals(3, fighterPlane.getHealth(), "Initial health should be 3.");

        fighterPlane.takeDamage();
        assertEquals(2, fighterPlane.getHealth(), "Health should decrease by 1 after taking damage.");

        fighterPlane.takeDamage();
        assertEquals(1, fighterPlane.getHealth(), "Health should decrease by 1 again after taking damage.");

        fighterPlane.takeDamage();
        assertTrue(fighterPlane.getIsDestroyed(), "Plane should be destroyed when health reaches zero.");
    }

    @Test
    void testSetAndGetHealth() {
        fighterPlane.setHealth(5);
        assertEquals(5, fighterPlane.getHealth(), "Health should be updated to the set value.");
    }

    @Test
    void testProjectilePositionCalculations() {
        double expectedX = 150.0; // Initial X + offset
        double expectedY = 250.0; // Initial Y + offset

        assertEquals(expectedX, fighterPlane.getProjectileXPosition(50.0), "Projectile X position should be correctly calculated.");
        assertEquals(expectedY, fighterPlane.getProjectileYPosition(50.0), "Projectile Y position should be correctly calculated.");
    }
}
