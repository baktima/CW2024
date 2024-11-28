package com.example.demo.level.levelView;

import com.example.demo.display.GameOverImage;
import com.example.demo.display.GameOverMenu;
import com.example.demo.display.HeartDisplay;
import com.example.demo.display.WinImage;
import javafx.scene.Group;

public class LevelView {
	
	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 25;
	private static final int WIN_IMAGE_X_POSITION = 355;
	private static final int WIN_IMAGE_Y_POSITION = 175;

	//CHANGING THE WEIRD CONSTANT
	private static final int LOSS_SCREEN_X_POSITION = 355;
	private static final int LOSS_SCREEN_Y_POSITION = 175;
	private final Group root;
	private final WinImage winImage;
	private final GameOverImage gameOverImage;
	private final HeartDisplay heartDisplay;
	private final GameOverMenu gameOverMenu;
	
	public LevelView(Group root, int heartsToDisplay) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);

		winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
		gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSITION);

		gameOverMenu = new GameOverMenu(LOSS_SCREEN_X_POSITION,LOSS_SCREEN_Y_POSITION);
	}
	
	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}

	public void showWinImage() {
		root.getChildren().add(winImage);
		winImage.showWinImage();
	}
	
	public void showGameOverImage() {
		root.getChildren().add(gameOverMenu);

		System.out.println("showing");
	}

	public void removeWinImage(){
		winImage.hideWinImage();
		root.getChildren().remove(winImage);
	}

	public void removeGameOverImage(){
		root.getChildren().remove(gameOverMenu);
		System.out.println("removing");
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