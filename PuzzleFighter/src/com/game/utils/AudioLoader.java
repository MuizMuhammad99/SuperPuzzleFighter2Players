package com.game.utils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;


/**
 * loads audio input stream for the audio file
 */
public class AudioLoader {
    public static AudioInputStream loadAudioInputStream(String path) {
        AudioInputStream stream = null;
        try {
            stream = AudioSystem.getAudioInputStream(AudioLoader.class.getResource(path));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stream;
    }
}
