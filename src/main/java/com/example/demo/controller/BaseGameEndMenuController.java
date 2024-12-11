package com.example.demo.controller;

import com.example.demo.display.menu.MainMenu;
import com.example.demo.level.LevelParent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Abstract base class for menu controllers related to game-ending states.
 * This class provides shared functionality for handling common actions such as
 * transitioning to the main menu and cleaning up game resources.
 */
public abstract class BaseGameEndMenuController {
    /**
     * The current level instance associated with this controller.
     */
    protected LevelParent levelParent;

    /**
     * Default constructor for {@code BaseGameEndMenuController}.
     * <p>
     * This constructor does not perform any initialization as this class is designed
     * to be extended by specific menu controllers.
     */
    protected BaseGameEndMenuController() {
        // not intended for instantiation
    }

    /**
     * Initializes the controller with the current level instance.
     *
     * @param levelParent The current level instance.
     */
    public void initialize(LevelParent levelParent) {
        this.levelParent = levelParent;
    }

    /**
     * Exits the current level and transitions to the main menu.
     * Cleans up the current level resources and initializes a new controller
     * for the main menu.
     *
     * @throws IOException If there is an issue loading the main menu resources.
     */
    protected void exitToMainMenu() throws IOException {
        levelParent.cleanup();
        Stage stage = levelParent.getStage();
        Controller controller = new Controller(stage);
        MainMenu.showMainMenu(stage, controller);
    }
}
