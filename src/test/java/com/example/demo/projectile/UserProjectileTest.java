package com.example.demo.projectile;

import com.example.demo.util.JavaFXTestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserProjectileTest {

    private UserProjectile userProjectile;

    @BeforeAll
    static void initFX() throws InterruptedException {
        JavaFXTestUtils.initJavaFX();
    }

    @BeforeEach
    void setUp() {
        // Initialize the UserProjectile with a specific initial position
        userProjectile = new UserProjectile(100, 200);
    }

    @Test
    void testInitialPosition() {
        assertEquals(100, userProjectile.getLayoutX(), "Initial X position should match the constructor value.");
        assertEquals(200, userProjectile.getLayoutY(), "Initial Y position should match the constructor value.");
    }

    @Test
    void testHorizontalVelocity() {
        assertEquals(15, userProjectile.getHorizontalVelocity(), "Horizontal velocity should match the expected value.");
    }

    @Test
    void testTakeDamageAndDestroy() {
        assertFalse(userProjectile.getIsDestroyed(), "Projectile should not be destroyed initially.");

        userProjectile.takeDamage(); // Simulate taking damage

        assertTrue(userProjectile.getIsDestroyed(), "Projectile should be destroyed after taking damage.");
    }

    @Test
    void testUpdatePosition() {
        // Record initial position
        double initialX = userProjectile.getLayoutX() + userProjectile.getTranslateX();

        // Update position
        userProjectile.updatePosition();

        // Check new position
        double updatedX = userProjectile.getLayoutX() + userProjectile.getTranslateX();

        assertEquals(initialX + userProjectile.getHorizontalVelocity(), updatedX, "BomberProjectile should move horizontally.");
    }
}
