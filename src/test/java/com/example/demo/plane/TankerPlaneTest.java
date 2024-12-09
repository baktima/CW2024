package com.example.demo.plane;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TankerPlaneTest {

    @Test
    void testInitialHealth() {
        TankerPlane tankerPlane = new TankerPlane(100, 200);
        assertEquals(8, tankerPlane.getHealth(), "TankerPlane should have initial health of 8.");
    }

    @Test
    void testInitialPosition() {
        TankerPlane tankerPlane = new TankerPlane(100, 200);
        assertEquals(100, tankerPlane.getLayoutX() + tankerPlane.getTranslateX(), "Initial X position should match.");
        assertEquals(200, tankerPlane.getLayoutY() + tankerPlane.getTranslateY(), "Initial Y position should match.");
    }

    @Test
    void testFireProjectile() {
        TankerPlane tankerPlane = new TankerPlane(100, 200);
        assertNull(tankerPlane.fireProjectile(), "TankerPlane should not fire projectiles.");
    }

    @Test
    void testToggleVerticalVelocity() {
        TankerPlane tankerPlane = new TankerPlane(100, 200);
        int initialVerticalVelocity = tankerPlane.getVerticalVelocity();

        // Simulate toggling
        tankerPlane.toggleVerticalVelocity();
        assertEquals(-initialVerticalVelocity, tankerPlane.getVerticalVelocity(), "Vertical velocity should toggle.");

        // Toggle again to ensure it flips back
        tankerPlane.toggleVerticalVelocity();
        assertEquals(initialVerticalVelocity, tankerPlane.getVerticalVelocity(), "Vertical velocity should toggle back.");
    }

    @Test
    void testZigzagMovement() {
        // Initialize TankerPlane
        TankerPlane tankerPlane = new TankerPlane(100, 200);

        // Get initial vertical velocity
        int initialVerticalVelocity = tankerPlane.getVerticalVelocity();

        // Simulate first position update
        double initialY = tankerPlane.getLayoutY() + tankerPlane.getTranslateY();
        tankerPlane.updatePosition(); // Move with initial vertical velocity
        double updatedY = tankerPlane.getLayoutY() + tankerPlane.getTranslateY();

        // Assert movement in the initial direction
        assertEquals(initialY + initialVerticalVelocity, updatedY, 0.01, "TankerPlane should move in the initial vertical direction.");

        // Simulate zigzag by toggling direction
        tankerPlane.toggleVerticalVelocity();
        int toggledVerticalVelocity = tankerPlane.getVerticalVelocity();

        // Assert velocity direction toggled
        assertEquals(-initialVerticalVelocity, toggledVerticalVelocity, "Vertical velocity should be toggled.");

        // Update position after toggling
        initialY = updatedY;
        tankerPlane.updatePosition();
        updatedY = tankerPlane.getLayoutY() + tankerPlane.getTranslateY();

        // Assert movement in the new direction
        assertEquals(initialY + toggledVerticalVelocity, updatedY, 0.01, "TankerPlane should move in the toggled vertical direction.");
    }


    @Test
    void testHorizontalVelocity() {
        TankerPlane tankerPlane = new TankerPlane(100, 200);
        assertEquals(-3, tankerPlane.getHorizontalVelocity(), "Horizontal velocity should be -3.");
    }

    @Test
    void testTakeDamage() {
        TankerPlane tankerPlane = new TankerPlane(100, 200);
        assertEquals(8, tankerPlane.getHealth(), "Initial health should be 8.");

        // Simulate taking damage
        tankerPlane.takeDamage();
        assertEquals(7, tankerPlane.getHealth(), "Health should decrease to 7 after one hit.");

        // Simulate taking enough damage to destroy it
        for (int i = 0; i < 7; i++) {
            tankerPlane.takeDamage();
        }
        assertTrue(tankerPlane.getIsDestroyed(), "TankerPlane should be destroyed after all health is gone.");
    }
}
