package com.example.demo.level.levelView;

import com.example.demo.display.*;
import javafx.scene.Group;

public class LevelView {
	
	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 25;
	private static final int WIN_IMAGE_X_POSITION = 355;
	private static final int WIN_IMAGE_Y_POSITION = 175;
	private static final int LOSS_SCREEN_X_POSITION = 355;
	private static final int LOSS_SCREEN_Y_POSITION = 175;
	private final Group root;
	private final HeartDisplay heartDisplay;
	private final GameOverMenu gameOverMenu;
	private final WinMenu winMenu;
	
	public LevelView(Group root, int heartsToDisplay) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);

		winMenu = new WinMenu(WIN_IMAGE_X_POSITION,WIN_IMAGE_Y_POSITION);
		gameOverMenu = new GameOverMenu(LOSS_SCREEN_X_POSITION,LOSS_SCREEN_Y_POSITION);
	}
	
	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}

	public void showWinImage() {
		root.getChildren().add(winMenu);
		winMenu.showWinImage();
	}
	
	public void showGameOverImage() {
		root.getChildren().add(gameOverMenu);
	}

	public void removeWinImage(){
		winMenu.hideWinImage();
		root.getChildren().remove(winMenu);
	}

	public void removeGameOverImage(){
		root.getChildren().remove(gameOverMenu);
	}
	
	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

	public void resetHeartDisplay(){

		//use getter for the total of heart not like this
		heartDisplay.getContainer().getChildren().clear();

		heartDisplay.setNumberOfHeartsToDisplay(5);
		heartDisplay.initializeHearts();

	}

}