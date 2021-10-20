package com.game.puzzlefighter.characters;

import com.game.animation.Animation;

/**
 * ken character
 *
 */
public class Ken extends Character {

	public Ken(int x, int y) {
		super("ken",x, y);

		idle = new Animation("kenIdle", 0.25f);
		advantage = new Animation("kenAdvantage", 0.15f);
		disAdvantage = new Animation("kenDisAdvantage", 0.15f);
		win = new Animation("kenWin", 0.15f);
		lose = new Animation("kenLose", 0.15f);
		win.setRestartAble(false);

		currentAnimation = idle;

	}

	@Override
	public void update(float delta) {
		if (currentAnimation.isComplete() && currentAnimation != win && currentAnimation != lose)
			idle();
		currentAnimation.update(delta);
	}

	
}
