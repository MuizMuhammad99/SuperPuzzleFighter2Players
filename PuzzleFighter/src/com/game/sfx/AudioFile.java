package com.game.sfx;

import javax.sound.sampled.*;
import java.io.IOException;

/**
 * An audio file which can be played.
 */
public class AudioFile {

    private Clip clip;


    /**
     * Constructor
     *
     * @param stream audio input stream
     */
    public AudioFile(AudioInputStream stream) {
        DataLine.Info info = new DataLine.Info(Clip.class, stream.getFormat());

        try {
            this.clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * plays the audio file
     */
    public void play() {
        clip.setFramePosition(0);
        clip.start();
    }

    /**
     * loops the audio file
     */
    public void loop() {
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * stops the audio file
     */
    public void stop() {
        clip.stop();
        clip.flush();
    }

    /**
     * set volume of audio in decibel unit
     *
     * @param value value
     */
    public void setVolume(float value) {
        FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(value);

    }

    /**
     * reset the audio
     */
    public void reset() {
        clip.setFramePosition(0);
    }
}
