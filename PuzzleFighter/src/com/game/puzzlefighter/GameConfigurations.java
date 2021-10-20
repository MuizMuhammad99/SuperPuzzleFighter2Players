package com.game.puzzlefighter;

/**
 * Configurations for the game
 *
 */
public class GameConfigurations {

	private int gameWidth, gameHeight;
	private boolean fullScreen;
    private String title;
    private int fps = 60;
    private boolean isScaling;
	private boolean resizeable;
	
	//getters and setters
	
	public int getGameWidth() {
		return gameWidth;
	}

	public void setGameWidth(int gameWidth) {
		this.gameWidth = gameWidth;
	}

	public int getGameHeight() {
		return gameHeight;
	}

	public void setGameHeight(int gameHeight) {
		this.gameHeight = gameHeight;
	}

	public boolean fullScreen() {
		return fullScreen;
	}

	public void setFullScreen(boolean fullScreen) {
		this.fullScreen = fullScreen;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getFps() {
		return fps;
	}
	
	public void setFps(int fps) {
		this.fps = fps;
	}

	public boolean isScaling() {
		return isScaling;
	}
	
	public void setScaling(boolean s) {
		isScaling = s;
	}

	public void setResizeable(boolean r) {
		resizeable = r;
	}
	public boolean resizeable() {
		return resizeable;
	}

}
