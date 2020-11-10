package gObjects;

import java.awt.Graphics;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import fx.Explosion;
import main.GalagaGame;

public class Wave {

	public static LinkedList<Bullet> BULLETS = new LinkedList<Bullet>();

	private static final int EXPL_RAD = 40;

	private LinkedList<Explosion> explosions;

	private LinkedList<EnemyShip> enemies;

	private LinkedList<DamageNumber> damageNumbers;

	private GalagaGame gameInstance;

	/**
	 * Constructs this {@code Wave} of {@code EnemyShip} objects
	 * 
	 * @param num number of {@link EnemyShip} objects in this {@link Wave}
	 */
	public Wave(int num, GalagaGame game, int bulletSpeed, double probability) {

		this.enemies = new LinkedList<EnemyShip>();

		this.explosions = new LinkedList<Explosion>();

		this.damageNumbers = new LinkedList<DamageNumber>();

		this.gameInstance = game;

		this.initiateWave(num, game, bulletSpeed, probability);

	}

	private void initiateWave(int num, GalagaGame game, int bulletSpeed,
			double probability) {

		Random r = new Random();

		for (int i = 0; i < num; i++) {

			int x = r.nextInt(GalagaGame.WIDTH - SpaceShip.WIDTH);
			int y = -SpaceShip.WIDTH;
			int descY = r.nextInt(2 * GalagaGame.HEIGHT / 3);

			this.enemies.add(
					new EnemyShip(x, y, descY, game, bulletSpeed, probability, this));

		}

	}

	public void tick() {

		// tick enemies

		for (EnemyShip enemy : this.enemies) {
			enemy.tick();
		}

		// tick explosions

		Iterator<Explosion> eItr = this.explosions.iterator();
		while (eItr.hasNext()) {
			Explosion e = eItr.next();
			e.tick();
			if (e.size() == 0) {
				e.tick();
				eItr.remove();
			}
		}
		
		// tick damage numbers
		
		Iterator<DamageNumber> dItr = this.damageNumbers.iterator();
		while(dItr.hasNext()) {
			DamageNumber d = dItr.next();
			d.tick();
			if(d.hasTraveledDist()) {
				d.tick();
				dItr.remove();
			}
		}

		// tick bullets

		Iterator<Bullet> itr = Wave.BULLETS.iterator();
		while (itr.hasNext()) {
			Bullet b = null;

			try {
				b = itr.next();
			} catch (ConcurrentModificationException e) {
				e.printStackTrace();
			}

			b.move(1);

			if (b.getY() > GalagaGame.HEIGHT) {
				try {
					itr.remove();
				} catch (ConcurrentModificationException | IllegalStateException e) {
					e.printStackTrace();
				}
			}

			// Check intersection with good guy ship

			SpaceShip goodGuy = this.gameInstance.goodGuy();

			if (goodGuy != null && goodGuy.getBounds().intersects(b.getBounds())) {
				try {
					itr.remove();
				} catch (ConcurrentModificationException | IllegalStateException e) {
					e.printStackTrace();
				}
				goodGuy.damage(Bullet.DAMAGE);
				this.gameInstance.updateHUDHealth(goodGuy.getHealth());
				if (goodGuy.getHealth() <= 0) {
					this.gameInstance.destroyShip();
				}
			}

		}

	}

	public void draw(Graphics g) {

		for (EnemyShip enemy : this.enemies) {
			enemy.draw(g);
		}

		for (Bullet b : Wave.BULLETS) {
			b.draw(g);
		}

		for (Explosion explosion : this.explosions) {
			explosion.draw(g);
		}
		
		for(DamageNumber d : this.damageNumbers) {
			d.draw(g);
		}

	}

	public LinkedList<EnemyShip> getEnemies() {
		return this.enemies;
	}

	public void removeEnemy(EnemyShip enemy) {
		this.enemies.remove(enemy);
	}

	public void clearBullets() {
		Wave.BULLETS.clear();
	}

	public void addExplosion(int x, int y) {
		this.explosions.add(new Explosion(x, y, Wave.EXPL_RAD));
		addDamageNUmber(x + EnemyShip.WIDTH / 2, y);
	}

	private void addDamageNUmber(int x, int y) {
		this.damageNumbers.add(new DamageNumber(EnemyShip.ELIM_RWRD, x, y));
	}

}
