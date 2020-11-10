package gObjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import main.GalagaGame;
import main.Launcher;
import resLoader.ImageHandler;

public class EnemyShip {

	private static boolean CAN_SHOOT = true;

	public static int ELIM_RWRD = 400; // score given to player once this is eliminated

	public static final String TYPE = "ENEMYSHIP";

	public static final String FILE = "enemy.png";
	
	public static final BufferedImage IMAGE = ImageHandler.loadAndResize(EnemyShip.FILE, EnemyShip.WIDTH,
			EnemyShip.WIDTH);

	public static final int WIDTH = 40;

	private int mvmntDir;

	private int x;

	private int y;

	private int descY;

	private GalagaGame gameInstance;

	private Wave myWave;

	private int dx;

	private int health;

	private boolean descending;

	private LinkedList<Bullet> bullets;

	private int bulletSpeed;

	private double probability;

	public EnemyShip(int x, int y, int descY, GalagaGame game, int bulletSpeed,
			double probability, Wave wave) {
		this.x = x;
		this.y = y;
		this.dx = 1;
		this.gameInstance = game;
		this.descY = descY;
		this.bulletSpeed = bulletSpeed;
		this.probability = probability;
		this.myWave = wave;

		this.descending = true;

		this.bullets = new LinkedList<Bullet>();

		// override velocity

		this.dx = 1;

		this.mvmntDir = new Random().nextInt(2) == 0 ? 1 : -1; // either move forwards or
																// backwards once
																// descended

		this.health = 10;
	}

	public void tick() {

		if (this.gameInstance.goodGuy() != null) {

			// Random seeder for computer actions

			Random r = new Random();

			// Actual Movement for this ship

			if (this.descending) {
				this.y += 2;
				if (this.y >= this.descY) {
					this.descending = false;
				}
			}

			this.move();

			Iterator<EnemyShip> shipItr = this.gameInstance.getEnemies().iterator();
			while (shipItr.hasNext()) {
				EnemyShip enemy = null;

				try {
					enemy = shipItr.next();
				} catch (ConcurrentModificationException e) {
					e.printStackTrace();
				}

				if (enemy != this && this.getBounds().intersects(enemy.getBounds())) {
					this.y += this.y > enemy.getY() ? 1 : -1;
					this.x += this.x > enemy.getX() ? 1 : -1;
				}
			}

			if (r.nextInt((int) (1 / this.probability)) == 0 && CAN_SHOOT) {
				Bullet.SOUND.setVolume(0.01f);
				this.shoot();
			}
		}

	}

	public void shoot() {
		Wave.BULLETS.add(new Bullet(this.x - 3 * Bullet.WIDTH / 2 + EnemyShip.WIDTH,
				this.y, EnemyShip.TYPE, this.bulletSpeed));
	}

	public Rectangle getBounds() {
		return new Rectangle(this.x + EnemyShip.WIDTH / 4 - 2,
				this.y + EnemyShip.WIDTH / 4 - 2, EnemyShip.WIDTH / 2 + 2,
				EnemyShip.WIDTH / 2 - 5);
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
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

	public void clearBullets() {
		this.bullets.clear();
	}

	public void damage(int damage) {
		this.health -= damage;
	}

	public int getHealth() {
		return this.health;
	}

	public void move() {
		this.x += this.mvmntDir * this.dx;
		if (this.x < 0) {
			this.x = 0;
			this.mvmntDir = -this.mvmntDir;
			if (!this.descending && new Random().nextInt(5) == 0) {
				this.y++;
			}
		} else if (this.x > 482 - EnemyShip.WIDTH) {
			this.x = 482 - EnemyShip.WIDTH;
			this.mvmntDir = -this.mvmntDir;
			if (!this.descending && new Random().nextInt(5) == 0) {
				// 1/5 chance of descending once it reaches its "lowest" point and hits a
				// wall
				this.y++;
			}
		} else if (this.x + EnemyShip.WIDTH == this.gameInstance.goodGuy().getX()
				|| this.x == this.gameInstance.goodGuy().getX() + EnemyShip.WIDTH) {
			// This makes it so the player cannot simply sit still and fire
			this.mvmntDir = -this.mvmntDir;
		}
	}

	public void draw(Graphics g) {
		g.drawImage(IMAGE, this.x, this.y, null);

		if (Launcher.SHOW_DEBUG_STATS) {
			g.setColor(java.awt.Color.RED);
			g.drawRect(this.x + EnemyShip.WIDTH / 4 - 2, this.y + EnemyShip.WIDTH / 4 - 2,
					EnemyShip.WIDTH / 2 + 2, EnemyShip.WIDTH / 2 - 5);
		}
	}

	public int getDescY() {
		return this.descY;
	}

	public Wave getMyWave() {
		return myWave;
	}

}
