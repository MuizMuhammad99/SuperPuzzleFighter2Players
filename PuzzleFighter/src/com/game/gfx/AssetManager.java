package com.game.gfx;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import com.game.sfx.AudioFile;
import com.game.utils.AudioLoader;
import com.game.utils.ImageLoader;

/**
 * AssetManager Class
 * Manages the resources of the game.
 * All the sounds,images and the animation frames are loaded and stored here.
 *
 */
public class AssetManager {

	private static HashMap<String, AudioFile> allSounds = new HashMap<String, AudioFile>();
	private static HashMap<String, BufferedImage> allImages = new HashMap<String, BufferedImage>();
	private static HashMap<String, BufferedImage[]> allAnimations = new HashMap<String, BufferedImage[]>();

	/**
	 * loads the resources
	 */
	public static void load() {
		loadImages();
		loadAnimations();
		loadAudio();

	}

	/**
	 * loads the images
	 */
	private static void loadImages() {

		SpriteSheet blueSheet = new SpriteSheet("/blue.png");
		SpriteSheet redSheet = new SpriteSheet("/red.png");
		SpriteSheet greenSheet = new SpriteSheet("/green.png");
		SpriteSheet yellowSheet = new SpriteSheet("/yellow.png");

		// gem images
		allImages.put("blueGem", blueSheet.crop(114, 0, 14, 14));
		allImages.put("redGem", redSheet.crop(114, 0, 14, 14));
		allImages.put("greenGem", greenSheet.crop(114, 0, 14, 14));
		allImages.put("yellowGem", yellowSheet.crop(114, 0, 14, 14));

		/* HUD IMAGES **/
		SpriteSheet hudSheet = new SpriteSheet("/HUD.png");
		SpriteSheet hud2Sheet = new SpriteSheet("/HUD2.png");

		// board
		allImages.put("board", hudSheet.crop(191, 16, 102, 211));

		// next
		allImages.put("next1", hudSheet.crop(301, 30, 36, 50));
		allImages.put("next2", hudSheet.crop(301, 30, 36, 50));

		// score
		allImages.put("score1", hudSheet.crop(13, 13, 65, 39));
		allImages.put("score2", hudSheet.crop(98, 13, 65, 39));

		// score numbers
		// purple
		allImages.put("purple0", hudSheet.crop(12, 77, 8, 13));
		allImages.put("purple1", hudSheet.crop(20, 77, 6, 13));
		allImages.put("purple2", hudSheet.crop(26, 77, 8, 13));
		allImages.put("purple3", hudSheet.crop(34, 77, 8, 13));
		allImages.put("purple4", hudSheet.crop(42, 77, 8, 13));
		allImages.put("purple5", hudSheet.crop(12, 92, 8, 13));
		allImages.put("purple6", hudSheet.crop(20, 92, 8, 13));
		allImages.put("purple7", hudSheet.crop(28, 92, 8, 13));
		allImages.put("purple8", hudSheet.crop(36, 92, 8, 13));
		allImages.put("purple9", hudSheet.crop(44, 92, 8, 13));

		// pink
		allImages.put("pink0", hudSheet.crop(138, 86, 8, 13));
		allImages.put("pink1", hudSheet.crop(146, 86, 6, 13));
		allImages.put("pink2", hudSheet.crop(152, 86, 8, 13));
		allImages.put("pink3", hudSheet.crop(160, 86, 8, 13));
		allImages.put("pink4", hudSheet.crop(168, 86, 8, 13));
		allImages.put("pink5", hudSheet.crop(138, 101, 8, 13));
		allImages.put("pink6", hudSheet.crop(146, 101, 8, 13));
		allImages.put("pink7", hudSheet.crop(154, 101, 8, 13));
		allImages.put("pink8", hudSheet.crop(162, 101, 8, 13));
		allImages.put("pink9", hudSheet.crop(170, 101, 8, 13));

		// logo
		allImages.put("logo", hudSheet.crop(111, 118, 64, 62));

		allImages.put("ready", hud2Sheet.crop(18, 46, 158, 29));
		allImages.put("fight", hud2Sheet.crop(12, 255, 140, 29));

		// stage
		allImages.put("stage", ImageLoader.loadImage("/stage.png"));

	}

	
	/**
	 * loads the animations
	 */
	private static void loadAnimations() {
		SpriteSheet blueSheet = new SpriteSheet("/blue.png");
		SpriteSheet redSheet = new SpriteSheet("/red.png");
		SpriteSheet greenSheet = new SpriteSheet("/green.png");
		SpriteSheet yellowSheet = new SpriteSheet("/yellow.png");

		// counter animation frames
		BufferedImage[] counter1, counter2, counter3, counter4;
		counter1 = new BufferedImage[6];
		counter2 = new BufferedImage[6];
		counter3 = new BufferedImage[6];
		counter4 = new BufferedImage[6];

		for (int i = 0; i < 6; i++) {
			counter1[i] = blueSheet.crop(374 + (i * 16), 498, 16, 16);
			counter2[i] = redSheet.crop(374 + (i * 16), 498, 16, 16);
			counter3[i] = greenSheet.crop(374 + (i * 16), 498, 16, 16);
			counter4[i] = yellowSheet.crop(374 + (i * 16), 498, 16, 16);
		}

		allAnimations.put("counter1", counter1);
		allAnimations.put("counter2", counter2);
		allAnimations.put("counter3", counter3);
		allAnimations.put("counter4", counter4);

		// crash animation frames
		BufferedImage[] crash1, crash2, crash3, crash4;
		crash1 = new BufferedImage[4];
		crash2 = new BufferedImage[4];
		crash3 = new BufferedImage[4];
		crash4 = new BufferedImage[4];

		for (int i = 0; i < 4; i++) {
			crash1[i] = blueSheet.crop(240, 209 + (i * 16), 16, 16);
			crash2[i] = redSheet.crop(240, 209 + (i * 16), 16, 16);
			crash3[i] = greenSheet.crop(240, 209 + (i * 16), 16, 16);
			crash4[i] = yellowSheet.crop(240, 209 + (i * 16), 16, 16);
		}

		allAnimations.put("crash1", crash1);
		allAnimations.put("crash2", crash2);
		allAnimations.put("crash3", crash3);
		allAnimations.put("crash4", crash4);

		// diamond animation frames
		BufferedImage[] diamond1, diamond2, diamond3, diamond4;
		diamond1 = new BufferedImage[4];
		diamond2 = new BufferedImage[4];
		diamond3 = new BufferedImage[4];
		diamond4 = new BufferedImage[4];

		for (int i = 0; i < 4; i++) {
			diamond1[i] = blueSheet.crop(2 + (i * 16), 425, 16, 16);
			diamond2[i] = redSheet.crop(2 + (i * 16), 425, 16, 16);
			diamond3[i] = greenSheet.crop(2 + (i * 16), 425, 16, 16);
			diamond4[i] = yellowSheet.crop(2 + (i * 16), 425, 16, 16);
		}

		allAnimations.put("diamond1", diamond1);
		allAnimations.put("diamond2", diamond2);
		allAnimations.put("diamond3", diamond3);
		allAnimations.put("diamond4", diamond4);

		// destroy animation frames
		BufferedImage[] destroy1, destroy2, destroy3, destroy4;
		destroy1 = new BufferedImage[8];
		destroy2 = new BufferedImage[8];
		destroy3 = new BufferedImage[8];
		destroy4 = new BufferedImage[8];

		for (int i = 0; i < 8; i++) {
			destroy1[i] = blueSheet.crop(256 + (i * 32), 82, 32, 32);
			destroy2[i] = redSheet.crop(256 + (i * 32), 82, 32, 32);
			destroy3[i] = greenSheet.crop(256 + (i * 32), 82, 32, 32);
			destroy4[i] = yellowSheet.crop(256 + (i * 32), 82, 32, 32);
		}

		allAnimations.put("destroy1", destroy1);
		allAnimations.put("destroy2", destroy2);
		allAnimations.put("destroy3", destroy3);
		allAnimations.put("destroy4", destroy4);

		// GEM BLOCK ANIMATIONS

		// top left corner
		BufferedImage[] topLeftCorner1, topLeftCorner2, topLeftCorner3, topLeftCorner4;
		topLeftCorner1 = new BufferedImage[4];
		topLeftCorner2 = new BufferedImage[4];
		topLeftCorner3 = new BufferedImage[4];
		topLeftCorner4 = new BufferedImage[4];

		for (int i = 0; i < 4; i++) {
			topLeftCorner1[i] = blueSheet.crop(1 + (i * 32), 31, 16, 16);
			topLeftCorner2[i] = redSheet.crop(1 + (i * 32), 31, 16, 16);
			topLeftCorner3[i] = greenSheet.crop(1 + (i * 32), 31, 16, 16);
			topLeftCorner4[i] = yellowSheet.crop(1 + (i * 32), 31, 16, 16);
		}

		allAnimations.put("topLeftCorner1", topLeftCorner1);
		allAnimations.put("topLeftCorner2", topLeftCorner2);
		allAnimations.put("topLeftCorner3", topLeftCorner3);
		allAnimations.put("topLeftCorner4", topLeftCorner4);

		// top right corner
		BufferedImage[] topRightCorner1, topRightCorner2, topRightCorner3, topRightCorner4;
		topRightCorner1 = new BufferedImage[4];
		topRightCorner2 = new BufferedImage[4];
		topRightCorner3 = new BufferedImage[4];
		topRightCorner4 = new BufferedImage[4];

		for (int i = 0; i < 4; i++) {
			topRightCorner1[i] = blueSheet.crop(17 + (i * 32), 31, 16, 16);
			topRightCorner2[i] = redSheet.crop(17 + (i * 32), 31, 16, 16);
			topRightCorner3[i] = greenSheet.crop(17 + (i * 32), 31, 16, 16);
			topRightCorner4[i] = yellowSheet.crop(17 + (i * 32), 31, 16, 16);
		}

		allAnimations.put("topRightCorner1", topRightCorner1);
		allAnimations.put("topRightCorner2", topRightCorner2);
		allAnimations.put("topRightCorner3", topRightCorner3);
		allAnimations.put("topRightCorner4", topRightCorner4);
		// bottom left corner
		BufferedImage[] bottomLeftCorner1, bottomLeftCorner2, bottomLeftCorner3, bottomLeftCorner4;
		bottomLeftCorner1 = new BufferedImage[4];
		bottomLeftCorner2 = new BufferedImage[4];
		bottomLeftCorner3 = new BufferedImage[4];
		bottomLeftCorner4 = new BufferedImage[4];

		for (int i = 0; i < 4; i++) {
			bottomLeftCorner1[i] = blueSheet.crop(1 + (i * 32), 47, 16, 16);
			bottomLeftCorner2[i] = redSheet.crop(1 + (i * 32), 47, 16, 16);
			bottomLeftCorner3[i] = greenSheet.crop(1 + (i * 32), 47, 16, 16);
			bottomLeftCorner4[i] = yellowSheet.crop(1 + (i * 32), 47, 16, 16);
		}

		allAnimations.put("bottomLeftCorner1", bottomLeftCorner1);
		allAnimations.put("bottomLeftCorner2", bottomLeftCorner2);
		allAnimations.put("bottomLeftCorner3", bottomLeftCorner3);
		allAnimations.put("bottomLeftCorner4", bottomLeftCorner4);
		// bottom right corner
		BufferedImage[] bottomRightCorner1, bottomRightCorner2, bottomRightCorner3, bottomRightCorner4;
		bottomRightCorner1 = new BufferedImage[4];
		bottomRightCorner2 = new BufferedImage[4];
		bottomRightCorner3 = new BufferedImage[4];
		bottomRightCorner4 = new BufferedImage[4];

		for (int i = 0; i < 4; i++) {
			bottomRightCorner1[i] = blueSheet.crop(17 + (i * 32), 47, 16, 16);
			bottomRightCorner2[i] = redSheet.crop(17 + (i * 32), 47, 16, 16);
			bottomRightCorner3[i] = greenSheet.crop(17 + (i * 32), 47, 16, 16);
			bottomRightCorner4[i] = yellowSheet.crop(17 + (i * 32), 47, 16, 16);
		}

		allAnimations.put("bottomRightCorner1", bottomRightCorner1);
		allAnimations.put("bottomRightCorner2", bottomRightCorner2);
		allAnimations.put("bottomRightCorner3", bottomRightCorner3);
		allAnimations.put("bottomRightCorner4", bottomRightCorner4);
		// top edge
		BufferedImage[] topEdge1, topEdge2, topEdge3, topEdge4;
		topEdge1 = new BufferedImage[4];
		topEdge2 = new BufferedImage[4];
		topEdge3 = new BufferedImage[4];
		topEdge4 = new BufferedImage[4];

		for (int i = 0; i < 4; i++) {
			topEdge1[i] = blueSheet.crop(193 + (i * 16), 15, 16, 16);
			topEdge2[i] = redSheet.crop(193 + (i * 16), 15, 16, 16);
			topEdge3[i] = greenSheet.crop(193 + (i * 16), 15, 16, 16);
			topEdge4[i] = yellowSheet.crop(193 + (i * 16), 15, 16, 16);
		}

		allAnimations.put("topEdge1", topEdge1);
		allAnimations.put("topEdge2", topEdge2);
		allAnimations.put("topEdge3", topEdge3);
		allAnimations.put("topEdge4", topEdge4);
		// right edge
		BufferedImage[] rightEdge1, rightEdge2, rightEdge3, rightEdge4;
		rightEdge1 = new BufferedImage[4];
		rightEdge2 = new BufferedImage[4];
		rightEdge3 = new BufferedImage[4];
		rightEdge4 = new BufferedImage[4];

		for (int i = 0; i < 4; i++) {
			rightEdge1[i] = blueSheet.crop(65 + (i * 16), 63, 16, 16);
			rightEdge2[i] = redSheet.crop(65 + (i * 16), 63, 16, 16);
			rightEdge3[i] = greenSheet.crop(65 + (i * 16), 63, 16, 16);
			rightEdge4[i] = yellowSheet.crop(65 + (i * 16), 63, 16, 16);
		}

		allAnimations.put("rightEdge1", rightEdge1);
		allAnimations.put("rightEdge2", rightEdge2);
		allAnimations.put("rightEdge3", rightEdge3);
		allAnimations.put("rightEdge4", rightEdge4);
		// bottom edge
		BufferedImage[] bottomEdge1, bottomEdge2, bottomEdge3, bottomEdge4;
		bottomEdge1 = new BufferedImage[4];
		bottomEdge2 = new BufferedImage[4];
		bottomEdge3 = new BufferedImage[4];
		bottomEdge4 = new BufferedImage[4];

		for (int i = 0; i < 4; i++) {
			bottomEdge1[i] = blueSheet.crop(129 + (i * 16), 15, 16, 16);
			bottomEdge2[i] = redSheet.crop(129 + (i * 16), 15, 16, 16);
			bottomEdge3[i] = greenSheet.crop(129 + (i * 16), 15, 16, 16);
			bottomEdge4[i] = yellowSheet.crop(129 + (i * 16), 15, 16, 16);
		}

		allAnimations.put("bottomEdge1", bottomEdge1);
		allAnimations.put("bottomEdge2", bottomEdge2);
		allAnimations.put("bottomEdge3", bottomEdge3);
		allAnimations.put("bottomEdge4", bottomEdge4);
		// left edge
		BufferedImage[] leftEdge1, leftEdge2, leftEdge3, leftEdge4;
		leftEdge1 = new BufferedImage[4];
		leftEdge2 = new BufferedImage[4];
		leftEdge3 = new BufferedImage[4];
		leftEdge4 = new BufferedImage[4];

		for (int i = 0; i < 4; i++) {
			leftEdge1[i] = blueSheet.crop(1 + (i * 16), 63, 16, 16);
			leftEdge2[i] = redSheet.crop(1 + (i * 16), 63, 16, 16);
			leftEdge3[i] = greenSheet.crop(1 + (i * 16), 63, 16, 16);
			leftEdge4[i] = yellowSheet.crop(1 + (i * 16), 63, 16, 16);
		}

		allAnimations.put("leftEdge1", leftEdge1);
		allAnimations.put("leftEdge2", leftEdge2);
		allAnimations.put("leftEdge3", leftEdge3);
		allAnimations.put("leftEdge4", leftEdge4);

		// center
		BufferedImage[] center1, center2, center3, center4;
		center1 = new BufferedImage[4];
		center2 = new BufferedImage[4];
		center3 = new BufferedImage[4];
		center4 = new BufferedImage[4];

		for (int i = 0; i < 4; i++) {
			center1[i] = blueSheet.crop(129 + (i * 16), 63, 16, 16);
			center2[i] = redSheet.crop(129 + (i * 16), 63, 16, 16);
			center3[i] = greenSheet.crop(129 + (i * 16), 63, 16, 16);
			center4[i] = yellowSheet.crop(129 + (i * 16), 63, 16, 16);
		}

		allAnimations.put("center1", center1);
		allAnimations.put("center2", center2);
		allAnimations.put("center3", center3);
		allAnimations.put("center4", center4);

		/* CHARACTER ANIMATIONS */
		SpriteSheet ryuSheet = new SpriteSheet("/ryu.png");

		// Ryu animations

		// idle
		BufferedImage[] idleFrames = new BufferedImage[7];
		idleFrames[0] = ryuSheet.crop(4, 7, 61, 62);
		idleFrames[1] = ryuSheet.crop(67, 7, 61, 63);
		idleFrames[2] = ryuSheet.crop(130, 7, 61, 63);
		idleFrames[3] = ryuSheet.crop(191, 7, 61, 65);
		idleFrames[4] = ryuSheet.crop(273, 7, 61, 64);
		idleFrames[5] = ryuSheet.crop(336, 7, 61, 64);
		idleFrames[6] = ryuSheet.crop(395, 7, 61, 64);

		// advantage frames
		BufferedImage[] advanFrames = new BufferedImage[8];
		advanFrames[0] = ryuSheet.crop(6, 77, 49, 66);
		advanFrames[1] = ryuSheet.crop(66, 77, 49, 66);
		advanFrames[2] = ryuSheet.crop(127, 77, 49, 66);
		advanFrames[3] = ryuSheet.crop(187, 77, 49, 66);
		advanFrames[4] = ryuSheet.crop(240, 77, 49, 66);
		advanFrames[5] = ryuSheet.crop(301, 77, 49, 66);
		advanFrames[6] = ryuSheet.crop(361, 77, 49, 66);
		advanFrames[7] = ryuSheet.crop(431, 77, 49, 66);

		// disadvantage frames
		BufferedImage[] disAdvanFrames = new BufferedImage[8];
		disAdvanFrames[0] = ryuSheet.crop(6, 216, 60, 64);
		disAdvanFrames[1] = ryuSheet.crop(68, 216, 60, 64);
		disAdvanFrames[2] = ryuSheet.crop(129, 216, 60, 64);
		disAdvanFrames[3] = ryuSheet.crop(190, 216, 60, 64);
		disAdvanFrames[4] = ryuSheet.crop(255, 216, 60, 64);
		disAdvanFrames[5] = ryuSheet.crop(317, 216, 60, 64);
		disAdvanFrames[6] = ryuSheet.crop(378, 216, 60, 64);
		disAdvanFrames[7] = ryuSheet.crop(439, 216, 60, 64);

		// win frames
		BufferedImage[] winFrames = new BufferedImage[4];
		winFrames[0] = ryuSheet.crop(130, 935, 74, 64);
		winFrames[1] = ryuSheet.crop(206, 935, 95, 64);
		winFrames[2] = ryuSheet.crop(304, 935, 95, 64);
		winFrames[3] = ryuSheet.crop(400, 935, 95, 64);

		// lose frames
		BufferedImage[] loseFrames = new BufferedImage[2];
		// loseFrames[0] = ryuSheet.crop(5, 1004, 59, 63);
		loseFrames[0] = ryuSheet.crop(70, 1004, 74, 64);
		loseFrames[1] = ryuSheet.crop(145, 1004, 74, 64);

		allAnimations.put("ryuIdle", idleFrames);
		allAnimations.put("ryuAdvantage", advanFrames);
		allAnimations.put("ryuDisAdvantage", disAdvanFrames);
		allAnimations.put("ryuWin", winFrames);
		allAnimations.put("ryuLose", loseFrames);

		// Ken Animations
		SpriteSheet kenSheet = new SpriteSheet("/ken.png");

		// idle frames
		BufferedImage[] idleFrames1 = new BufferedImage[4];
		idleFrames1[0] = horizontalFlip(kenSheet.crop(5, 4, 63, 64));
		idleFrames1[1] = horizontalFlip(kenSheet.crop(74, 4, 63, 64));
		idleFrames1[2] = horizontalFlip(kenSheet.crop(143, 4, 63, 64));
		idleFrames1[3] = horizontalFlip(kenSheet.crop(210, 4, 63, 64));

		// advantage frames
		BufferedImage[] advanFrames1 = new BufferedImage[4];
		advanFrames1[0] = horizontalFlip(kenSheet.crop(7, 73, 50, 69));
		advanFrames1[1] = horizontalFlip(kenSheet.crop(68, 73, 50, 69));
		advanFrames1[2] = horizontalFlip(kenSheet.crop(127, 73, 50, 69));
		advanFrames1[3] = horizontalFlip(kenSheet.crop(184, 73, 50, 69));

		// disAdvantage frames
		BufferedImage[] disAdvanFrames1 = new BufferedImage[4];
		disAdvanFrames1[0] = horizontalFlip(kenSheet.crop(4, 149, 57, 73));
		disAdvanFrames1[1] = horizontalFlip(kenSheet.crop(67, 149, 57, 73));
		disAdvanFrames1[2] = horizontalFlip(kenSheet.crop(132, 149, 57, 73));
		disAdvanFrames1[3] = horizontalFlip(kenSheet.crop(197, 149, 57, 73));

		// win frames
		BufferedImage[] winFrames1 = new BufferedImage[6];
		// winFrames1[0] = horizontalFlip(kenSheet.crop(5, 708, 57, 76));
		// winFrames1[1] = horizontalFlip(kenSheet.crop(69, 708, 57, 76));
		// winFrames1[2] = horizontalFlip(kenSheet.crop(132, 708, 57, 76));
		winFrames1[0] = horizontalFlip(kenSheet.crop(196, 708, 58, 76));
		winFrames1[1] = horizontalFlip(kenSheet.crop(262, 708, 58, 76));
		winFrames1[2] = horizontalFlip(kenSheet.crop(329, 708, 58, 76));
		winFrames1[3] = horizontalFlip(kenSheet.crop(398, 708, 58, 76));
		winFrames1[4] = horizontalFlip(kenSheet.crop(464, 708, 58, 76));
		winFrames1[5] = horizontalFlip(kenSheet.crop(527, 708, 58, 76));

		// lose frames
		BufferedImage[] loseFrames1 = new BufferedImage[2];
		loseFrames1[0] = horizontalFlip(kenSheet.crop(4, 940, 64, 58));
		loseFrames1[1] = horizontalFlip(kenSheet.crop(77, 940, 64, 58));

		allAnimations.put("kenIdle", idleFrames1);
		allAnimations.put("kenAdvantage", advanFrames1);
		allAnimations.put("kenDisAdvantage", disAdvanFrames1);
		allAnimations.put("kenWin", winFrames1);
		allAnimations.put("kenLose", loseFrames1);

		/* TEXT ANIMATIONS */
		SpriteSheet hud2Sheet = new SpriteSheet("/HUD2.png");

		// ko animation
		BufferedImage[] koFrames = new BufferedImage[7];
		koFrames[0] = hud2Sheet.crop(38, 293, 258, 190);
		koFrames[1] = hud2Sheet.crop(296, 293, 258, 190);
		koFrames[2] = hud2Sheet.crop(535, 293, 258, 190);
		koFrames[3] = hud2Sheet.crop(754, 293, 207, 190);
		koFrames[4] = hud2Sheet.crop(947, 293, 195, 190);
		koFrames[5] = hud2Sheet.crop(1127, 293, 171, 190);
		koFrames[6] = hud2Sheet.crop(1288, 293, 163, 190);
		
		allAnimations.put("ko", koFrames);

		// win animation
		BufferedImage[] winTextFrames = new BufferedImage[4];
		winTextFrames[0] = hud2Sheet.crop(20, 509, 92, 63);
		winTextFrames[1] = hud2Sheet.crop(20, 586, 92, 63);
		winTextFrames[2] = hud2Sheet.crop(20, 649, 92, 33);
		winTextFrames[3] = hud2Sheet.crop(20, 699, 92,18);
		
		allAnimations.put("win", winTextFrames);

		// lose animation
		BufferedImage[] loseTextFrames = new BufferedImage[4];
		loseTextFrames[0] = hud2Sheet.crop(124, 509, 92, 63);
		loseTextFrames[1] = hud2Sheet.crop(124, 586, 92, 63);
		loseTextFrames[2] = hud2Sheet.crop(124, 649, 92, 33);
		loseTextFrames[3] = hud2Sheet.crop(124, 699, 92, 18);
		
		allAnimations.put("lose", loseTextFrames);
		
		/*TTILE LOGO ANIMATION*/
		SpriteSheet hudSheet = new SpriteSheet("/HUD.png");
		
		//title logo
		BufferedImage[] titleFrames = new BufferedImage[2];
		titleFrames[0] = hudSheet.crop(6, 146, 96, 48);
		titleFrames[1] = hudSheet.crop(6, 201, 96, 48);
		
		allAnimations.put("title", titleFrames);
		
	}

	
	/**
	 * loads the audio files
	 */
	private static void loadAudio() {
		
		allSounds.put("menuBGM", new AudioFile(AudioLoader.loadAudioInputStream("/bgm/menuBGM.wav")));
		allSounds.put("stageBGM", new AudioFile(AudioLoader.loadAudioInputStream("/bgm/stageBGM.wav")));
		
		allSounds.put("counterGemPlace", new AudioFile(AudioLoader.loadAudioInputStream("/sfx/counterGemPlace.wav")));
		allSounds.put("counterToNormalGem", new AudioFile(AudioLoader.loadAudioInputStream("/sfx/counterToNormalGem.wav")));
		allSounds.put("fight", new AudioFile(AudioLoader.loadAudioInputStream("/sfx/fight.wav")));
		allSounds.put("notify", new AudioFile(AudioLoader.loadAudioInputStream("/sfx/notify.wav")));
		allSounds.put("gemBreak", new AudioFile(AudioLoader.loadAudioInputStream("/sfx/gemBreak.wav")));
		allSounds.put("gemPairMove", new AudioFile(AudioLoader.loadAudioInputStream("/sfx/gemPairMove.wav")));
		allSounds.put("gemPlace", new AudioFile(AudioLoader.loadAudioInputStream("/sfx/gemPlace.wav")));
		allSounds.put("kenAdvantage", new AudioFile(AudioLoader.loadAudioInputStream("/sfx/kenAdvantage.wav")));
		allSounds.put("kenWin", new AudioFile(AudioLoader.loadAudioInputStream("/sfx/kenWin.wav")));
		allSounds.put("ko", new AudioFile(AudioLoader.loadAudioInputStream("/sfx/ko.wav")));
		allSounds.put("ready", new AudioFile(AudioLoader.loadAudioInputStream("/sfx/ready.wav")));
		allSounds.put("ryuAdvantage", new AudioFile(AudioLoader.loadAudioInputStream("/sfx/ryuAdvantage.wav")));
		allSounds.put("ryuWin", new AudioFile(AudioLoader.loadAudioInputStream("/sfx/ryuWin.wav")));
		allSounds.put("versus", new AudioFile(AudioLoader.loadAudioInputStream("/sfx/versus.wav")));
		}

	/**
	 * horizontally flips an image
	 * @param img	the image to flip
	 * @return the flipped image
	 */
	private static BufferedImage horizontalFlip(BufferedImage img) {
		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage flippedImage = new BufferedImage(w, h, img.getType());
		Graphics2D g = flippedImage.createGraphics();
		g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null);
		g.dispose();
		return flippedImage;
	}

	//getters
	
	public static AudioFile getAudioFile(String key) {
		return allSounds.get(key);
	}

	public static BufferedImage getImage(String key) {
		return allImages.get(key);
	}

	public static BufferedImage[] getFrames(String key) {
		return allAnimations.get(key);
	}

	/*
	 * public void loadFont(String path) {
	 * 
	 * }
	 * 
	 * public Font getFont(String key) {
	 * 
	 * }
	 */
}
