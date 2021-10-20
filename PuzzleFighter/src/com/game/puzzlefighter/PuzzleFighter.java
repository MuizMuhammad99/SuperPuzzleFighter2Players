package com.game.puzzlefighter;

import com.game.gfx.AssetManager;
import com.game.puzzlefighter.states.GameState;
import com.game.puzzlefighter.states.MenuState;
import com.game.puzzlefighter.states.State;
import com.game.puzzlefighter.states.TransitionState;

/**
 * Puzzle fighter game
 *
 */
public class PuzzleFighter extends Game {

	private MenuState menuState;
	private GameState gameState;
	private TransitionState transitionState;
	private State state;

	@Override
	public void create() {
		AssetManager.load();

		menuState = new MenuState(this);
		transitionState = new TransitionState(this);
		setState(menuState);
	}

	@Override
	public void update(float delta) {
		state.update(delta);

	}

	@Override
	public void render() {
		state.render(graphics);
	}

	public void setState(State state) {
		this.state = state;

	}

	//transitions to menu state
	public void transitionToMenu() {
		Game.soundPlayer.playBackGroundMusic("menuBGM");
		transitionState.start(gameState, menuState);
		setState(transitionState);
	}
	
	//transitions to gamestate
	public void startGame() {
		gameState = new GameState(this);
		transitionState.start(menuState, gameState);
		setState(transitionState);
	}

}
