package com.game.puzzlefighter.states;

import java.awt.Color;
import java.awt.Graphics2D;

import com.game.puzzlefighter.Game;
import com.game.puzzlefighter.PuzzleFighter;

/**
 * Transition state
 * it gives a visual transision between two states.
 */
public class TransitionState extends State {
	private State from;
	private State to;

	private enum STATE {
		IN, OUT;
	}

	private STATE state;

	private float timer;
	private float TRANS_TIME = 0.8f;

	private float START_ALPHA = 0f;
	private float END_ALPHA = 1f;
	private float alpha = 0f;

	public TransitionState(PuzzleFighter game) {
		super(game);
	}

	@Override
	public void update(float delta) {

		//update
		timer += delta;
		if (timer > TRANS_TIME) {
			timer = 0f;
			if (state == STATE.IN) {
				state = STATE.OUT;
			} else if (state == STATE.OUT) {
				game.setState(to);
			}

		}

		//update from
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
	 * @param from	from state
	 * @param to to state
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
	 * applys interpolation to the values
	 */
	private float apply(float a, float b, float p) {
		return (b - a) * p + a;
	}

}