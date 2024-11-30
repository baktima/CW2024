package com.example.demo.manager;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GameLoopManager {
    private final Timeline timeline;

    public GameLoopManager(double delay, Runnable updateLogic) {
        timeline = new Timeline(new KeyFrame(Duration.millis(delay), e -> updateLogic.run()));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void start() {
        timeline.play();
    }

    public void stop() {
        timeline.stop();
    }

    public void reset() {
        timeline.stop();
        timeline.getKeyFrames().clear();
    }

    public void playFromBeginning(){
        timeline.playFromStart();
    }
}
