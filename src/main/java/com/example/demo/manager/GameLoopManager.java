package com.example.demo.manager;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Manages the game loop using a JavaFX {@link Timeline}.
 * This class provides an easy way to control the execution of repetitive game logic at a fixed interval.
 *
 * <p>The game loop is driven by a {@link Runnable} that contains the logic to execute at each cycle.</p>
 */
public class GameLoopManager {
    private final Timeline timeline;

    /**
     * Creates a new GameLoopManager with delay between updates and the logic to execute on each cycle.
     *
     * @param delay the delay (in milliseconds) between each update
     * @param updateLogic the logic to execute on each update
     */
    public GameLoopManager(double delay, Runnable updateLogic) {
        timeline = new Timeline(new KeyFrame(Duration.millis(delay), e -> updateLogic.run()));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    /**
     * Starts the game loop and executing the function continuously.
     */
    public void start() {
        timeline.play();
    }

    /**
     * Stop the game loop.
     */
    public void stop() {
        timeline.stop();
    }

    /**
     *  Starts the game loop from the beginning, resetting the timeline before execution.
     */
    public void playFromBeginning(){
        timeline.playFromStart();
    }
}
