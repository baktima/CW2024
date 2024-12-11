package com.example.demo.plane;

import com.example.demo.projectile.UserProjectile;
import com.example.demo.actor.ActiveActor;
import com.example.demo.util.JavaFXTestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserPlaneTest {

    private UserPlane userPlane;

    @BeforeAll
    static void initJavaFX() throws InterruptedException {
        // Initialize the JavaFX platform once for all tests
        JavaFXTestUtils.initJavaFX();
    }

    @BeforeEach
    void setUp() {
        // Initialize the UserPlane with a specific health
        userPlane = new UserPlane(3);
    }

    @Test
    void testInitialHealth() {
        assertEquals(3, userPlane.getHealth(), "UserPlane should have the correct initial health.");
    }

    //all the movement and firing the projectiles needs an input thus just simulating the inputs.
    @Test
    void testMoveUp() {
        userPlane.moveUp();
        userPlane.updatePosition();

        // Simulate upward movement
        double initialY = userPlane.getLayoutY() + userPlane.getTranslateY();
        userPlane.updatePosition();
        double updatedY = userPlane.getLayoutY() + userPlane.getTranslateY();

        assertTrue(updatedY < initialY, "UserPlane should move up when moveUp is called.");
    }

    @Test
    void testMoveDown() {
        userPlane.moveDown();
        userPlane.updatePosition();

        // Simulate downward movement
        double initialY = userPlane.getLayoutY() + userPlane.getTranslateY();
        userPlane.updatePosition();
        double updatedY = userPlane.getLayoutY() + userPlane.getTranslateY();

        assertTrue(updatedY > initialY, "UserPlane should move down when moveDown is called.");
    }

    @Test
    void testStopMovement() {
        userPlane.moveDown();
        userPlane.stop();
        userPlane.updatePosition();

        // Ensure no movement after stop
        double initialY = userPlane.getLayoutY() + userPlane.getTranslateY();
        userPlane.updatePosition();
        double updatedY = userPlane.getLayoutY() + userPlane.getTranslateY();

        assertEquals(initialY, updatedY, "UserPlane should not move when stop is called.");
    }

    @Test
    void testFireProjectile() {
        ActiveActor projectile = userPlane.fireProjectile();

        // Check if the projectile is created and positioned correctly
        assertNotNull(projectile, "UserPlane should create a projectile when fireProjectile is called.");
        assertInstanceOf(UserProjectile.class, projectile, "The projectile fired should be a UserProjectile.");
    }

    @Test
    void testKillCountIncrement() {
        int initialKillCount = userPlane.getNumberOfKills();

        // Increment kill count
        userPlane.incrementKillCount();

        assertEquals(initialKillCount + 1, userPlane.getNumberOfKills(), "UserPlane should correctly increment kill count.");
    }

    @Test
    void testSetKillCount() {
        userPlane.setNumberOfKills(5);

        assertEquals(5, userPlane.getNumberOfKills(), "UserPlane should correctly set the kill count.");
    }

    @Test
    void testHealthReduction() {
        // Reduce health by 1
        userPlane.takeDamage();

        assertEquals(2, userPlane.getHealth(), "UserPlane health should reduce correctly after taking damage.");
    }

    @Test
    void testDestroyedOnZeroHealth() {
        // Reduce health to zero because the checking of the destroyed is at taking the damage
        for(int i = 0; i < 3; i++){
            userPlane.takeDamage();
        }

        assertTrue(userPlane.getIsDestroyed(), "UserPlane should be destroyed when health reaches zero.");
    }

    @Test
    void testDoNotExceedTopBoundary() {
        // Move the plane upwards multiple times
        userPlane.moveUp();

        // Keep calling updatePosition to simulate continuous upward movement
        for (int i = 0; i < 100; i++) {
            userPlane.updatePosition();
        }

        double currentY = userPlane.getLayoutY() + userPlane.getTranslateY();
        assertTrue(currentY >= 0, "UserPlane should not move above the top boundary (Y=0). Current Y: " + currentY);
    }

    @Test
    void testDoNotExceedBottomBoundary() {
        // Move the plane downwards multiple times
        userPlane.moveDown();

        // Keep calling updatePosition to simulate continuous downward movement
        for (int i = 0; i < 100; i++) {
            userPlane.updatePosition();
        }

        double currentY = userPlane.getLayoutY() + userPlane.getTranslateY();
        assertTrue(currentY <= 650.0, "UserPlane should not move below the bottom boundary (Y=650). Current Y: " + currentY);
    }
}
