package hud;

import java.awt.Color;
import java.awt.Graphics;

import gObjects.SpaceShip;

public class HealthBar {

	public static final int WIDTH = 50;

	public static final int HEIGHT = 20;

	public static final int BAR_INDENT = 1;

	public static final int STEP = 30;

	private int displayHealth;

	private int x;

	private int y;

	private int R, G, B;

	public HealthBar(int initialHealth, int x, int y) {
		this.displayHealth = initialHealth;
		this.x = x;
		this.y = y;

		this.R = 50;

		this.G = 255;

		this.B = 0;
	}

	public void draw(Graphics g) {

		int px = (int) ((1. * this.displayHealth) / SpaceShip.FULL_HEALTH * HealthBar.WIDTH + 1);

		if (px == 1) {
			px = 0;
		}

		// Border
		
		g.setColor(Color.WHITE);

		g.drawRect(this.x, this.y, HealthBar.WIDTH + 2 * HealthBar.BAR_INDENT,
				HealthBar.HEIGHT + 2 * HealthBar.BAR_INDENT);
		
		// Black backdrop

		g.setColor(Color.BLACK);

		g.fillRect(this.x + HealthBar.BAR_INDENT, this.y + HealthBar.BAR_INDENT, HealthBar.WIDTH + 1,
				HealthBar.HEIGHT + 1);

		// Health fill
		
		g.setColor(new Color(this.R, this.G, this.B));

		g.fillRect(this.x + HealthBar.BAR_INDENT, this.y + HealthBar.BAR_INDENT, px, HealthBar.HEIGHT + 1);

	}

	public void updatePlayerHealth(int health) {
		this.displayHealth = health;
		if (this.R < 255) {
			this.R += HealthBar.STEP;
			if (this.R > 255) {
				this.R = 255;
			}
		} else if (this.G > 0) {
			this.G -= HealthBar.STEP;
			if (this.G < 0) {
				this.G = 0;
			}
		}
	}

	public void reset() {
		this.displayHealth = SpaceShip.FULL_HEALTH;
		this.R = 50;
		this.G = 255;
		this.B = 0;
	}

}
