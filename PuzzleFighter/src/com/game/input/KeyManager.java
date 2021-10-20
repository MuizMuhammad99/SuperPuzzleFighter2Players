package com.game.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * KeyManager Class Manages all key events.
 */
public class KeyManager extends KeyAdapter {
    private final boolean[] keys;
    private final boolean[] keysJustPressed;
    private final boolean[] keysPressable;

    /**
     * Constructor
     */
    public KeyManager() {
        keys = new boolean[KeyEvent.RESERVED_ID_MAX];
        keysJustPressed = new boolean[KeyEvent.RESERVED_ID_MAX];
        keysPressable = new boolean[KeyEvent.RESERVED_ID_MAX];
    }

    // update just pressed keys
    public void update() {
        for (int i = 0; i < keys.length; i++) {
            if (!keysPressable[i] && !keys[i]) {
                keysPressable[i] = true;
            } else if (keysJustPressed[i]) {
                keysJustPressed[i] = false;
                keysPressable[i] = false;
            }
            if (keysPressable[i] && keys[i]) {
                keysJustPressed[i] = true;
            }
        }

    }

    /**
     * @param keyCode key code
     * @return true if the key is just pressed
     */
    public boolean isKeyJustPressed(int keyCode) {
        return keysJustPressed[keyCode];
    }

    /**
     * @return true if any key is just pressed
     */
    public boolean isAnyKeyJustPressed() {
        for (int i = 0; i < keys.length; i++)
            if (keysJustPressed[i])
                return true;

        return false;
    }

    /*	KEY ADAPTER FUNCTIONS	**/

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;

    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

}
