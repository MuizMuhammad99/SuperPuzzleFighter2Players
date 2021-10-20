package com.game.puzzlefighter.states;

import java.awt.Graphics2D;

import com.game.puzzlefighter.PuzzleFighter;

/**
 * An abstract state of game
 *
 */
public abstract class State {

	protected PuzzleFighter game;
	
	public State(PuzzleFighter game) {
		this.game = game;
	}
	
	public abstract void update(float delta);
	public abstract void render(Graphics2D g2);

	public PuzzleFighter getGame() {
		return game;
	}
	

	
}
