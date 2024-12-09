package com.example.demo.util;

import javafx.application.Platform;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class JavaFXTestUtils {
    public static void initJavaFX() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown); // Initialize the JavaFX Platform
        if (!latch.await(5, TimeUnit.SECONDS)) { // Wait for initialization
            throw new RuntimeException("JavaFX platform failed to start");
        }
    }
}
