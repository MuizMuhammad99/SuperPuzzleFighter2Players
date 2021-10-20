package com.game.sfx;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;

/**
 * An audio file which can be played.
 *
 */
public class AudioFile {

	private AudioInputStream stream;
	private Clip clip;
	private DataLine.Info info;
	
	
	/**
	 * Constructor
	 * @param stream	audio input stream
	 */
	public AudioFile(AudioInputStream stream) {
		this.stream = stream;
		this.info = new DataLine.Info(Clip.class, this.stream.getFormat());
		
		try {
			this.clip = (Clip) AudioSystem.getLine(info);
			clip.open(this.stream);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 
	 * @return true if audio file is played
	 */
	public boolean isPlayed() {
		return clip.getMicrosecondPosition() == clip.getMicrosecondLength();
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
