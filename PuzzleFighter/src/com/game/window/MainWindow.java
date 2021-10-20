package com.game.window;

import javax.swing.*;
import java.awt.*;

/**
 * Game window
 */
public class MainWindow {

    private final JFrame frame;
    private final Canvas canvas;

    /**
     * Constructor
     *
     * @param name       title
     * @param width      width
     * @param height     height
     * @param fullScreen make full screen flag
     * @param resizeable is resize-able
     */
    public MainWindow(String name, int width, int height, boolean fullScreen, boolean resizeable) {
        frame = new JFrame(name);

        canvas = new Canvas();
        canvas.setSize(width, height);
        frame.setSize(width, height);
        frame.setResizable(resizeable);
        frame.add(canvas);
        if (fullScreen) makeFullScreen();

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
    }

    /*
     * makes full screen
     */
    private void makeFullScreen() {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = env.getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            frame.setUndecorated(true);
            frame.setResizable(false);
            frame.setIgnoreRepaint(true);
            gd.setFullScreenWindow(frame);
        }
    }

    //getters and setters

    public JFrame getFrame() {
        return frame;
    }

    public Canvas getCanvas() {
        return canvas;
    }

}
