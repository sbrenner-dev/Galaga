package fx;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class FireBall {

	public static final int WIDTH = 2;

	public static final Color[] COLORS = { Color.RED, Color.ORANGE, Color.YELLOW,
			Color.BLUE, Color.GREEN, Color.MAGENTA };

	private int x;

	private int y;

	private int initX, initY;

	private int dirX;

	private int dirY;

	private int dx;

	private int dy;

	private Color color;

	public FireBall(int x, int y) {
		this.x = x;
		this.y = y;

		this.initX = x;
		this.initY = y;

		Random r = new Random();

		int c = r.nextInt(3);

		this.dirX = c == 0 ? 0 : c == 1 ? 1 : -1;

		c = r.nextInt(3);

		this.dirY = c == 0 ? 0 : c == 1 ? 1 : -1;

		if (this.dirX == 0 || this.dirY == 0) {
			this.dirX = r.nextInt(2) == 0 ? 1 : -1;
		}

		this.color = COLORS[r.nextInt(COLORS.length)];

		this.dx = r.nextInt(10) + 5;
		this.dy = r.nextInt(10) + 5;

	}

	public void draw(Graphics g) {
		g.setColor(this.color);
		g.fillRect(this.x, this.y, FireBall.WIDTH, FireBall.WIDTH);
	}

	public void move() {
		this.x += this.dirX * this.dx;
		this.y += this.dirY * this.dy;
	}

	public boolean outOfRange(int r) {
		Random myR = new Random();
		int rand = myR.nextInt(r / 5);
		rand *= myR.nextInt(2) == 1 ? -1 : 1;
		return Math.sqrt(Math.pow(initX - x, 2) + Math.pow(initY - y, 2)) >= r + rand;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

}
