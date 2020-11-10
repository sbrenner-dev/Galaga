package fx;

import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;

import gObjects.SpaceShip;

public class Explosion {

	public static final int SIZE = 120;

	private LinkedList<FireBall> balls;
	
	private Sound explosion;
	
	private int radius;

	public Explosion(int x, int y, int radius) {
		this.balls = new LinkedList<FireBall>();

		this.populate(x, y);

		this.radius = radius;
		
		this.explosion = new Sound();
		this.explosion.setFile(getClass().getResource("/explosion.wav"));
		this.explosion.setVolume(0.1f);
	}

	public void tick() {
		Iterator<FireBall> itr = this.balls.iterator();
		while (itr.hasNext()) {
			FireBall ball = itr.next();
			ball.move();
			if(ball.outOfRange(this.radius)) {
				itr.remove();
			}
		}
	}
	
	public void playSound() {
		this.explosion.play();
	}
	
	public int size() {
		return this.balls.size();
	}

	public void draw(Graphics g) {
		for (FireBall ball : this.balls) {
			ball.draw(g);
		}
	}

	private void populate(int x, int y) {
		for (int i = 0; i < Explosion.SIZE; i++) {
			this.balls.add(new FireBall(x + SpaceShip.WIDTH / 2, y + SpaceShip.WIDTH / 2));
		}
	}

}
