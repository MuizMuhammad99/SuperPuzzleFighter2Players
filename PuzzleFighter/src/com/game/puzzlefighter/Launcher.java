
package com.game.puzzlefighter;

/**
 * Game launcher Class
 *
 */
public final class Launcher {

	/**
	 * Initializes the game with configurations and runs it
	 * 
	 * @param config configurations of game
	 * @param game   the game
	 */
	public static final void launch(GameConfigurations config, Game game) {
		game.initialize(config);
		game.start();
	}
}
