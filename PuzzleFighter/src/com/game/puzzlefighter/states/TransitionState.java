package com.game.puzzlefighter.states;

import com.game.puzzlefighter.Game;
import com.game.puzzlefighter.PuzzleFighter;

import java.awt.*;

/**
 * Transition state
 * it gives a visual transition between two states.
 */
public class TransitionState extends State {
    private State from;
    private State to;
    private STATE state;
    private float timer;
    private float alpha = 0f;

    public TransitionState(PuzzleFighter game) {
        super(game);
    }

    @Override
    public void update(float delta) {

        //update
        timer += delta;
        float TRANS_TIME = 0.8f;
        if (timer > TRANS_TIME) {
            timer = 0f;
            if (state == STATE.IN) {
                state = STATE.OUT;
            } else if (state == STATE.OUT) {
                game.setState(to);
            }

        }

        //update from
        float START_ALPHA = 0f;
        float END_ALPHA = 1f;
        if (state == STATE.IN) {
            alpha = apply(START_ALPHA, END_ALPHA, timer / TRANS_TIME);
            from.update(delta);

        }

        //update to
        if (state == STATE.OUT) {
            alpha = apply(END_ALPHA, START_ALPHA, timer / TRANS_TIME);
            to.update(delta);

        }

    }

    @Override
    public void render(Graphics2D g2) {

        if (state == STATE.IN)
            from.render(g2);
        else if (state == STATE.OUT)
            to.render(g2);

        g2.setColor(new Color(0, 0, 0, alpha));
        g2.fillRect(0, 0, Game.getGameWidth(), Game.getGameHeight());

    }

    /**
     * start transition
     *
     * @param from from state
     * @param to   to state
     */
    public void start(State from, State to) {
        this.from = from;
        this.to = to;
        state = STATE.IN;
        alpha = 0f;
        timer = 0;
        game.setState(this);

    }

    /**
     * applies interpolation to the values
     */
    private float apply(float a, float b, float p) {
        return (b - a) * p + a;
    }

    private enum STATE {
        IN, OUT
    }

}