package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import main.Launcher;

/**
 * Keeps track of elapsed time during an instance of {@code GalagaGame}
 * <p>
 * Uses: Timing specific event Score
 * 
 * @author Samuel Brenner
 *
 */

public class GameTimer implements Runnable {

	public static double CURRENT_ELAPSED_TIME = Launcher.START_TIME / 1000.0;

	private static double ERROR = 0.05;

	private static int DELAY_MS = 1;

	private double lappedTime;

	private boolean lapped;

	private boolean running;

	public GameTimer(boolean running) {
		this.lappedTime = 0;
		this.lapped = false;

		this.running = running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	@Override
	public void run() {
		while (this.running) {
			GameTimer.CURRENT_ELAPSED_TIME = (System.currentTimeMillis()
					- Launcher.START_TIME) / 1000.0;
			try {
				Thread.sleep(GameTimer.DELAY_MS);
			} catch (InterruptedException e) {
				try {
					e.printStackTrace(new PrintStream(new File("bug.txt")));
				} catch (FileNotFoundException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	public synchronized void lap() {
		synchronized (this) {

			if (this.lapped) {
				throw new UnsupportedOperationException("Timer is already lapping");
			}
			
			this.lappedTime = GameTimer.CURRENT_ELAPSED_TIME;
			this.lapped = true;
		}
	}

	public synchronized double getLappedTime() {
		synchronized (this) {
			return this.lappedTime;
		}
	}

	public synchronized boolean lappedTimeWithinRange(double elapsedTime) {
		synchronized (this) {
			double elapsed = GameTimer.CURRENT_ELAPSED_TIME - this.lappedTime;
			return this.lapped && (elapsed >= elapsedTime - GameTimer.ERROR
					&& elapsed <= elapsedTime + GameTimer.ERROR);
		}
	}

	public synchronized void resetLap() {
		synchronized (this) {
			this.lapped = false;
			this.lappedTime = 0;
		}
	}

	/**
	 * Starts this {@code Runnable} {@code GameTimer} on a {@code Thread} instance
	 */
	public void start() {
		new Thread(this).start();
	}

}
