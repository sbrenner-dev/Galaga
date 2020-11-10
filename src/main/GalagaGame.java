package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.net.URL;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import background.Space;
import fx.Explosion;
import fx.Sound;
import gObjects.EnemyShip;
import gObjects.SpaceShip;
import gObjects.Wave;
import hud.HeadsUpDisplay;
import listeners.GameListener;
import runner.GameRunner;
import util.GameTimer;

public class GalagaGame extends JPanel {

	// Public Members

	public static final int WIDTH = 500;

	public static final int HEIGHT = 750;

	public static int ENEMIES = 5; // can change

	// Private Members

	private static final long serialVersionUID = -3965915235204188194L;

	private String title;

	private JFrame frame;

	private SpaceShip goodGuy;

	private Thread thread;

	private Space space;

	private Explosion explosion;

	private Sound music;

	private Wave enemyWave;

	private HeadsUpDisplay hud;

	private GameTimer timer;
	
	private GameListener gListener;

	// Gameplay advancement

	private int enemyBulletSpeed;

	private double probability;

	private boolean show;

	/**
	 * Constructs this game and starts initialization
	 * 
	 * @param title Title for this Game (Galaga)
	 */
	public GalagaGame(String title) {

		this.title = title;

		this.init();

	}

	private void init() {

		// Internal Setup

		this.frame = new JFrame(this.title);
		this.frame.add(this);

		// Visual setup

		this.goodGuy = new SpaceShip(GalagaGame.WIDTH / 2 - 20, 600, this);
		this.enemyBulletSpeed = 10;
		this.probability = 0.05;
		this.enemyWave = new Wave(GalagaGame.ENEMIES, this, this.enemyBulletSpeed,
				this.probability);

		this.space = new Space();

		this.explosion = null;

		this.show = true;

		this.hud = new HeadsUpDisplay(GalagaGame.WIDTH / 20, 9 * GalagaGame.HEIGHT / 10, this);

//		/this.frame.addKeyListener(new MovementListener(this.goodGuy));
		
		this.gListener = new GameListener(this);
		
		this.frame.addMouseListener(this.gListener);
		this.frame.addKeyListener(this.gListener);

		this.setSize(new Dimension(GalagaGame.WIDTH, GalagaGame.HEIGHT));
		this.setVisible(true);

		this.frame.setSize(new Dimension(GalagaGame.WIDTH, GalagaGame.HEIGHT));
		this.frame.setLocationRelativeTo(null);
		this.frame.setVisible(true);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.requestFocus();

		// Audio Setup

		this.loadMusic();

		/**
		 * Thread instance starts here Not assigning for now, might change later
		 */

		this.thread = new Thread(new GameRunner(true, this));
		this.timer = new GameTimer(true);
		this.thread.start();
		this.timer.start();
	}

	/**
	 * Loads the background music for this {@code GalagaGame} instance
	 */
	public void loadMusic() {

		URL musicPath = GalagaGame.class.getResource("/galaxy-runner-dl-sounds.wav");
		this.music = new Sound();
		this.music.setFile(musicPath);
		this.music.setVolume(0.01f);
		this.music.play();
		this.music.loop();

	}

	public LinkedList<EnemyShip> getEnemies() {
		return this.enemyWave.getEnemies();
	}

	public void removeEnemy(EnemyShip enemy) {
		this.enemyWave.removeEnemy(enemy);
	}

	public SpaceShip goodGuy() {
		return this.goodGuy;
	}

	public void tick() {

		// Elapsed time is over

		if (this.timer.lappedTimeWithinRange(4)) {
			this.timer.resetLap();
			this.space.setSpeeds(2);
			this.show = true;
			this.reset();
		}

		// Normal ticking
		
		this.gListener.tick();

		if (this.explosion == null && this.show) {
			this.goodGuy.tick();
			this.enemyWave.tick();
		}
		
		this.goodGuy.incScore();

		if (this.explosion != null) {
			this.explosion.tick();
			if (this.explosion.size() == 0) {
				this.explosion.tick(); // one more time to get all Fireball objects off
										// the screen
				this.explosion = null;
				this.reset();
			}
		}

		this.space.tick();

		this.repaint();
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GalagaGame.WIDTH, GalagaGame.HEIGHT);
		this.space.draw(g);
		
		this.hud.draw(g);

		if (this.explosion == null) {
			this.goodGuy.draw(g);
		}

		if (this.explosion != null) {
			this.explosion.draw(g);
		}

		if (this.show) {
			this.enemyWave.draw(g);
		}
	}

	public void reset() {

		this.goodGuy.resetHealth();
		SpaceShip.CAN_SHOOT = true;

		this.enemyBulletSpeed += 5;
		GalagaGame.ENEMIES += 5;
		this.probability -= 0.01 * this.probability;

		if (this.explosion == null) {
			this.enemyWave = new Wave(GalagaGame.ENEMIES, this, this.enemyBulletSpeed,
					this.probability);
		}

		this.explosion = null;

		this.hud.reset();
	}

	public void endRound() {

		this.timer.lap();
		this.space.setSpeeds(30);
		this.show = false;
		this.goodGuy.boost();

		SpaceShip.CAN_SHOOT = false;

	}

	public void updateHUDHealth(int health) {
		this.hud.updatePlayerHealth(health);
	}

	public void destroyShip() {
		this.explosion = new Explosion(this.goodGuy.getX(), this.goodGuy.getY(), 70);
		this.explosion.playSound();
		this.enemyWave.clearBullets();
	}

	public Wave getEnemyWave() {
		return enemyWave;
	}

	public boolean getShow() {
		return this.show;
	}

}
