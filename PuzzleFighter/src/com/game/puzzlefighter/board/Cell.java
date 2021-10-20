package com.game.puzzlefighter.board;

import java.awt.*;

/**
 * A single cell on board
 * It updates the gems it holds.
 */
public class Cell {

    private final int row;
    private final int col;
    private Gem gem;

    /**
     * Constructor
     *
     * @param row row
     * @param col column
     */
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void update(float delta) {
        if (isEmpty())
            return;
        gem.update(delta);
    }

    public void render(Graphics2D g2, int xOffset, int yOffset) {
        if (isEmpty())
            return;

        gem.render(g2, xOffset, yOffset);
    }

    //getters

    public boolean isEmpty() {
        return gem == null;
    }

    public Gem getGem() {
        return gem;
    }

    public void setGem(Gem gem) {
        this.gem = gem;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
