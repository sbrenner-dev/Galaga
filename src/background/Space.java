package background;

import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import main.GalagaGame;

/**
 * Background scenery rendered to the main {@code Thread} in the
 * {@code GalagaGame} instance of this game
 * 
 * @author Samuel Brenner
 *
 */

public class Space {

	public static final int SIZE = 50;

	private LinkedList<Star> stars;

	public Space() {
		this.stars = new LinkedList<Star>();

		this.populate();

	}

	private void populate() {
		Random r = new Random();

		for (int i = 0; i < Space.SIZE; i++) {
			this.stars.add(new Star(r.nextInt(GalagaGame.WIDTH - Star.WIDTH),
					r.nextInt(GalagaGame.HEIGHT) - Star.WIDTH));
		}
	}

	public void tick() {

		int removed = 0;

		// tick all the stars downwards
		Iterator<Star> itr = this.stars.iterator();
		while (itr.hasNext()) {
			Star star = itr.next();
			star.move();
			if (star.getY() > GalagaGame.HEIGHT) {
				itr.remove();
				removed++;
			}
		}

		/*
		 * re-add any stars that were removed off the screen, to keep a constant number
		 * of stars on the screen
		 */
		for (int i = 0; i < removed; i++) {
			this.stars.add(
					new Star(new Random().nextInt(GalagaGame.WIDTH - Star.WIDTH), 0));
		}
	}

	public void draw(Graphics g) {
		for (Star star : this.stars) {
			star.draw(g);
		}
	}

	public void setSpeeds(int speed) {
		for (Star star : this.stars) {
			star.setSpeed(speed);
		}
	}

}
