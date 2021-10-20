package com.game.puzzlefighter.board;

import java.awt.Graphics2D;

/**
 * Defines a gem pair which falls down from top to bottom
 * This gem pair can move left or right and it can also rotate left or right as well.
 */
public class GemPair {

	private Gem middle;
	private Gem gem;
	private Gem nextGem, nextMiddle;
	private int angle = 90;

	private final Board board;
	private final int spawnX;
	private final int spawnY;

	/**
	 * Constructor
	 * @param board	the board
	 * @param spawnX	spawn x
	 * @param spawnY	spawn y
	 */
	public GemPair(Board board, int spawnX, int spawnY) {
		this.spawnX = spawnX;
		this.spawnY = spawnY;
		this.board = board;
		randomNextGemPair();
	}


	public void update(float delta) {
		
		//update gem
		if (gem != null) {
			gem.update(delta);

			//if some other gem below,then stop moving
			if (!board.isEmpty(gem.getX(), gem.getY() + Gem.HEIGHT)) {
				gem.setState(Gem.IDLE);
				board.place(gem, gem.getX(), gem.getY());
				gem = null;

				// make the other fast speed
				if (middle != null && middle.isFastSpeed())
					middle.fastSpeed();
			}
		}

		//update the middle gem
		if (middle != null) {
			middle.update(delta);

			//if some other gem below,then stop moving
			if (!board.isEmpty(middle.getX(), middle.getY() + Gem.HEIGHT)) {
				middle.setState(Gem.IDLE);
				board.place(middle, middle.getX(), middle.getY());
				middle = null;

				// make the other fast speed
				if (gem != null && gem.isFastSpeed())
					gem.fastSpeed();
			}
		}
	}

	public void render(Graphics2D g2, int xOffset, int yOffset) {
		if (middle != null)
			middle.render(g2, xOffset, yOffset);

		if (gem != null)
			gem.render(g2, xOffset, yOffset);
	}

	/**
	 * move left
	 */
	public void left() {
		if (gem == null || middle == null)
			return;

		float gemX = gem.getX() - Gem.WIDTH;
		float gemY = gem.getY();
		float middleX = middle.getX() - Gem.WIDTH;
		float middleY = middle.getY();

		//if left side is empty only then move
		if (board.isEmpty(middleX, middleY + Gem.HEIGHT) && board.isEmpty(gemX, gemY + Gem.HEIGHT)) {
			gem.setLocation(gemX, gemY);
			middle.setLocation(middleX, middleY);
		}
	}

	/**
	 * move right
	 */
	public void right() {
		if (gem == null || middle == null)
			return;

		float gemX = gem.getX() + Gem.WIDTH;
		float gemY = gem.getY();
		float middleX = middle.getX() + Gem.WIDTH;
		float middleY = middle.getY();

		//if right side is empty only then move
		if (board.isEmpty(middleX, middleY + Gem.HEIGHT) && board.isEmpty(gemX, gemY + Gem.HEIGHT)) {
			gem.setLocation(gemX, gemY);
			middle.setLocation(middleX, middleY);
		}
	}

	/**
	 * rotate left
	 */
	public void rotateLeft() {
		if (gem == null || middle == null)
			return;
		angle -= 90;
		if (angle < 0) {
			angle += 360;
		}

		float gemX = getRotatedX();
		float gemY = getRotatedY();

		//if no obstruction when rotating only then rotate
		if (board.isEmpty(gemX, gemY) && board.isEmpty(gem.getX(), gemY + Gem.HEIGHT)
				&& board.isEmpty(gemX, gemY + Gem.HEIGHT))
			gem.setLocation(gemX, gemY);
		else
			angle += 90;
	}

	public void rotateRight() {
		if (gem == null || middle == null)
			return;
		angle += 90;
		if (angle > 360) {
			angle -= 360;
		}

		float gemX = getRotatedX();
		float gemY = getRotatedY();

		//if no obstruction when rotating only then rotate
		if (board.isEmpty(gemX, gemY) && board.isEmpty(gem.getX(), gemY + Gem.HEIGHT)
				&& board.isEmpty(gemX, gemY + Gem.HEIGHT))
			gem.setLocation(gemX, gemY);
		else
			angle -= 90;
	}

	
	/*
	 * returns the x position after rotation
	 */
	private float getRotatedX() {
		switch (angle) {
		case 0:
			return middle.getX() - Gem.WIDTH;
		case 90:
			return middle.getX();
		case 180:
			return middle.getX() + Gem.WIDTH;
		case 270:
			return middle.getX();
		case 360:
			return middle.getX() - Gem.WIDTH;
		}

		return 0;
	}

	
	/**
	 * 
	 * @return	the y position after rotation
	 */
	private float getRotatedY() {
		switch (angle) {
		case 0:
			return middle.getY();
		case 90:
			return middle.getY() - Gem.HEIGHT;
		case 180:
			return middle.getY();
		case 270:
			return middle.getY() + Gem.HEIGHT;
		case 360:
			return middle.getY();
		}

		return 0;
	}

	/**
	 * assigns the next random pair
	 */
	public void randomNextGemPair() {
		nextGem = Gem.randomGem();
		nextMiddle = Gem.randomGem();
	}

	/**
	 * assigns the gem pair the next pair
	 */
	public void newGemPair() {
		// random blocks
		middle = nextMiddle;
		gem = nextGem;

		// set location to spawn
		gem.setLocation(spawnX, spawnY);
		middle.setLocation(spawnX, spawnY + Gem.HEIGHT);
		// reset angle
		angle = 90;

	}
	
	/**
	 * fast places the gem pair
	 */
	public void fastPlace() {
		if (gem != null)
			gem.fastSpeed();
		if (middle != null)
			middle.fastSpeed();

	}
	
	//getters and setters

	public boolean isPlaced() {
		return middle == null && gem == null;
	}
	
	public Gem getNextGem() {
		return nextGem;
	}
	
	public Gem getNextMiddle() {
		return nextMiddle;
	}

	

}
