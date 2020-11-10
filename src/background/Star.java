package background;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Star {
	
	public static final int WIDTH = 3;
	
	public static Color[] COLORS = {Color.WHITE, Color.DARK_GRAY, Color.GRAY, Color.LIGHT_GRAY};
	
	public static int SPEED = 2; // 2 px / tick
	
	private int x;
	
	private int y;
	
	private Color color;
	
	public Star(int x, int y) {
	
		this.x = x;
		
		this.y = y;
		
		this.color = Star.COLORS[new Random().nextInt(Star.COLORS.length)];
		
	}
	
	public void draw(Graphics g) {
		g.setColor(this.color);
		g.fillRect(this.x, this.y, Star.WIDTH, Star.WIDTH);
	}
	
	public void move() {
		this.y += Star.SPEED;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	public void setSpeed(int speed) {
		Star.SPEED = speed;
	}

}
