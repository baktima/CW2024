package com.example.demo.manager;

import com.example.demo.plane.UserPlane;
import javafx.scene.input.KeyCode;

/**
 * Manages input handling for the user-controlled plane in the game.
 *<p>
 * This class maps keyboard inputs to user actions such as movement, firing, and pausing the game.
 *</p>
 */
public class InputManager {
    private final UserPlane user;
    private final Runnable togglePause;
    private final Runnable fireWithCooldown;
    private final Runnable userSkillClearEnemyBullet;

    /**
     * Creates a new InputManager to handle user input for the specified {@link UserPlane}.
     *
     * @param user the {@link UserPlane} controlled by the user
     * @param togglePause a {@link Runnable} that toggles the game's pause state when invoked
     * @param fireWithCooldown a {@link Runnable} that execute the generation of the user projectile.
     * @param userSkillClearEnemyBullet a {@link Runnable}
     */
    public InputManager(UserPlane user, Runnable togglePause, Runnable fireWithCooldown, Runnable userSkillClearEnemyBullet) {
        this.user = user;
        this.togglePause = togglePause;
        this.fireWithCooldown = fireWithCooldown;
        this.userSkillClearEnemyBullet = userSkillClearEnemyBullet;
    }

    /**
     * Handles key press events and triggers corresponding actions for the user plane.
     *
     * @param kc the {@link KeyCode} of the key that was pressed
     */
    public void handleKeyPress(KeyCode kc) {
        if (kc == KeyCode.UP) user.moveUp();
        if (kc == KeyCode.DOWN) user.moveDown();
        if (kc == KeyCode.SPACE) fireWithCooldown.run();
        if (kc == KeyCode.ESCAPE) togglePause.run();
        if (kc == KeyCode.S) userSkillClearEnemyBullet.run();
    }

    /**
     * Handles key release events and stops the user's movement when appropriate.
     *
     * @param kc the {@link KeyCode} of the key that was released
     */
    public void handleKeyRelease(KeyCode kc) {
        if (kc == KeyCode.UP || kc == KeyCode.DOWN) user.stop();
    }
}
