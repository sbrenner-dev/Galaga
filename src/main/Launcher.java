package main;

public class Launcher {
	
	public static final long START_TIME = System.currentTimeMillis();
	
	public static boolean SHOW_DEBUG_STATS = false;
	
	public static void main(String[] args) {
		new GalagaGame("Galaga");
	}

}
