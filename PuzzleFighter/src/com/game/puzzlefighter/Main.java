package com.game.puzzlefighter;

/**
 * Main runner class of game
 *
 */
public class Main {

	public static void main(String[] args) {
		GameConfigurations gc = new GameConfigurations();
		gc.setTitle("Puzzle Fighter Turbo");
		gc.setGameHeight(528);
		gc.setGameWidth(992);
		gc.setScaling(true);
		gc.setResizeable(true);
		
		PuzzleFighter game = new PuzzleFighter();		
		Launcher.launch(gc, game);
	}

}
