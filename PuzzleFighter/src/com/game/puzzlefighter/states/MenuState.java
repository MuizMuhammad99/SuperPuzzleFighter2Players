package com.game.puzzlefighter.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.game.animation.Animation;
import com.game.puzzlefighter.Game;
import com.game.puzzlefighter.PuzzleFighter;
import com.game.utils.FontUtils;

/**
 * Menu screen
 *
 */
public class MenuState extends State {

	private int titleX, titleY;
	private Animation titleAnimation;
	private int width, height;
	private Color backgroundColor = Color.PINK;

	private Font font = new Font("Monospaced", Font.BOLD, 20);
	private float delayCount;
	private boolean blink = true;

	/**
	 * Constructor
	 * 
	 * @param game game
	 */
	public MenuState(PuzzleFighter game) {
		super(game);

		// title animation
		titleAnimation = new Animation("title", 0.55f);
		width = titleAnimation.getCurrentFrame().getWidth() * 5;
		height = titleAnimation.getCurrentFrame().getHeight() * 5;

		// title x y
		titleX = Game.getGameWidth() / 2 - width / 2;
		titleY = Game.getGameHeight() / 2 - height / 2;

		Game.soundPlayer.playBackGroundMusic("menuBGM");

	}

	@Override
	public void update(float delta) {
		titleAnimation.update(delta);

		// update blink timer
		delayCount += delta;
		float blinkDelay = 0.5f;
		if (delayCount >= blinkDelay) {
			delayCount = 0;
			blink = !blink;
		}

		// start game if any key pressed
		if (Game.keyManager.isAnyKeyJustPressed()) {
			Game.soundPlayer.playSoundEffect("notify");
			game.startGame();

		}
	}

	@Override
	public void render(Graphics2D g2) {
		// background
		g2.setColor(backgroundColor);
		g2.fillRect(0, 0, Game.getGameWidth(), Game.getGameHeight());

		// titile
		g2.drawImage(titleAnimation.getCurrentFrame(), titleX, titleY, width, height, null);

		if (blink)
			return;
		// draw press any msg
		g2.setColor(Color.MAGENTA);
		g2.setFont(font);
		String msg = "Press any key to start..";
		g2.drawString(msg, Game.getGameWidth() / 2 - FontUtils.getTextWidth(font, msg) / 2,
				titleY + height + font.getSize());

	}

}
