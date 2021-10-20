package com.game.puzzlefighter.ui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.game.gfx.AssetManager;

/**
 * Score Display ui
 *
 */
public class ScoreUI {

	private final int x;
	private final int y;
	private final int scoreX;
	private final int scoreY;
	private final float scale = 2.5f;
	private final BufferedImage scoreUIImage;
	private final String scoreColor;

	private final int[] scoreDigits;
	private int score;

	/**
	 * Constructor
	 * @param scoreUIKey	image key
	 * @param scoreColor	color of digits
	 * @param x	x
	 * @param y	y
	 */
	public ScoreUI(String scoreUIKey, String scoreColor, int x, int y) {
		this.x = x;
		this.y = y;
		scoreUIImage = AssetManager.getImage(scoreUIKey);
		this.scoreColor = scoreColor;

		scoreX = x + 5;
		scoreY = y + 35;
		scoreDigits = new int[8];
	}


	/**
	 * update score
	 * @param score	new score
	 */
	public void updateScore(int score) {
		this.score = score;
		updateScoreDigits();
	}

	public void render(Graphics2D g2) {

		g2.drawImage(scoreUIImage, x, y,(int)( scoreUIImage.getWidth() * scale) + 10, (int)(scoreUIImage.getHeight() * scale), null);
		drawScore(g2);
	}

	/**
	 * update score digits
	 */
	private void updateScoreDigits() {
		int temp = score;
		int i = scoreDigits.length - 1;
		while (temp > 0) {
			scoreDigits[i--] = temp % 10;
			temp /= 10;
		}
	}

	/**
	 * draws score by drawing the digit image
	 * @param g2	graphics object
	 */
	private void drawScore(Graphics2D g2) {
		BufferedImage image;
		int x = scoreX;
		int width = 0, height = 0;

		for (int i = 0; i < scoreDigits.length; i++) {
			image = getDigitImage(scoreDigits[i]);
			width = (int) (image.getWidth() * scale);
			height = (int) (image.getHeight() * scale);
			g2.drawImage(image, x, scoreY, width, height, null);

			x += width;
		}
	}

	private BufferedImage getDigitImage(int i) {
		return AssetManager.getImage(scoreColor + i);
	}
}
