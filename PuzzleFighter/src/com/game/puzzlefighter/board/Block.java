package com.game.puzzlefighter.board;

import com.game.animation.Animation;

import java.util.ArrayList;

/**
 * Defines a block gem.
 */
public class Block {

    /**
     * Constructor
     *
     * @param gems the gems forming the block
     */
    public Block(ArrayList<Gem> gems) {

        // set type of gems to BLOCK & set animation state same for all block gems
        Animation animation = gems.get(0).getBlockAnimation();
        for (Gem gem : gems) {
            gem.setBlock(this);
            gem.getBlockAnimation().copyState(animation);
        }
    }

}
