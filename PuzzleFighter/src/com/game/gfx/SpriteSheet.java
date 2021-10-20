package com.game.gfx;

import com.game.utils.ImageLoader;

import java.awt.image.BufferedImage;

/**
 * SpriteSheet Class
 * Models a sprite sheet from which individual sprites can be cropped.
 */
public class SpriteSheet {

    private final BufferedImage sheet;

    /**
     * Constructor
     *
     * @param path file path
     */
    public SpriteSheet(String path) {
        this.sheet = (ImageLoader.loadImage(path));
    }

    /**
     * Crops the sheet.
     *
     * @param x      x
     * @param y      y
     * @param width  width
     * @param height height
     * @return cropped image
     */
    public BufferedImage crop(int x, int y, int width, int height) {
        return sheet.getSubimage(x, y, width, height);
    }

}
