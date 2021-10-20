package com.game.puzzlefighter.board;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.game.animation.Animation;
import com.game.gfx.AssetManager;
import com.game.puzzlefighter.Game;

/**
 * Defines all types of gems in the game.
 *	Counter, Normal, Diamond, and crash gems of all color types.
 */
public class Gem {

	// color
	public static final int BLUE = 1;
	public static final int RED = 2;
	public static final int GREEN = 3;
	public static final int YELLOW = 4;

	// type
	public static final int NORMAL = 1;
	public static final int CRASH = 2;
	public static final int COUNTER = 3;
	public static final int DIAMOND = 4;

	// state
	public static final int MOVING = 1;
	public static final int IDLE = 2;
	public static final int DESTROY = 3;

	// dimensions
	public static final int WIDTH = 40;
	public static final int HEIGHT = 40;

	private int gemColor;
	private int type;
	private int state = MOVING;
	private Block block;

	private int countTimer = 5;

	private float x, y;
	private float normalSpeed = 1f;
	private float fastSpeed = 8f;
	private float speed = normalSpeed;

	private Animation destroyAnimation, diamondAnimation, counterAnimation, crashAnimation, blockAnimation;
	private BufferedImage gemImage, image;

	/**
	 * Constructor
	 * @param gemColor	color of gem
	 * @param type	type of gem
	 */
	public Gem(int gemColor, int type) {
		this.gemColor = gemColor;
		this.type = type;
		loadGemImage();
		loadAnimations();
		updateImage(0);
	}

	public void update(float delta) {
		switch (state) {
		case MOVING:
			y += speed;
			break;
		}

		// set image
		updateImage(delta);

	}

	public void render(Graphics2D g2, int xOffset, int yOffset) {
		if (state == DESTROY)
			g2.drawImage(image, (int) (x - WIDTH / 2) + xOffset, (int) (y + HEIGHT / 2) + yOffset, WIDTH * 2,
					HEIGHT * 2, null);
		else
			g2.drawImage(image, (int) x + xOffset, (int) y + yOffset, WIDTH, HEIGHT, null);
	}
	

	/**
	 * loads all gem images
	 */
	private void loadGemImage() {
		switch (gemColor) {
		case BLUE:
			gemImage = AssetManager.getImage("blueGem");
			break;
		case GREEN:
			gemImage = AssetManager.getImage("greenGem");
			break;
		case RED:
			gemImage = AssetManager.getImage("redGem");
			break;
		case YELLOW:
			gemImage = AssetManager.getImage("yellowGem");
			break;
		}
	}
	
	/**
	 * update the image of gem
	 */
	private void updateImage(float delta) {
		if (state == DESTROY) {
			destroyAnimation.update(delta);
			image = destroyAnimation.getCurrentFrame();
		} else if (type == COUNTER) {
			image = counterAnimation.getFrames()[countTimer];
		} else if (type == DIAMOND) {
			diamondAnimation.update(delta);
			image = diamondAnimation.getCurrentFrame();
		} else if (type == CRASH) {
			crashAnimation.update(delta);
			image = crashAnimation.getCurrentFrame();
		} else if (block != null) {
			blockAnimation.update(delta);
			image = blockAnimation.getCurrentFrame();

		} else
			image = gemImage;
	}

	/**
	 * loads the animations
	 */
	private void loadAnimations() {
		destroyAnimation = new Animation("destroy" + gemColor, 0.05f);
		destroyAnimation.setRestartAble(false);
		diamondAnimation = new Animation("diamond" + gemColor, 0.15f);
		counterAnimation = new Animation("counter" + gemColor, 0.15f);
		crashAnimation = new Animation("crash" + gemColor, 0.15f);
		blockAnimation = new Animation("", 0.25f);
	}

	/**
	 * 
	 * @return	random gem
	 */
	public static Gem randomGem() {
		Random random = new Random();
		int color = random.nextInt(4) + 1;
		int type = 0;
		int chance = random.nextInt(100) + 1;

		//chance for the gem to spawn of certain type
		if (chance <= 80)
			type = NORMAL;
		else if (chance <= 95)
			type = CRASH;
		else
			type = DIAMOND;

		Gem gem = new Gem(color, type);
		return gem;
	}

	/**
	 * ticks the counter timer
	 */
	public void tick() {
		countTimer--;
		if (countTimer < 0) {
			countTimer = 0;
			type = NORMAL;
			Game.soundPlayer.playSoundEffect2("counterToNormalGem");
		}
	}

	//getters and setters
	
	public void setBlockFrames(String frameKey) {
		blockAnimation.setFrames(frameKey);
	}

	public boolean isBlockGem() {
		return block != null;
	}

	public boolean isCounterGem() {
		return type == COUNTER;
	}

	public boolean isCrashGem() {
		return type == CRASH;
	}

	public boolean isDiamondGem() {
		return type == DIAMOND;
	}

	public void fastSpeed() {
		speed = fastSpeed;
	}

	public void normalSpeed() {
		speed = normalSpeed;
	}

	public boolean isFastSpeed() {
		return speed == fastSpeed;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void setLocation(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public int getType() {
		return type;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getGemColor() {
		return gemColor;
	}

	public boolean isNormalGem() {
		return type == NORMAL;
	}

	public boolean isDestroyed() {
		return state == DESTROY && destroyAnimation.isComplete();
	}

	public boolean isFalling() {
		return state == MOVING;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public Block getBlock() {
		return block;
	}

	public void destroy() {
		state = DESTROY;

	}
	
	public BufferedImage getGemImage() {
		return gemImage;
	}
	
	public BufferedImage getImage() {
		return image;
	}

	public Animation getBlockAnimation() {
		return blockAnimation;
	}

	public void setType(int type) {
		this.type = type;

	}

}
