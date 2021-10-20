package com.game.puzzlefighter.board;

import java.util.ArrayList;

import com.game.animation.Animation;

/**
 * Defines a block gem.
 * 
 *
 */
public class Block {

	private ArrayList<Gem> gems;
	private int startRow, startCol;
	private int endRow, endCol;

	/**
	 * Constructor
	 * @param gems	the gems forming the block
	 * @param startRow	start row of block
	 * @param startCol	start column of block
	 * @param endRow	end row of block
	 * @param endCol	end column of block
	 */
	public Block(ArrayList<Gem> gems, int startRow, int startCol, int endRow, int endCol) {
		this.gems = gems;
		this.startRow = startRow;
		this.startCol = startCol;
		this.endRow = endRow;
		this.endCol = endCol;

		// set type of gems to BLOCK & set animation state same for all block gems
		Animation animation = gems.get(0).getBlockAnimation();
		for (Gem gem : gems) {
			gem.setBlock(this);
			gem.getBlockAnimation().copyState(animation);
		}
	}

	/**
	 * Destroys all the gems in block
	 */
	public void destroy() {
		for (Gem gem : gems)
			gem.destroy();
	}

	//getters
	
	public ArrayList<Gem> getGems() {
		return gems;
	}

	public int getStartRow() {
		return startRow;
	}

	public int getStartCol() {
		return startCol;
	}

	public int getEndRow() {
		return endRow;
	}

	public int getEndCol() {
		return endCol;
	}

}
