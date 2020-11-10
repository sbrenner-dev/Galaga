package gObjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;

import fx.Sound;
import main.GalagaGame;
import main.Launcher;
import resLoader.ImageHandler;

public class SpaceShip {

	public static int WIDTH = 40;

	public static boolean CAN_SHOOT = true;

	public static final int FULL_HEALTH = 200;

	public static final String TYPE = "SPACESHIP";

	public static final String FILE = "ship.png";

	private int x;

	private int y;

	private GalagaGame gameInstance;

	private int health;

	private int dx;

	private LinkedList<Bullet> bullets;

	private BufferedImage image;

	private Sound boost;

	private int score;

	public SpaceShip(int x, int y, GalagaGame game) {

		this.x = x;
		this.y = y;
		this.gameInstance = game;
		this.dx = 5;

		this.health = 200;

		this.bullets = new LinkedList<Bullet>();

		this.image = ImageHandler.loadAndResize(SpaceShip.FILE, SpaceShip.WIDTH,
				SpaceShip.WIDTH);

		this.boost = new Sound();
		this.boost.setFile(SpaceShip.class.getResource("/boost.wav"));
		this.boost.setVolume(0.05f);

	}

	public void boost() {
		this.boost.play();
	}

	public void addToScore(int a) {
		this.score += a;
	}
	
	public int getScore() {
		return score;
	}

	public void move(int direction) {
		this.x += direction * this.dx;
		if (this.x < 0) {
			this.x = 0;
		} else if (this.x > 482 - SpaceShip.WIDTH) {
			this.x = 482 - SpaceShip.WIDTH;
		}
	}
	
	public void incScore() {
		this.score++;
	}

	public void tick() {

		if (this.gameInstance.getEnemies().size() == 0) {
			SpaceShip.CAN_SHOOT = false;
			if (this.bullets.size() == 0 && Wave.BULLETS.size() == 0) {
				this.gameInstance.getEnemyWave().clearBullets();
				this.gameInstance.endRound();
			}
		}

		Iterator<Bullet> itr = this.bullets.iterator();
		while (itr.hasNext()) {
			Bullet b = null;
			try {
				b = itr.next();
			} catch (ConcurrentModificationException e) {
				e.toString();
				e.printStackTrace();
				break;
			}

			if (b != null) {
				b.move(-1);
				if (b.getY() + 2 * Bullet.WIDTH < 0) {
					try {
						itr.remove();
					} catch (IllegalStateException | ConcurrentModificationException e) {
						e.printStackTrace();
					}
				}

				// Check intersection with enemy ships

				LinkedList<EnemyShip> enemies = this.gameInstance.getEnemies();

				Iterator<EnemyShip> itr2 = enemies.iterator();

				while (itr2.hasNext()) {
					EnemyShip enemy = itr2.next();
					if (enemy != null && enemy.getBounds().intersects(b.getBounds())) {
						enemy.damage(Bullet.DAMAGE);
						try {
							if(b != null) {
								itr.remove();
							}
							if (enemy.getHealth() <= 0) {
								int x = enemy.getX();
								int y = enemy.getY();
								enemy.getMyWave().addExplosion(x, y);
								addToScore(EnemyShip.ELIM_RWRD);
								itr2.remove();
							}
						} catch (IllegalStateException
								| ConcurrentModificationException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	public void resetHealth() {
		this.health = SpaceShip.FULL_HEALTH;
	}

	public void shoot() {
		this.bullets.add(new Bullet(this.x + SpaceShip.WIDTH - 3 * Bullet.WIDTH / 2,
				this.y - Bullet.HEIGHT / 2, SpaceShip.TYPE, 10));
	}

	public void stop() {
		this.dx = 5;
	}

	public void draw(Graphics g) {
		g.drawImage(this.image, this.x, this.y, null);

		if (Launcher.SHOW_DEBUG_STATS) {
			g.setColor(java.awt.Color.GREEN);
			g.drawRect(this.x + SpaceShip.WIDTH / 4, this.y + SpaceShip.WIDTH / 4,
					SpaceShip.WIDTH / 2 - 2, SpaceShip.WIDTH / 2);
		}

		for (Bullet b : this.bullets) {
			b.draw(g);
		}
	}

	public void accelerate() {
		if (this.dx <= 10) {
			this.dx++;
		}
	}

	public Rectangle getBounds() {
		return new Rectangle(this.x + SpaceShip.WIDTH / 4, this.y + SpaceShip.WIDTH / 4,
				SpaceShip.WIDTH / 2 - 2, SpaceShip.WIDTH / 2);
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

	public int getSpeed() {
		return this.dx;
	}

}
