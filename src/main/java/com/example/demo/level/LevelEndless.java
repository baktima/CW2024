package com.example.demo.level;

public class LevelEndless extends LevelThree{
    public LevelEndless(double screenHeight, double screenWidth) {
        super(screenHeight, screenWidth);
    }
    @Override
    protected void checkIfGameOver(){
        if (getUserIsDestroyed()) {
            loseGame();
        }
    }
}
