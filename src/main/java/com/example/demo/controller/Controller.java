package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.example.demo.implementation.LevelChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import com.example.demo.level.LevelParent;

/**
 * The main controller class for managing the game's levels and transitions.
 * This class is responsible for initializing and navigating between different game levels.
 */
public record Controller(Stage stage) implements LevelChangeListener {

	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.level.LevelOne";

	/**
	 * Launches the game by starting at the first level.
	 *
	 * @throws ClassNotFoundException       If the specified level class cannot be found.
	 * @throws NoSuchMethodException        If the level class does not have the required constructor.
	 * @throws SecurityException            If there is a security violation while accessing the class or constructor.
	 * @throws InstantiationException       If the level class cannot be instantiated.
	 * @throws IllegalAccessException       If the level class or its constructor is not accessible.
	 * @throws IllegalArgumentException     If invalid arguments are provided to the constructor.
	 * @throws InvocationTargetException    If the constructor invocation results in an exception.
	 */
	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		goToLevel(LEVEL_ONE_CLASS_NAME);
	}

	/**
	 * Navigates to a specific level in the game by dynamically loading its class.
	 *
	 * @param className The fully qualified class name of the level to load.
	 * @throws ClassNotFoundException       If the specified level class cannot be found.
	 * @throws NoSuchMethodException        If the level class does not have the required constructor.
	 * @throws SecurityException            If there is a security violation while accessing the class or constructor.
	 * @throws InstantiationException       If the level class cannot be instantiated.
	 * @throws IllegalAccessException       If the level class or its constructor is not accessible.
	 * @throws IllegalArgumentException     If invalid arguments are provided to the constructor.
	 * @throws InvocationTargetException    If the constructor invocation results in an exception.
	 */
	public void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		Class<?> myClass = Class.forName(className);
		Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
		LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());

		myLevel.setStage(this.stage);
		myLevel.setLevelChangeListener(this);

		Scene scene = myLevel.initializeScene();
		stage.setScene(scene);
		myLevel.startGame();
	}

	/**
	 * Handles level transitions by loading the specified next level.
	 *
	 * @param nextLevelClassName The fully qualified class name of the next level.
	 */
	@Override
	public void levelChange(String nextLevelClassName) {
		try {
			goToLevel(nextLevelClassName);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				 | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText(e.getClass().toString());
			alert.show();
		}
	}
}
