package com.game.puzzlefighter.ui;

import com.game.animation.Animation;
import com.game.gfx.AssetManager;

import java.awt.*;

/**
 * ko animation.
 * this animation is displayed at the center of screen and pauses for a little too before going away.
 */
public class KOAnimation extends TextAnimation {

    public static final int IN = 1;
    public static final int OUT = 2;

    private final Animation koAnimation;
    private final int width;
    private final int height;
    private int state = 1;
    private boolean isStarted;
    private boolean isFinished;
    private float pauseCounter;

    /**
     * Constructor
     *
     * @param x     x
     * @param y     y
     * @param scale scale
     */
    public KOAnimation(float x, float y, float scale) {
        super(x, y);

        koAnimation = new Animation(AssetManager.getFrames("ko"), 0.06f);
        koAnimation.setReverseAble(true);

        width = (int) (koAnimation.getCurrentFrame().getWidth() * scale);
        height = (int) (koAnimation.getCurrentFrame().getHeight() * scale);
    }

    @Override
    public void update(float delta) {
        if (isFinished || !isStarted)
            return;

        switch (state) {
            case IN:

                //pause if animation is complete
                if (koAnimation.isComplete()) {
                    pauseCounter += delta;
                    float pause = 2;
                    if (pauseCounter >= pause) {
                        pauseCounter = 0;
                        state = OUT;
                    }
                } else
                    koAnimation.update(delta);

                break;
            case OUT:

                //animation is finished if it returns to first index
                if (koAnimation.getIndex() == 0) {
                    isFinished = true;
                }
                koAnimation.update(delta);
                break;
        }
    }

    @Override
    public void render(Graphics2D g2) {
        if (isFinished || !isStarted)
            return;

        g2.drawImage(koAnimation.getCurrentFrame(), (int) (x - width / 2), (int) (y - height / 2), width, height, null);
    }

    public void start() {
        isStarted = true;
    }

    public boolean isFinished() {
        return isFinished;
    }

}
