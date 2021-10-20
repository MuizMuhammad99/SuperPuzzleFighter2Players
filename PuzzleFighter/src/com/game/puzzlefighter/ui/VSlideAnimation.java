package com.game.puzzlefighter.ui;

import com.game.animation.Animation;
import com.game.puzzlefighter.Game;

import java.awt.*;

/**
 * A vertical sliding image animation
 * it slides the animation from top to bottom and also updates the animation image as well.
 */
public class VSlideAnimation extends TextAnimation {

    private final Animation animation;
    private final float slidingSpeed;
    private final float atCenterPause;
    private final int width;
    private final int height;

    private boolean slideDone;
    private boolean atCenter;
    private float atCenterPauseCount;

    /**
     * Constructor
     *
     * @param x             x
     * @param dy            delta y
     * @param slidingSpeed  sliding speed
     * @param atCenterPause pause at center
     * @param scale         scale
     * @param animationKey  animation key
     */
    public VSlideAnimation(float x, float dy, float slidingSpeed, float atCenterPause, float scale, String animationKey) {
        super(0, 0);
        this.x = x;
        this.y = -dy;
        this.slidingSpeed = slidingSpeed;
        this.atCenterPause = atCenterPause;
        this.animation = new Animation(animationKey, 0.25f);
        animation.setRestartAble(false);
        this.width = (int) (animation.getCurrentFrame().getWidth() * scale);
        this.height = (int) (animation.getCurrentFrame().getHeight() * scale);

    }

    public void update(float delta) {
        if (slideDone)
            return;

        animation.update(delta);

        //pause at center
        if (atCenter) {
            atCenterPauseCount += delta;
            if (atCenterPauseCount > atCenterPause)
                atCenter = false;
        } else
            y += slidingSpeed;//slide image

        //if at center
        if (!atCenter && atCenterPauseCount < atCenterPause && y > ((Game.getGameHeight() / 2) - ((height) / 2)))
            atCenter = true;

        //if outside screen, then slide is done
        if (y > Game.getGameHeight())
            slideDone = true;

    }

    public void render(Graphics2D g2) {
        if (slideDone)
            return;

        g2.drawImage(animation.getCurrentFrame(), (int) x, (int) y, width, height, null);
    }

    //getters

    public boolean isSlideDone() {
        return slideDone;
    }

    public void setX(int x) {
        this.x = x;
    }

}
