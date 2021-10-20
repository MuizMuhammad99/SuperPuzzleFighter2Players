package com.game.puzzlefighter.board;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.game.gfx.AssetManager;
import com.game.puzzlefighter.Game;
import com.game.sfx.SoundPlayer;

/**
 * Defines the board of the puzzle fighter game
 *
 */
public class Board {

	// state
	public static final int INPUT_STATE = 1;
	public static final int DESTROYING_STATE = 2;
	public static final int FALLING_STATE = 3;

	// dimensions
	public static final int ROWS = 12;
	public static final int COLS = 6;

	private final int x;
	private final int y;
	private int state = INPUT_STATE;
	public final Cell[][] cells;

	private final GemPair fallingGemPair;
	private int rightKey, leftKey, rotateRightKey, rotateLeftKey, fastPlaceKey;
	private final ArrayList<Block> blocks;
	private final ArrayList<Gem> destroyingGems;
	private final ArrayList<Gem> fallingGems;
	private final ArrayList<Gem> fallingCounterGems;

	private final BoardObserver observer;
	private final BufferedImage boardHUD;
	private int score;
	private boolean boardFinished;

	private final SoundPlayer soundPlayer;

	/**
	 * Constructor
	 * 
	 * @param observer board observer
	 * @param x        x
	 * @param y        y
	 */
	public Board(BoardObserver observer, int x, int y) {
		this.observer = observer;
		this.x = x;
		this.y = y;

		// init cells
		cells = new Cell[ROWS][COLS];
		for (int i = 0; i < ROWS; i++)
			for (int j = 0; j < COLS; j++)
				cells[i][j] = new Cell(i, j);

		// falling gem pair
		fallingGemPair = new GemPair(this, Gem.WIDTH * 3, -Gem.HEIGHT);
		fallingGemPair.newGemPair();
		fallingGemPair.randomNextGemPair();

		blocks = new ArrayList<Block>();
		destroyingGems = new ArrayList<Gem>();
		fallingGems = new ArrayList<Gem>();
		fallingCounterGems = new ArrayList<Gem>();

		// board hud
		boardHUD = AssetManager.getImage("board");

		soundPlayer = new SoundPlayer();
	}

	public void update(float delta) {
		if (boardFinished) {
			return;
		}

		switch (state) {
		case INPUT_STATE:
			input();

			// update block
			fallingGemPair.update(delta);

			// if gem is placed,then form blocks and change state
			if (fallingGemPair.isPlaced()) {
				resetBlocks();
				formBlocks();
				soundPlayer.playSoundEffect("gemPlace");

				boolean gemsDestroyed = destroyGems();
				state = DESTROYING_STATE;

				if (gemsDestroyed) {
					soundPlayer.playSoundEffect2("gemBreak");
					observer.notifyGemsDestroyed(this, destroyingGems.size());

				}

			}
			break;
		case DESTROYING_STATE:

			// remove gems which are destroyed
			for (int i = destroyingGems.size() - 1; i >= 0; i--) {
				destroyingGems.get(i).update(delta);
				if (destroyingGems.get(i).isDestroyed())
					destroyingGems.remove(i);
			}

			// change state if all gems to be destroyed are destroyed
			if (destroyingGems.size() == 0) {
				fallGems();
				state = FALLING_STATE;
			}

			break;
		case FALLING_STATE:

			// stop gems that has already fallen
			for (int i = 0; i < fallingGems.size(); i++) {
				Gem gem = fallingGems.get(i);
				gem.update(delta);
				if (!isEmpty(gem.getX(), gem.getY() + Gem.HEIGHT)) {
					gem.setState(Gem.IDLE);
					place(gem, gem.getX(), gem.getY());
				}
			}

			// remove gems that has done falling
			for (int i = fallingGems.size() - 1; i >= 0; i--)
				if (!fallingGems.get(i).isFalling())
					fallingGems.remove(i);

			// if all falling gems has fallen,then form blocks and change state
			if (fallingGems.size() == 0) {
				resetBlocks();
				formBlocks();
				soundPlayer.playSoundEffect("gemPlace");

				boolean destroyGems = destroyGems();
				state = destroyGems ? DESTROYING_STATE : INPUT_STATE;
				if (state == INPUT_STATE) {
					fallingGemPair.newGemPair();
					fallingGemPair.randomNextGemPair();
					tickCounterGems();
				}

				// notify observer
				if (destroyGems) {
					soundPlayer.playSoundEffect("gemBreak");
					observer.notifyGemsDestroyed(this, destroyingGems.size());

				}
			}

			break;
		}

		// update cells
		for (int i = 0; i < ROWS; i++)
			for (int j = 0; j < COLS; j++)
				cells[i][j].update(delta);

		// fall counter gems
		for (int i = 0; i < fallingCounterGems.size(); i++) {
			Gem gem = fallingCounterGems.get(i);
			gem.update(delta);
			if (!isEmpty(gem.getX(), gem.getY() + Gem.HEIGHT)) {
				gem.setState(Gem.IDLE);
				place(gem, gem.getX(), gem.getY());
				soundPlayer.playSoundEffect("counterGemPlace");
			}
		}

		// remove counter gems that have fallen
		for (int i = fallingCounterGems.size() - 1; i >= 0; i--)
			if (!fallingCounterGems.get(i).isFalling())
				fallingCounterGems.remove(i);

		// if entrance cell is filled, game ends
		if (!cells[0][3].isEmpty()) {
			observer.notifyGameFinished(this);
			boardFinished = true;
		}

	}

	public void render(Graphics2D g2) {

		// image
		g2.drawImage(boardHUD, x - 6, y, COLS * Gem.WIDTH + 12, ROWS * Gem.HEIGHT + 5, null);

		// cells
		for (int i = 0; i < ROWS; i++)
			for (int j = 0; j < COLS; j++)
				cells[i][j].render(g2, x, y);

		// destroying gems
		for (Gem gem : destroyingGems)
			gem.render(g2, x, y);

		// falling gems
		for (Gem gem : fallingGems)
			gem.render(g2, x, y);

		// falling counter gems
		for (Gem gem : fallingCounterGems)
			gem.render(g2, x, y);

		// block
		fallingGemPair.render(g2, x, y);
	}

	/**
	 * Take input from the user
	 */
	private void input() {
		if (Game.keyManager.isKeyJustPressed(leftKey)) {
			fallingGemPair.left();
			soundPlayer.playSoundEffect("gemPairMove");
		} else if (Game.keyManager.isKeyJustPressed(rightKey)) {
			fallingGemPair.right();
			soundPlayer.playSoundEffect("gemPairMove");
		} else if (Game.keyManager.isKeyJustPressed(rotateLeftKey)) {
			fallingGemPair.rotateLeft();
			soundPlayer.playSoundEffect("gemPairMove");
		} else if (Game.keyManager.isKeyJustPressed(rotateRightKey)) {
			fallingGemPair.rotateRight();
			soundPlayer.playSoundEffect("gemPairMove");
		} else if (Game.keyManager.isKeyJustPressed(fastPlaceKey)) {
			fallingGemPair.fastPlace();
			soundPlayer.playSoundEffect("gemPairMove");
		}

	}

	/**
	 * Resets all formed blocks in board
	 */
	private void resetBlocks() {
		blocks.clear();

		for (int i = 0; i < ROWS; i++)
			for (int j = 0; j < COLS; j++)
				if (getGem(i, j) != null && getGem(i, j).isBlockGem()) {
					getGem(i, j).setBlock(null);
				}
	}

	/**
	 * Forms the blocks in the board
	 */
	private void formBlocks() {
		for (int i = 0; i < ROWS; i++)
			for (int j = 0; j < COLS; j++) {
				if (!cells[i][j].isEmpty() && !getGem(i, j).isBlockGem() && getGem(i, j).isNormalGem()) {

					// if 2 x 2 block has any empty cell,then no block is formed
					if (getGem(i, j + 1) == null || getGem(i + 1, j) == null || getGem(i + 1, j + 1) == null)
						continue;

					// if 2x2 block gems do not have same color,then block is not formed
					int gemColor = getGem(i, j).getGemColor();
					if (getGem(i, j + 1).getGemColor() != gemColor || getGem(i + 1, j).getGemColor() != gemColor
							|| getGem(i + 1, j + 1).getGemColor() != gemColor)
						continue;

					// if 2x2 blocks gems are already part of block,the block is not formed
					if (getGem(i, j).getType() != Gem.NORMAL || getGem(i, j + 1).getType() != Gem.NORMAL
							|| getGem(i + 1, j).isBlockGem() || getGem(i + 1, j + 1).isBlockGem())
						continue;

					// end row,col of block
					int endRow = i + 1;
					int endCol = j + 1;

					// if a 2x2 block is formed, grow it:
					// vertically
					for (int row = endRow + 1; row < ROWS; row++)
						if (getGem(row, j) != null && getGem(row, j + 1) != null
								&& getGem(row, j).getGemColor() == gemColor
								&& getGem(row, j + 1).getGemColor() == gemColor && !getGem(row, j).isBlockGem()
								&& !getGem(row, j + 1).isBlockGem())
							endRow = row;
						else
							break;

					// horizontally
					for (int col = endCol + 1; col < COLS; col++) {
						boolean canGrow = true;

						for (int row = i; row <= endRow; row++)
							if (getGem(row, col) == null || getGem(row, col).getGemColor() != gemColor
									|| getGem(row, col).isBlockGem()) {
								canGrow = false;
								break;
							}

						if (canGrow)
							endCol = col;
						else
							break;
					}

					// set store block gems in list
					ArrayList<Gem> blockGems = new ArrayList<Gem>();
					for (int row = i; row <= endRow; row++)
						for (int col = j; col <= endCol; col++)
							if (getGem(row, col) == null)
								System.out.println(row + "--" + col);
							else {
								Gem gem = getGem(row, col);
								gem.setBlockFrames("center" + gem.getGemColor());
								blockGems.add(gem);
							}

					// change texture of block gems
					// corner
					getGem(i, j).setBlockFrames("topLeftCorner" + getGem(i, j).getGemColor());
					getGem(i, endCol).setBlockFrames("topRightCorner" + getGem(i, endCol).getGemColor());
					getGem(endRow, j).setBlockFrames("bottomLeftCorner" + getGem(endRow, j).getGemColor());
					getGem(endRow, endCol).setBlockFrames("bottomRightCorner" + getGem(endRow, endCol).getGemColor());

					// edges
					// left & right edges
					for (int row = i + 1; row < endRow; row++) {
						getGem(row, j).setBlockFrames("leftEdge" + getGem(row, j).getGemColor());
						getGem(row, endCol).setBlockFrames("rightEdge" + getGem(row, endCol).getGemColor());
					}
					// top and bottom edges
					for (int col = j + 1; col < endCol; col++) {
						getGem(i, col).setBlockFrames("topEdge" + getGem(i, col).getGemColor());
						getGem(endRow, col).setBlockFrames("bottomEdge" + getGem(endRow, col).getGemColor());
					}

					// create a block
					Block block = new Block(blockGems);
					blocks.add(block);
				}

			}

	}

	/**
	 * Falls the gems which have an empty space below them
	 */
	private void fallGems() {
		for (int i = ROWS - 2; i >= 0; i--)
			for (int j = COLS - 1; j >= 0; j--) {
				Gem gem = getGem(i, j);
				Gem belowGem = getGem(i + 1, j);
				if (gem != null && belowGem == null) {
					cells[i][j].setGem(null);
					gem.setState(Gem.MOVING);
					// gem.normalSpeed();
					gem.fastSpeed();
					fallingGems.add(gem);
				}
			}
	}

	/**
	 * Destroys the gems which are destroyed by crash or diamond gems
	 * 
	 * @return true if gems are destroyed
	 */
	private boolean destroyGems() {
		for (int i = 0; i < ROWS; i++)
			for (int j = 0; j < COLS; j++)
				if (getGem(i, j) != null)
					// if crash gem, then destroy,similar gems
					if (getGem(i, j).getType() == Gem.CRASH)
						destroyGems(getGem(i, j), i, j);

					// if diamond gem,then destroy all same colored gems
					else if (getGem(i, j).getType() == Gem.DIAMOND) {
						getGem(i, j).destroy();
						destroyingGems.add(getGem(i, j));
						cells[i][j].setGem(null);

						Gem bottomGem = getGem(i + 1, j);
						if (bottomGem != null)
							destroySameColorGems(bottomGem.getGemColor());
					}

		return destroyingGems.size() > 0;
	}

	/**
	 * Destroys all same colored gems
	 * 
	 * @param gemColor the color
	 */
	private void destroySameColorGems(int gemColor) {
		for (int i = 0; i < ROWS; i++)
			for (int j = 0; j < COLS; j++)
				if (getGem(i, j) != null && getGem(i, j).getGemColor() == gemColor) {
					Gem gem = cells[i][j].getGem();
					gem.destroy();

					destroyingGems.add(gem);
					cells[i][j].setGem(null);

					score += 10;
				}
	}

	/**
	 * Destroy Gems which for a group starting from cell(r,c)
	 * 
	 * @param gem the gem
	 * @param r   row
	 * @param c   col
	 */
	private void destroyGems(Gem gem, int r, int c) {
		ArrayList<Cell> cellsToClear = new ArrayList<Cell>();
		int gemColor = gem.getGemColor();
		boolean crashGem = gem.getType() == Gem.CRASH;
		cellsToClear.add(cells[r][c]);

		for (int i = 0; i < cellsToClear.size(); i++) {
			Cell cell = cellsToClear.get(i);
			int row = cell.getRow();
			int col = cell.getCol();

			// check right
			if (col < COLS - 1)
				if (!cells[row][col + 1].isEmpty() && cells[row][col + 1].getGem().getGemColor() == gemColor
						&& (cells[row][col + 1].getGem().getType() == Gem.NORMAL
								|| cells[row][col + 1].getGem().getType() == Gem.CRASH)
						&& !cellsToClear.contains(cells[row][col + 1])) {
					cellsToClear.add(cells[row][col + 1]);
					if (cells[row][col + 1].getGem().getType() == Gem.CRASH)
						crashGem = true;
				}

			// check left
			if (col > 0)
				if (!cells[row][col - 1].isEmpty() && cells[row][col - 1].getGem().getGemColor() == gemColor
						&& (cells[row][col - 1].getGem().getType() == Gem.NORMAL
								|| cells[row][col - 1].getGem().getType() == Gem.CRASH)
						&& !cellsToClear.contains(cells[row][col - 1])) {
					cellsToClear.add(cells[row][col - 1]);
					if (cells[row][col - 1].getGem().getType() == Gem.CRASH)
						crashGem = true;
				}

			// check up
			if (row > 0)
				if (!cells[row - 1][col].isEmpty() && cells[row - 1][col].getGem().getGemColor() == gemColor
						&& (cells[row - 1][col].getGem().getType() == Gem.NORMAL
								|| cells[row - 1][col].getGem().getType() == Gem.CRASH)
						&& !cellsToClear.contains(cells[row - 1][col])) {
					cellsToClear.add(cells[row - 1][col]);
					if (cells[row - 1][col].getGem().getType() == Gem.CRASH)
						crashGem = true;
				}

			// check down
			if (row < ROWS - 1)
				if (!cells[row + 1][col].isEmpty() && cells[row + 1][col].getGem().getGemColor() == gemColor
						&& (cells[row + 1][col].getGem().getType() == Gem.NORMAL
								|| cells[row + 1][col].getGem().getType() == Gem.CRASH)
						&& !cellsToClear.contains(cells[row + 1][col])) {
					cellsToClear.add(cells[row + 1][col]);
					if (cells[row + 1][col].getGem().getType() == Gem.CRASH)
						crashGem = true;
				}

		}

		// if there is crash gem in the cells and cells to clear > 1 then,
		// destroy gems in cells and add to destroying list
		if (crashGem && cellsToClear.size() > 1)
			for (int i = 0; i < cellsToClear.size(); i++) {
				Gem g = cellsToClear.get(i).getGem();
				g.destroy();
				destroyingGems.add(g);
				cellsToClear.get(i).setGem(null);

				score += g.isBlockGem() ? 20 : 10;
			}

	}

	/**
	 * 
	 * @param row cell row
	 * @param col cell column
	 * @return the gem at cell(row,col)
	 */
	private Gem getGem(int row, int col) {
		if (row >= ROWS || row < 0 || col < 0 || col >= COLS)
			return null;

		return cells[row][col].getGem();
	}

	/**
	 * updates the counter gems
	 */
	private void tickCounterGems() {
		for (int i = 0; i < ROWS; i++)
			for (int j = 0; j < COLS; j++)
				if (getGem(i, j) != null && getGem(i, j).isCounterGem())
					getGem(i, j).tick();
	}

	/**
	 * places the gem at x,y
	 * 
	 * @param gem gem
	 * @param x   x
	 * @param y   y
	 */
	public void place(Gem gem, float x, float y) {
		int row = (int) (y / Gem.HEIGHT);
		int col = (int) (x / Gem.WIDTH);

		gem.setLocation(col * Gem.WIDTH, row * Gem.HEIGHT);
		cells[row][col].setGem(gem);

	}

	/**
	 * spawns counter gems
	 * 
	 * @param numGems num of counter gems to spawn
	 */
	public void spawnCounterGems(int numGems) {
		int count = 0;

		for (int i = 1; i < ROWS; i++)
			for (int j = 0; j < COLS; j++)
				if (getGem(i, j) == null) {
					// create counter gem
					Gem gem = Gem.randomGem();
					gem.setType(Gem.COUNTER);
					gem.fastSpeed();
					gem.setLocation(j * Gem.WIDTH, i * Gem.HEIGHT);

					// add to list
					fallingCounterGems.add(gem);
					count++;

					if (count >= numGems)
						return;
				}
	}

	/**
	 * 
	 * @param x x
	 * @param y y
	 * @return true if the cell is empty
	 */
	public boolean isEmpty(float x, float y) {
		int row = (int) (y / Gem.HEIGHT);
		int col = (int) (x / Gem.WIDTH);

		if (row >= ROWS || row < 0 || col < 0 || col >= COLS)
			return false;

		return cells[row][col].isEmpty();
	}

	/**
	 * Sets the input controls for the game
	 * 
	 */
	public void setControls(int leftKey, int rightKey, int rotateLeftKey, int rotateRightKey, int fastPlaceKey) {
		this.leftKey = leftKey;
		this.rightKey = rightKey;
		this.rotateLeftKey = rotateLeftKey;
		this.rotateRightKey = rotateRightKey;
		this.fastPlaceKey = fastPlaceKey;
	}

	// getters

	public void finished() {
		boardFinished = true;
	}

	public GemPair getFallingPair() {
		return fallingGemPair;
	}

	public int getX() {
		return x;
	}

	public int getScore() {
		return score;
	}

}
