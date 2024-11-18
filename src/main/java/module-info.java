module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    //added javafx media in here and the dependencies and then need to also reload the maven, so it can be used.
    requires javafx.media;


    opens com.example.demo to javafx.fxml;
    
 // Export your packages to JavaFX, so it can access @FXML methods
    opens com.example.demo.controller to javafx.fxml;
    exports com.example.demo;
    exports com.example.demo.controller;
}