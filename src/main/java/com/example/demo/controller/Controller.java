package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.example.demo.implementation.LevelChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import com.example.demo.level.LevelParent;

public record Controller(Stage stage) implements LevelChangeListener {

	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.level.LevelOne";

	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		goToLevel(LEVEL_ONE_CLASS_NAME);
	}

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
