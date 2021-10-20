package com.game.puzzlefighter.characters;

import java.awt.Graphics2D;

import com.game.animation.Animation;
import com.game.sfx.SoundPlayer;

/**
 * A character in game which has different animations for different states.
 *
 */
public abstract class Character {

	protected int x, y;
	protected String name;
	protected int scale = 2;
	protected Animation idle, advantage, disAdvantage, win, lose;
	protected Animation currentAnimation;
	protected SoundPlayer soundPlayer;

	/**
	 * Constructor
	 * @param name name of character
	 * @param x	x
	 * @param y	y
	 */
	public Character(String name,int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
		soundPlayer = new SoundPlayer();
	}

	public abstract void update(float delta);

	public void render(Graphics2D g2) {
		g2.drawImage(currentAnimation.getCurrentFrame(),x,y,currentAnimation.getCurrentFrame().getWidth() * scale,
				currentAnimation.getCurrentFrame().getHeight() * scale,null);
	}

	/**
	 * idle animation
	 */
	public void idle() {
		currentAnimation = idle;
		resetAnimation();
	}

	/**
	 * advantage animation
	 */
	public void advantage() {
		if (currentAnimation != idle)
			return;

		currentAnimation = advantage;
		soundPlayer.playSoundEffect(name + "Advantage");
		resetAnimation();
	}

	/**
	 * disadvantage animation
	 */
	public void disAdvantage() {
		if (currentAnimation != idle)
			return;
		currentAnimation = disAdvantage;
		resetAnimation();
	}

	/**
	 * win animation
	 */
	public void win() {
		currentAnimation = win;
		soundPlayer.playSoundEffect(name + "Win");
		resetAnimation();
	}

	/**
	 * lose animation
	 */
	public void lose() {
		currentAnimation = lose;
		resetAnimation();
	}

	/**
	 * resets all animations
	 */
	private void resetAnimation() {
		idle.reset();
		advantage.reset();
		disAdvantage.reset();
		win.reset();
		lose.reset();
	}
}
