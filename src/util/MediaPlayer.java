package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Queue;

import fx.Sound;

public class MediaPlayer implements Runnable {

	private Queue<Sound> soundQueue;

	private boolean running;

	public MediaPlayer(boolean running) {
		this.soundQueue = new LinkedList<Sound>();
		this.running = running;
	}

	@Override
	public void run() {
		while (this.running) {
			int size = this.soundQueue.size();
			for(int e = 0; e < size; e++) {
				Sound sound = this.soundQueue.poll(); // retrieves and removes sound
				sound.play();
			}
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				try {
					e.printStackTrace(new PrintStream(new File("bug.txt")));
				} catch (FileNotFoundException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	public void addSoundToQueue(Sound sound) {
		this.soundQueue.add(sound);
	}
	
	public synchronized void start() {
		synchronized (this) {
			new Thread(this).start();
		}
	}

}
