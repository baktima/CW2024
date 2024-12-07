package com.example.demo.level.levelViews;

import com.example.demo.display.*;
import com.example.demo.display.menu.GameOverMenu;
import com.example.demo.display.menu.WinMenu;
import javafx.scene.Group;

/**
 * Represents most of the visual components of a game level, including heart displays,
 * win and game over images, and methods for managing and updating these elements.
 * This class interacts with the game's root container to add or remove visual elements.
 */
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

	/**
	 * Constructs a new LevelView instance.
	 *
	 * @param root            The root group where all level visuals are displayed.
	 * @param heartsToDisplay The initial number of hearts to display for the level.
	 */
	public LevelView(Group root, int heartsToDisplay) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);

		winMenu = new WinMenu(WIN_IMAGE_X_POSITION,WIN_IMAGE_Y_POSITION);
		gameOverMenu = new GameOverMenu(LOSS_SCREEN_X_POSITION,LOSS_SCREEN_Y_POSITION);
	}

	/**
	 * Displays the heart container in the game's root group.
	 */
	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}

	/**
	 * Displays the win image in the game's root group.
	 */
	public void showWinImage() {
		root.getChildren().add(winMenu);
		winMenu.showWinImage();
	}

	/**
	 * Displays the game over image in the game's root group.
	 */
	public void showGameOverImage() {
		root.getChildren().add(gameOverMenu);
	}

	/**
	 * Removes the game over image from the game's root group.
	 */
	public void removeGameOverImage(){
		root.getChildren().remove(gameOverMenu);
	}

	/**
	 * Removes hearts from the heart display to reflect the player's remaining health.
	 *
	 * @param heartsRemaining The number of hearts that should remain visible.
	 */
	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

	/**
	 * Resets the heart display to its initial state with all hearts visible.
	 */
	public void resetHeartDisplay(){
		//use getter for the total of heart not like this
		heartDisplay.getContainer().getChildren().clear();

		heartDisplay.setNumberOfHeartsToDisplay(5);
		heartDisplay.initializeHearts();

	}

}