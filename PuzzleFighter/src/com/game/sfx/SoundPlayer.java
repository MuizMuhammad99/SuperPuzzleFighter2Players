package com.game.sfx;

import com.game.gfx.AssetManager;

/**
 * A sound plays which plays audio files for us in different streams. It runs in
 * its own thread.
 */
public class SoundPlayer implements Runnable {

    private AudioFile BGM;
    private AudioFile soundEffect, soundEffect2;

    private boolean playingBackGround;
    private boolean playingSoundEffect, playingSoundEffect2;

    public SoundPlayer() {
        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * plays a background music
     *
     * @param soundKey key of sound
     */
    public void playBackGroundMusic(String soundKey) {
        AudioFile audio = AssetManager.getAudioFile(soundKey);
        if (audio != null && audio != BGM) {
            stop();
            this.BGM = audio;
            BGM.setVolume(-8f);
            playingBackGround = true;
        }

    }

    /**
     * stops the background music
     */
    public void stop() {
        if (BGM != null) {
            BGM.stop();
            BGM.reset();
        }
    }

    @Override
    public void run() {
        while (true) {
            if (playingBackGround) {
                BGM.loop();
                playingBackGround = false;
            }
            if (playingSoundEffect) {
                soundEffect.play();
                playingSoundEffect = false;
            }

            if (playingSoundEffect2) {
                soundEffect2.play();
                playingSoundEffect2 = false;
            }
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    /**
     * plays sound effect
     *
     * @param soundkey key of sound
     */
    public void playSoundEffect(String soundkey) {
        AudioFile soundEffect = AssetManager.getAudioFile(soundkey);
        if (soundEffect != null) {
            if (this.soundEffect != null)
                this.soundEffect.reset();
            this.soundEffect = soundEffect;

            playingSoundEffect = true;
        }

    }

    /**
     * plays sound effect at second stream
     *
     * @param soundkey key of sound
     */
    public void playSoundEffect2(String soundkey) {
        AudioFile soundEffect = AssetManager.getAudioFile(soundkey);
        if (soundEffect != null) {
            if (this.soundEffect2 != null)
                this.soundEffect2.reset();
            this.soundEffect2 = soundEffect;

            playingSoundEffect2 = true;
        }

    }
}
