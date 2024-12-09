package com.example.demo.plane;

import com.example.demo.display.TextDisplay;
import com.example.demo.actor.ActiveActor;
import com.example.demo.projectile.BossProjectile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BossTest {

    private Boss boss;
    private TextDisplay mockTextDisplay;

    @BeforeEach
    void setUp() {
        // Create a mock TextDisplay object to avoid UI dependencies
        mockTextDisplay = mock(TextDisplay.class);
        boss = new Boss(mockTextDisplay);
    }

    @Test
    void testInitialHealth() {
        assertEquals(50, boss.getHealth(), "Boss should start with initial health of 50.");
        verify(mockTextDisplay).updateBossHealth(50);
    }

    @Test
    void testTakeDamageWithoutShield() {
        boss.takeDamage();

        assertEquals(49, boss.getHealth(), "Boss health should decrease by 1 when taking damage without a shield.");
        verify(mockTextDisplay).updateBossHealth(49);
    }

    @Test
    void testTakeDamageWithShield() {
        // Call updateActor repeatedly to simulate shield activation
        for (int i = 0; i < 100; i++) { // Adjust the loop count if necessary
            boss.updateActor();
            if (boss.getShieldImage().isVisible()) {
                break;
            }
        }

        // Verify the shield is now active
        assertTrue(boss.getShieldImage().isVisible(), "Shield should be active after updateActor triggers it.");

        // Try taking damage
        boss.takeDamage();

        // Verify health did not decrease
        assertEquals(50, boss.getHealth(), "Boss health should not decrease when the shield is active.");
        verify(mockTextDisplay, times(1)).updateBossHealth(50);
    }

    @Test
    void testReset() {
        boss.takeDamage(); // Decrease health
        boss.reset();

        assertEquals(50, boss.getHealth(), "Boss health should reset to initial value.");
        verify(mockTextDisplay, times(1)).updateBossHealth(50); // Once during reset
    }

    @Test
    void testFireProjectile() {
        // Test multiple times due to probabilistic firing
        boolean projectileFired = false;

        for (int i = 0; i < 100; i++) {
            ActiveActor projectile = boss.fireProjectile();
            if (projectile != null) {
                projectileFired = true;
                assertInstanceOf(BossProjectile.class, projectile, "Boss should fire a BossProjectile.");
                break;
            }
        }

        assertTrue(projectileFired, "Boss should fire at least one projectile in multiple attempts.");
    }

    @Test
    void testMovementZigzag() {
        double initialY = boss.getLayoutY() + boss.getTranslateY();
        //make update position 50 times so the boss can move
        for(int i = 0 ; i < 50; i++) {
            boss.updatePosition();
        }

        // Ensure vertical movement occurs
        double updatedY = boss.getLayoutY() + boss.getTranslateY();
        assertNotEquals(initialY, updatedY, "Boss should move vertically in zigzag pattern.");
    }

    @Test
    void testShieldActivationAndDeactivation() {
        boolean shieldActivated = false;
        boolean shieldDeactivated = false;

        // Simulate enough updates to trigger shield activation and deactivation
        for (int i = 0; i < 200; i++) { // Loop count can be adjusted
            boss.updateActor();

            if (boss.getShieldImage().isVisible()) {
                shieldActivated = true;
            } else if (shieldActivated && !boss.getShieldImage().isVisible()) {
                shieldDeactivated = true;
                break;
            }
        }

        // Verify both activation and deactivation occurred
        assertTrue(shieldActivated, "Shield should activate during the update process.");
        assertTrue(shieldDeactivated, "Shield should deactivate after being activated.");
    }
}
