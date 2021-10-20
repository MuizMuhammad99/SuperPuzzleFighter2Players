package com.game.puzzlefighter.characters;

import com.game.animation.Animation;

/**
 * Ryu Character
 */
public class Ryu extends Character {

    public Ryu(int x, int y) {
        super("ryu", x, y);

        idle = new Animation("ryuIdle", 0.25f);
        advantage = new Animation("ryuAdvantage", 0.15f);
        disAdvantage = new Animation("ryuDisAdvantage", 0.15f);
        win = new Animation("ryuWin", 0.15f);
        lose = new Animation("ryuLose", 0.15f);

        currentAnimation = idle;

    }

    @Override
    public void update(float delta) {
        if (currentAnimation.isComplete() && currentAnimation != win && currentAnimation != lose)
            idle();
        currentAnimation.update(delta);
    }


}
