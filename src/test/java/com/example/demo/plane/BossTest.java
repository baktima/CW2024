package com.example.demo.plane;

import com.example.demo.display.TextDisplay;
import com.example.demo.actor.ActiveActor;
import com.example.demo.projectile.BossProjectile;
import com.example.demo.util.JavaFXTestUtils;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BossTest {

    private Boss boss;
    private TextDisplay mockTextDisplay;

    @BeforeAll
    static void initFX() throws InterruptedException {
        JavaFXTestUtils.initJavaFX();
    }

    @BeforeEach
    void setUp() throws InterruptedException {
        // Create a mock TextDisplay object to avoid UI dependencies
        mockTextDisplay = mock(TextDisplay.class);

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            boss = new Boss(mockTextDisplay);
            latch.countDown();
        });
        assertTrue(latch.await(3, TimeUnit.SECONDS), "Boss initialization timed out");
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
    void testTakeDamageWithShield() throws InterruptedException {
        // Activate shield by updating actor until shield shows
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            for (int i = 0; i < 200; i++) {
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

            latch.countDown();
        });

        assertTrue(latch.await(3, TimeUnit.SECONDS), "Shield test timed out");
    }

    @Test
    void testReset() {
        boss.takeDamage(); // Decrease health
        boss.reset();

        assertEquals(50, boss.getHealth(), "Boss health should reset to initial value.");
        // The boss updates health at initialization and after damage, reset doesn't necessarily call updateBossHealth again
        // but if it did or you'd like to verify it, you can adjust this line:
        // verify(mockTextDisplay, atLeastOnce()).updateBossHealth(50);
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
        for (int i = 0; i < 200; i++) {
            boss.updatePosition();
        }

        // Ensure vertical movement occurs
        double updatedY = boss.getLayoutY() + boss.getTranslateY();
        assertNotEquals(initialY, updatedY, "Boss should move vertically in a random pattern.");
    }

    @Test
    void testShieldActivationAndDeactivation() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            boolean shieldActivated = false;
            boolean shieldDeactivated = false;

            // Simulate enough updates to trigger shield activation and deactivation
            for (int i = 0; i < 400; i++) {
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

            latch.countDown();
        });
        assertTrue(latch.await(3, TimeUnit.SECONDS), "Shield activation/deactivation test timed out");
    }

    // Boundary tests
    @Test
    void testBossDoesNotExceedTopBoundary() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            // Attempt to move boss above the top boundary
            boss.setTranslateY(-10);
            boss.updatePosition();

            double correctedY = boss.getLayoutY() + boss.getTranslateY();
            assertTrue(correctedY >= 0, "Boss should not go above the top boundary. Current Y: " + correctedY);
            latch.countDown();
        });
        assertTrue(latch.await(3, TimeUnit.SECONDS), "Top boundary test timed out");
    }

    @Test
    void testBossDoesNotExceedBottomBoundary() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            // Attempt to move boss below the bottom boundary (475.0)
            boss.setTranslateY(500);
            boss.updatePosition();

            double correctedY = boss.getLayoutY() + boss.getTranslateY();
            assertTrue(correctedY <= 475, "Boss should not go beyond the bottom boundary. Current Y: " + correctedY);
            latch.countDown();
        });
        assertTrue(latch.await(3, TimeUnit.SECONDS), "Bottom boundary test timed out");
    }
}
