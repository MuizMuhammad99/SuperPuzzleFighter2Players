package com.game.utils;

import java.awt.Font;
import java.awt.font.FontRenderContext;

/**
 * Gives the font text width in pixels
 *
 */
public class FontUtils {

	public static int getTextWidth(Font font, String text) {
		return (int) font.getStringBounds(text, new FontRenderContext(font.getTransform(), true, true)).getWidth();
	}
}
