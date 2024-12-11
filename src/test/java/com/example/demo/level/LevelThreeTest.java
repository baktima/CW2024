package com.example.demo.level;

import com.example.demo.level.levelViews.LevelView;
import com.example.demo.manager.ActorManager;
import com.example.demo.util.JavaFXTestUtils;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class LevelThreeTest {

    // Test subclass of LevelThree just to mock the LevelView to avoid UI complexity.
    private static class TestLevelThree extends LevelThree {
        public TestLevelThree(double screenHeight, double screenWidth) {
            super(screenHeight, screenWidth);
        }

        @Override
        protected LevelView instantiateLevelView() {
            return mock(LevelView.class);
        }
    }

    private TestLevelThree levelThree;

    @BeforeAll
    static void initJavaFX() throws InterruptedException {
        // Initialize JavaFX if your code or classes depend on it
        JavaFXTestUtils.initJavaFX();
    }

    @BeforeEach
    void setUp() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            // Instantiate the TestLevelThree which uses the original LevelParent logic
            levelThree = new TestLevelThree(600, 800);
            latch.countDown();
        });
        assertTrue(latch.await(5, TimeUnit.SECONDS), "JavaFX initialization timed out");
    }

    @Test
    void testEnemiesAlwaysMaintainedAtFive() {
        ActorManager actorManager = levelThree.getActorManager();

        // Initially no enemies
        assertEquals(0, actorManager.getEnemyUnits().size(), "Initially, no enemies should be present.");

        // Attempt to spawn until we have 5 enemies
        // Because spawn probabilities are less than 1.0, we may need multiple attempts.
        // We'll try up to 100 times to get 5 enemies.
        for (int i = 0; i < 100 && actorManager.getEnemyUnits().size() < 5; i++) {
            levelThree.spawnEnemyUnits();
        }

        assertEquals(5, actorManager.getEnemyUnits().size(),
                "After initial spawn attempts, there should be 5 enemies.");

        // Simulate destroying 2 enemies
        actorManager.getEnemyUnits().remove(0);
        actorManager.getEnemyUnits().remove(0);
        assertEquals(3, actorManager.getEnemyUnits().size(),
                "After removing 2 enemies, there should be 3 left.");

        //try to always respawning and in the end it needs to only reach 5 no more no less
        for (int i = 0; i < 1000 ; i++) {
            levelThree.spawnEnemyUnits();
        }

        assertEquals(5, actorManager.getEnemyUnits().size(),
                "After respawning, total enemies should be back to 5.");
    }
}
