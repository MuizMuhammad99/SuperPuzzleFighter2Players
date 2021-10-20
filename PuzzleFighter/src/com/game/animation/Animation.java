package com.game.animation;

import java.awt.image.BufferedImage;

import com.game.gfx.AssetManager;

/**
 * Animation Class
 * Defines an animation object which contains frame images.
 * There is a certain delay between each frame.
 * The animation can be restart-able or reverse-able as well.
 */
public class Animation {

	private BufferedImage[] frames;
	private int index;
	private final float frameDelay;
	private float timer;

	private boolean restartAble = true;
	private boolean reverseAble;
	private boolean reverseMode;

	/**
	 * Constructor 1
	 * @param frames	frames
	 * @param frameDelay	frame delay
	 */
	public Animation(BufferedImage[] frames, float frameDelay) {
		this.frames = frames;
		index = 0;
		this.frameDelay = frameDelay;
	}

	/**
	 * Constructor 2
	 * @param framesKey	key of frames
	 * @param frameDelay	frame delay
	 */
	public Animation(String framesKey, float frameDelay) {
		this(AssetManager.getFrames(framesKey), frameDelay);
	}

	public void update(float delta) {
		
		//update frame timer
		timer += delta;
		if (timer >= frameDelay) {
			int step = 1;
			index += reverseMode ? -step : step;
			timer = 0;
		}

		//if at last index,then
		if (index >= frames.length) {
			if (restartAble) {
				if (reverseAble) {
					index = frames.length - 1;
					reverseMode = true;
				}
				else
					index = 0;

			} else
				index = frames.length - 1;
		}
		
		//if index < 0,then
		if(index < 0) {
			index = 0;
			reverseMode = false;
		}
	}
	
	/**
	 * Copies the state of other animation
	 * @param animation	the other animation
	 */
	public void copyState(Animation animation) {
		timer = animation.getTimer();
		index = animation.getIndex();
	}

	//getters and setters
	
	public void setRestartAble(boolean restartable) {
		this.restartAble = restartable;
	}

	public void reset() {
		index = 0;
		timer = 0;
	}

	public int getIndex() {
		return index;
	}

	public BufferedImage getCurrentFrame() {
		return frames[index];
	}

	public BufferedImage[] getFrames() {
		return this.frames;
	}

	public boolean isAtLastIndex() {
		return index == frames.length - 1;
	}

	public boolean isComplete() {
		return isAtLastIndex();
	}


	public float getTimer() {
		return timer;
	}

	public void setReverseAble(boolean reverseAble) {
		this.reverseAble = reverseAble;
		
	}

	public void setFrames(String frameKey) {
		this.frames = AssetManager.getFrames(frameKey);
		
	}

}
