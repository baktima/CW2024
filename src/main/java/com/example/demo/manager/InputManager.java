package com.example.demo.manager;

import com.example.demo.plane.UserPlane;
import javafx.scene.input.KeyCode;

public class InputManager {
    private final UserPlane user;
    private final Runnable togglePause;

    public InputManager(UserPlane user, Runnable togglePause) {
        this.user = user;
        this.togglePause = togglePause;
    }

    public void handleKeyPress(KeyCode kc) {
        if (kc == KeyCode.UP) user.moveUp();
        if (kc == KeyCode.DOWN) user.moveDown();
        if (kc == KeyCode.SPACE) user.fireProjectile();
        if (kc == KeyCode.ESCAPE) togglePause.run();
    }

    public void handleKeyRelease(KeyCode kc) {
        if (kc == KeyCode.UP || kc == KeyCode.DOWN) user.stop();
    }
}
