package com.game.puzzlefighter.states;

import com.game.puzzlefighter.PuzzleFighter;

import java.awt.*;

/**
 * An abstract state of game
 */
public abstract class State {

    protected final PuzzleFighter game;

    public State(PuzzleFighter game) {
        this.game = game;
    }

    public abstract void update(float delta);

    public abstract void render(Graphics2D g2);


}
