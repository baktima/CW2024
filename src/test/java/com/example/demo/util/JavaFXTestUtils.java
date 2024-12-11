package com.example.demo.util;

import javafx.application.Platform;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class JavaFXTestUtils {

    private static boolean initialized = false; // Ensure JavaFX initializes only once

    public static void initJavaFX() throws InterruptedException {
        if (initialized) {
            return; // JavaFX has already been initialized
        }

        CountDownLatch latch = new CountDownLatch(1);

        // Initialize the JavaFX platform
        Platform.startup(() -> {
            initialized = true; // Mark as initialized
            latch.countDown(); // Signal completion
        });

        // Wait for initialization to complete
        if (!latch.await(5, TimeUnit.SECONDS)) {
            throw new RuntimeException("JavaFX platform failed to start");
        }
    }
}
