package gObjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import fx.Sound;
import main.GalagaGame;
import main.Launcher;
import resLoader.ImageHandler;

public class Bullet {

	public static final int DAMAGE = 10;

	public static final int WIDTH = 20;

	public static final int HEIGHT = 40;

	public static final Sound SOUND = new Sound();

	public static final BufferedImage BULLET_NORMAL = ImageHandler
			.loadAndResize("bullet.png", Bullet.WIDTH, Bullet.HEIGHT);

	public static final BufferedImage BULLET_FLIPPED = ImageHandler
			.loadAndResize("flippedBullet.png", Bullet.WIDTH, Bullet.HEIGHT - 5);

	private int x;

	private int dx;

	private BufferedImage image;

	static {

		Bullet.SOUND.setFile(GalagaGame.class.getResource("/GalagaShoot.wav"));
		Bullet.SOUND.setVolume(0.1f);

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

	private int y;

	public Bullet(int x, int y, String shooter, int speed) {

		if (shooter.equals(SpaceShip.TYPE)) {
			Bullet.SOUND.play();
		}

		this.x = x;
		this.y = y;
		this.dx = speed;

		this.selectImage(shooter);
	}

	private void selectImage(String shooter) {

		this.image = (shooter.equals(SpaceShip.TYPE)) ? Bullet.BULLET_NORMAL
				: Bullet.BULLET_FLIPPED;
	}

	public void draw(Graphics g) {

		g.drawImage(this.image, this.x, this.y, null);

		// hitbox

		if (Launcher.SHOW_DEBUG_STATS) {
			g.setColor(this.image == BULLET_NORMAL ? java.awt.Color.GREEN : java.awt.Color.RED);
			g.drawRect(this.x + Bullet.WIDTH / 4, this.y + Bullet.HEIGHT / 4, Bullet.WIDTH / 2 - 2, Bullet.HEIGHT / 2 - 5);
		}
	}

	public void move(int dir) {
		this.y += dir * this.dx;
	}

	public Rectangle getBounds() {
		return new Rectangle(this.x + Bullet.WIDTH / 4, this.y + Bullet.HEIGHT / 4,
				Bullet.WIDTH / 2 - 2, Bullet.HEIGHT / 2 - 5);
	}

}
