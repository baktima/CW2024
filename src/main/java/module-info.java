module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    //added javafx media in here and the dependencies and then need to also reload the maven, so it can be used.
    requires javafx.media;
    requires java.logging;


    opens com.example.demo to javafx.fxml;
    
 // Export your packages to JavaFX, so it can access @FXML methods
    opens com.example.demo.controller to javafx.fxml;
    exports com.example.demo;
    exports com.example.demo.controller;
    exports com.example.demo.actor;
    opens com.example.demo.actor to javafx.fxml;
    exports com.example.demo.plane;
    opens com.example.demo.plane to javafx.fxml;
    exports com.example.demo.projectile;
    opens com.example.demo.projectile to javafx.fxml;
    exports com.example.demo.level;
    opens com.example.demo.level to javafx.fxml;
    exports com.example.demo.display;
    opens com.example.demo.display to javafx.fxml;
    exports com.example.demo.level.levelView;
    opens com.example.demo.level.levelView to javafx.fxml;
    exports com.example.demo.sound;
    opens com.example.demo.sound to javafx.fxml;
}