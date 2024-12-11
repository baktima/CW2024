/**
 * The {@code com.example.demo} module defines the structure and dependencies
 * of the game application. It specifies required JavaFX modules, external libraries,
 * and exports game packages for accessibility.
 * <p>
 * This module includes core game logic, controllers, actors, planes, projectiles,
 * levels, displays, sounds, and implementations.
 * </p>
 */
module com.example.demo {
    // Required JavaFX modules for UI and media functionality
    requires javafx.controls; // For UI controls like buttons and labels
    requires javafx.fxml; // For FXML-based UI designs
    requires javafx.media; // For handling audio playback
    requires java.logging; // For logging purposes

    // Core application package
    opens com.example.demo to javafx.fxml; // Allows FXML access to the core package
    exports com.example.demo;

    // Controllers for managing UI and game state
    opens com.example.demo.controller to javafx.fxml; // FXML access to controller package
    exports com.example.demo.controller;

    // Actors and gameplay elements
    opens com.example.demo.actor to javafx.fxml;
    exports com.example.demo.actor;

    // Planes and their behaviors
    opens com.example.demo.plane to javafx.fxml;
    exports com.example.demo.plane;

    // Projectiles and related functionalities
    opens com.example.demo.projectile to javafx.fxml;
    exports com.example.demo.projectile;

    // Levels and level management
    opens com.example.demo.level to javafx.fxml;
    exports com.example.demo.level;

    // Level views for specific levels
    opens com.example.demo.level.levelViews to javafx.fxml;
    exports com.example.demo.level.levelViews;

    // Display elements and menus
    opens com.example.demo.display to javafx.fxml;
    exports com.example.demo.display;

    // Menus for in-game UI like pause and game-over screens
    opens com.example.demo.display.menu to javafx.fxml;
    exports com.example.demo.display.menu;

    // Sound effects and background music management
    opens com.example.demo.sound to javafx.fxml;
    exports com.example.demo.sound;

    // Implementations (e.g., interfaces)
    opens com.example.demo.implementation to javafx.fxml;
    exports com.example.demo.implementation;
}
