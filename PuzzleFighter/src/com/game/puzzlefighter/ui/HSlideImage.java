package com.game.puzzlefighter.ui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.game.gfx.AssetManager;
import com.game.puzzlefighter.Game;

/**
 * A Horizontal Sliding Image which slides from left to right and pauses at center as well.
 * 
 **/
public class HSlideImage extends TextAnimation{

	private BufferedImage image;
	private int width,height;

	private float slidingSpeed;
	private boolean slideDone;
	private boolean atCenter;
	private float atCenterPause = 1f;
	private float atCenterPauseCount;
	
	/**
	 * Constructor
	 * @param dx	delta x
	 * @param y	y
	 * @param slidingSpeed	sliding speed
	 * @param atCenterPause	pause at center
	 * @param scale	image scale
	 * @param imageKey	key of image
	 */
	public HSlideImage(float dx, float y, float slidingSpeed,float atCenterPause,float scale, String imageKey) {
		super(0,0);
		this.x = 0-dx;
		this.y = y;
		this.slidingSpeed = slidingSpeed;
		this.atCenterPause = atCenterPause;
		this.image = AssetManager.getImage(imageKey);
		this.width = (int) (image.getWidth() * scale);
		this.height = (int) (image.getHeight() * scale);
	}

	public void update(float delta) {
		if (slideDone)
			return;

		//at center delay
		if (atCenter) {
			atCenterPauseCount += delta;
			if(atCenterPauseCount > atCenterPause)
				atCenter = false;
		} else
			x += slidingSpeed;//slide image

		//if is at center
		if (!atCenter && atCenterPauseCount < atCenterPause && x > ((Game.getGameWidth()/ 2) -((width)/2)) )
			atCenter = true;

		//if outside game screen
		if (x > Game.getGameWidth())
			slideDone = true;

	}

	public void render(Graphics2D g2) {
		if (slideDone)
			return;

		g2.drawImage(image, (int) x, (int) y, width, height, null);
	}
	
	//getters

	public boolean isSlideDone() {
		return slideDone;
	}
	
	public boolean isAtCenter() {
		return atCenter;
	}
	
	public int getHeight() {
		return height;
	}

}
