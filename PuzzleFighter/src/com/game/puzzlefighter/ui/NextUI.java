package com.game.puzzlefighter.ui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.game.gfx.AssetManager;
import com.game.puzzlefighter.board.Gem;
import com.game.puzzlefighter.board.GemPair;

/**
 * Next Gem ui
 *
 */
public class NextUI {

	
	private final int x;
    private final int y;
	private final GemPair gemPair;
	private final int gemX;
    private final int gemY;
	private final BufferedImage nextUIImage;
	private final int width;
    private final int height;
	private BufferedImage nextGemImage, nextMiddleImage;

	/**
	 * Constructor
	 * @param gemPair	the gem pair
	 * @param nextUIImageKey	image key
	 * @param x	x
	 * @param y	y
	 */
	public NextUI(GemPair gemPair, String nextUIImageKey, int x, int y) {
		this.x = x;
		this.y = y;
		this.gemPair = gemPair;
		this.nextUIImage = AssetManager.getImage(nextUIImageKey);
		width = nextUIImage.getWidth();
		height = nextUIImage.getHeight();
		
		gemX = x + 15;
		gemY = y + 30;
	}

	public void update() {
		nextGemImage = gemPair.getNextGem().getImage();
		nextMiddleImage = gemPair.getNextMiddle().getImage();
	}

	public void render(Graphics2D g2) {
		g2.drawImage(nextUIImage, x, y,width * 2 + 10, height * 2 + 20,null);
		g2.drawImage(nextGemImage, gemX, gemY, Gem.WIDTH, Gem.HEIGHT, null);
		g2.drawImage(nextMiddleImage, gemX, gemY + Gem.HEIGHT, Gem.WIDTH, Gem.HEIGHT, null);
	}
}
