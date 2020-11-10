package fx;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {

	private Clip clip;

	public void setFile(URL name) {
		try {
			AudioInputStream sound = AudioSystem.getAudioInputStream(name);
			clip = AudioSystem.getClip();
			clip.open(sound);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void play() {
		clip.setFramePosition(0);

		/*
		 * Put playing sound on its own Thread as to minimize Sound inteference
		 */

		new Thread(() -> {
			clip.start();
		}).start();
	}

	/**
	 * Sets volume of {@code Sound} clip to percentage of initial volume
	 * 
	 * @param volume percentage of original volume as a float
	 */
	public void setVolume(float volume) {
		if (volume < 0f || volume > 1f) {
			throw new IllegalArgumentException("Volume not valid: " + volume);
		}
		FloatControl gainControl = (FloatControl) clip
				.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(20f * (float) Math.log10(volume));
	}

	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public int framesSize() {
		return this.clip.getFrameLength();
	}

	public int currentFrame() {
		return this.clip.getFramePosition();
	}

}
