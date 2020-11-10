package runner;

import java.util.Timer;
import java.util.TimerTask;

import main.GalagaGame;

public class GameRunner implements Runnable {
	
	public static double MEMORY_USAGE;

	public static int FPS = 30;
	
	private static Runtime RT = Runtime.getRuntime();
	
	private static final double DUMP_THRHLD = 100; // MB
	
	static {
		MEMORY_USAGE = (RT.totalMemory() - RT.freeMemory()) / 1000000.0;
	}

	/**
	 * Refresh time in ms
	 */
	private static final int REFRESH_TIME = 1000 / GameRunner.FPS;

	/**
	 * Running status of the while loop in the run method
	 */
	private boolean running;

	/**
	 * {@code GalagaGame} through which to repaint on the {@code JPanel}
	 */
	private GalagaGame game;
	
	private TimerTask updateFPS;
	
	private int frameCount;
	
	//private boolean maxFPS;

	/**
	 * Constructs this {@code Runnable}
	 * 
	 * @param running Initial running status for when a Thread is started
	 * @param game    {@code GalagaGame} through which to tick on the {@code JPanel}
	 */
	public GameRunner(boolean running, GalagaGame game) {
		this.running = running;
		this.game = game;
		this.frameCount = 0;
		this.updateFPS = new TimerTask() {
			
			@Override
			public void run() {
				FPS = GameRunner.this.frameCount;
				GameRunner.this.frameCount = 0;
			}
		};
		
		Timer t = new Timer();
		t.schedule(updateFPS, 1000, 1000);
	}

	/**
	 * Overriden method to run a Thread
	 */
	@Override
	public void run() {

		while (running) {

			game.tick();
			
			MEMORY_USAGE = (RT.totalMemory() - RT.freeMemory()) / 1000000.0;
			
			if(MEMORY_USAGE > DUMP_THRHLD) {
				RT.gc();
			}
			
			this.frameCount++;

			try {
				Thread.sleep(GameRunner.REFRESH_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Changes the running status of the {@link #run()} method
	 * 
	 * @param running updated running status
	 */
	public synchronized void stop() {
		synchronized (this) {
			this.running = false;
		}
	}

}
