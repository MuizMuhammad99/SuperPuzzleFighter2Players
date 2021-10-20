package com.game.puzzlefighter.ui;

import java.awt.Graphics2D;

/**
 * An abstract text animation
 *
 */
public abstract class TextAnimation {

	protected float x,y;
	
	public TextAnimation(float x,float y) {
		this.x = x;
		this.y = y;
	}
	
	public abstract void update(float delta);
	public abstract void render(Graphics2D g2);
}
