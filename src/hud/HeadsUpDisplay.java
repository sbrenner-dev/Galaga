package hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import gObjects.SpaceShip;
import main.GalagaGame;
import main.Launcher;
import runner.GameRunner;
import util.GameTimer;

/**
 * Basic Heads Up Display (HUD)
 * <p>
 * Will store information such as health and points
 * 
 * @author Samuel Brenner
 *
 */

public class HeadsUpDisplay {

	private int x;

	private int y;

	private HealthBar playerHealth;

	private GalagaGame instance;

	public HeadsUpDisplay(int x, int y, GalagaGame instance) {
		this.x = x;
		this.y = y;
		this.instance = instance;

		this.playerHealth = new HealthBar(SpaceShip.FULL_HEALTH, this.x + 50,
				this.y - 15);
	}

	public void draw(Graphics g) {

		g.setColor(Color.GREEN);

		g.setFont(new Font("TimesRoman", Font.BOLD, 20));

		g.drawString(convertTo10Dig(this.instance.goodGuy().getScore()),
				0, 20);

		g.setFont(new Font("TimesRoman", Font.BOLD, 10));

		g.drawString("Health", this.x, this.y);

		this.playerHealth.draw(g);

		if (Launcher.SHOW_DEBUG_STATS) {
			g.setColor(Color.GREEN);
			g.drawString("Memory: " + GameRunner.MEMORY_USAGE + " MB",
					4 * GalagaGame.WIDTH / 5 - 50, 660);
			g.drawString("FPS: " + GameRunner.FPS, 4 * GalagaGame.WIDTH / 5 - 50, 670);
			g.drawString("Elapsed Time: " + GameTimer.CURRENT_ELAPSED_TIME + " s",
					4 * GalagaGame.WIDTH / 5 - 50, 680);
		}

	}

	public void updatePlayerHealth(int health) {
		this.playerHealth.updatePlayerHealth(health);
	}

	public void reset() {
		this.playerHealth.reset();
	}

	private String convertTo10Dig(int num) {
		String tenDig = "";

		while (num > 0) {
			tenDig = (num % 10) + tenDig;
			num /= 10;
		}

		int rem = 10 - tenDig.length();

		for (int i = 0; i < rem; tenDig = "0" + tenDig, i++)
			;

		return tenDig;
	}

}
