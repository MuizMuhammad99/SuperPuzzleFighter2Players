package com.game.puzzlefighter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.image.BufferStrategy;
import java.awt.image.VolatileImage;

import com.game.input.KeyManager;
import com.game.sfx.SoundPlayer;
import com.game.window.MainWindow;

/**
 * Game Class An important class which defines the main game engine/framework of
 * the game. It is responsible for creating game,rendering and updating the
 * game.
 */
public abstract class Game implements Runnable {

	protected static MainWindow mainFrame;
	protected static GameConfigurations gameConfigurations;

	protected Graphics2D graphics;
	private BufferStrategy bs;
	private GraphicsConfiguration gc;
	private VolatileImage vImage;
	protected Thread gameThread;
	protected int fps;

	public static final KeyManager keyManager = new KeyManager();
	public static final SoundPlayer soundPlayer = new SoundPlayer();

	/**
	 * Initializes the game according to the configurations
	 * 
	 * @param config game configurations
	 */
	public final void initialize(GameConfigurations config) {
		gameConfigurations = config;
		this.fps = config.getFps();

		// set up main frame
		mainFrame = new MainWindow(config.getTitle(), config.getGameWidth(), config.getGameHeight(),
				config.fullScreen(), config.resizeable());
		mainFrame.getCanvas().setBackground(Color.BLACK);
		gc = mainFrame.getCanvas().getGraphicsConfiguration();
		mainFrame.getFrame().addComponentListener(new ComponentListener() {

			@Override
			public void componentHidden(ComponentEvent arg0) {
			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
			}

			@Override
			public void componentResized(ComponentEvent c) {
				// update volatile image whenever window gets resized
				if (!gameConfigurations.isScaling())
					vImage = gc.createCompatibleVolatileImage(getWidth(), getHeight());

			}

			@Override
			public void componentShown(ComponentEvent arg0) {
			}

		});
		addKeyAdapter(keyManager);
	}

	/**
	 * creates game objects
	 */
	public abstract void create();

	/**
	 * update game objects
	 * 
	 * @param delta delta frame
	 */
	public abstract void update(float delta);

	/**
	 * renders the game
	 */
	public abstract void render();

	/**
	 * pre-renders the game i.e clears the screen so objects can be rendered on it.
	 * It also creats and validates the vImage if it is invalid or null.
	 */
	private void preRender() {
		if (vImage == null) {
			gc = mainFrame.getCanvas().getGraphicsConfiguration();
			vImage = gc.createCompatibleVolatileImage(gameConfigurations.getGameWidth(),
					gameConfigurations.getGameHeight());
		}
		if (vImage.validate(gc) == VolatileImage.IMAGE_INCOMPATIBLE) {
			vImage = gc.createCompatibleVolatileImage(gameConfigurations.getGameWidth(),
					gameConfigurations.getGameHeight());
		}
		graphics = (Graphics2D) vImage.getGraphics();
		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, getWidth(), getHeight());

	}

	/**
	 * Shows the rendered screen. It also scales the image according to the screen
	 * if scaling is enabled.
	 */
	private void show() {
		graphics.dispose();
		if (bs == null) {
			mainFrame.getCanvas().createBufferStrategy(3);
			bs = mainFrame.getCanvas().getBufferStrategy();
		}
		graphics = (Graphics2D) bs.getDrawGraphics();

		// scale the image to aspect ration, if scaling is enabled
		if (gameConfigurations.isScaling()) {
			int width = getWidth(), height = getHeight();

			double thumbRatio = (double) width / (double) height;
			int imageWidth = vImage.getWidth();
			int imageHeight = vImage.getHeight();
			double aspectRatio = (double) imageWidth / (double) imageHeight;

			if (thumbRatio < aspectRatio) {
				height = (int) (width / aspectRatio);
			} else {
				width = (int) (height * aspectRatio);
			}

			graphics.drawImage(vImage, (getWidth() - width) / 2, (getHeight() - height) / 2, width, height, null);
		} else
			graphics.drawImage(vImage, 0, 0, null);

		// dispose and draw
		graphics.dispose();
		bs.show();

	}

	/**
	 * start game thread
	 */
	public synchronized void start() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		create();
		double tickPerTime = 1000000000 / fps;
		long lastTime = System.nanoTime();
		long lastUpdateTime = System.nanoTime();
		long now = 0;
		long timer = 0;
		int ticks = 0;

		int updates = 0;
		int maxUpdates = 5;

		// game loop
		boolean running = true;
		while (running) {
			now = System.nanoTime();
			timer += (now - lastTime);

			updates = 0;
			while (now - lastUpdateTime >= tickPerTime) {
				float delta = (now - lastUpdateTime) / 1000000000.0f;
				keyManager.update();
				delta = delta <= 0.016f ? delta : 0.016f;
				update(delta);
				lastUpdateTime += tickPerTime;
				updates++;

				if (updates > maxUpdates) {
					break;
				}
			}

			preRender();
			render();
			show();
			ticks++;
			lastTime = now;

			long timeTake = System.nanoTime() - now;
			if (tickPerTime > timeTake)
				try {
					Thread.sleep((long) ((tickPerTime - timeTake) / 1000000f));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			if (timer >= 1000000000) {
				ticks = 0;
				timer = 0;
			}

		}

	}

	/**
	 * adds key adapter to the game
	 * 
	 * @param e key adapater
	 */
	public void addKeyAdapter(KeyAdapter e) {
		mainFrame.getCanvas().addKeyListener(e);
		mainFrame.getFrame().addKeyListener(e);
	}
	
	//getters and setters

	public static int getWidth() {
		return mainFrame.getCanvas().getWidth();
	}

	public static int getHeight() {
		return mainFrame.getCanvas().getHeight();
	}

	public static int getGameWidth() {
		return gameConfigurations.getGameWidth();
	}

	public static int getGameHeight() {
		return gameConfigurations.getGameHeight();
	}

}
