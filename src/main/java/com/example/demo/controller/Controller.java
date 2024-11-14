package com.example.demo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.Scene;
import javafx.scene.Parent; 
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;

//testing
import javafx.animation.Timeline;

import com.example.demo.LevelParent;
//import com.example.demo.PauseMenu; 

public class Controller implements Observer {

	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.LevelTwo";
	private final Stage stage;
	private static Stage pauseMenu; 

	//testing
	private static Controller instance; 
	private boolean isPaused = false;
	
	
	
	public Controller(Stage stage) {
		this.stage = stage;
	}
	

	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  {

			stage.show();
			goToLevel(LEVEL_ONE_CLASS_NAME);
	}

	public void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			Class<?> myClass = Class.forName(className);
			Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
			LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());
			myLevel.addObserver(this);
			Scene scene = myLevel.initializeScene();
			stage.setScene(scene);
			myLevel.startGame();

	}
	
	
	
	//maybe the update is wrong i need to change it first to know the root cause 

//	@Override
//	public void update(Observable arg0, Object arg1) {
//		try {
//			goToLevel((String) arg1);
//		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
//				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//			Alert alert = new Alert(AlertType.ERROR);
//			alert.setContentText(e.getClass().toString());
//			alert.show();
//		}
//	}
	
	//the changes is working 
	/*adding some changes to check the size of the heap for this particular project	
	 * */
	public void update(Observable arg0, Object arg1) {
	    try {
	        goToLevel((String) arg1);
	    } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
	            | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
	        
	        // Capture the stack trace as a string
	    	e.getCause().printStackTrace();
	    	System.out.println("Classpath: " + System.getProperty("java.class.path"));
	    	
	    	long totalHeapSize = Runtime.getRuntime().totalMemory();

	        // Maximum heap size (maximum memory that can be allocated to the JVM)
	        long maxHeapSize = Runtime.getRuntime().maxMemory();

	        // Free heap size (available memory in the current heap)
	        long freeHeapSize = Runtime.getRuntime().freeMemory();

	        System.out.println("Total Heap Size (MB): " + (totalHeapSize / (1024 * 1024)) + " MB");
	        System.out.println("Max Heap Size (MB): " + (maxHeapSize / (1024 * 1024)) + " MB");
	        System.out.println("Free Heap Size (MB): " + (freeHeapSize / (1024 * 1024)) + " MB");
	        
	        // Stop the program with a non-zero status to indicate an error
	        System.exit(1);
	    }
	}


}
