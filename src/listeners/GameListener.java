package listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import gObjects.SpaceShip;
import main.GalagaGame;
import main.Launcher;

public class GameListener extends MouseAdapter implements KeyListener {

	private GalagaGame game;

	private boolean[] pressed;

	public GameListener(GalagaGame game) {
		this.game = game;
		this.pressed = new boolean[2];
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1 && SpaceShip.CAN_SHOOT) {
			this.game.goodGuy().shoot();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
			case KeyEvent.VK_D:
				this.pressed[1] = true;
				this.pressed[0] = false;
				break;
			case KeyEvent.VK_A:
				this.pressed[0] = true;
				this.pressed[1] = false;
				break;
			case KeyEvent.VK_Q:
				Launcher.SHOW_DEBUG_STATS = !Launcher.SHOW_DEBUG_STATS;
				break;
			default:
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE && SpaceShip.CAN_SHOOT) {
			this.game.goodGuy().shoot();
		}

		int code = e.getKeyCode();
		if (code == KeyEvent.VK_D || code == KeyEvent.VK_A) {
			this.game.goodGuy().stop();
			if (code == KeyEvent.VK_D) {
				this.pressed[1] = false;
			} else {
				this.pressed[0] = false;
			}
		}
	}

	public void tick() {

		SpaceShip ship = this.game.goodGuy();

		if (this.pressed[0]) {
			ship.move(-1);
			ship.accelerate();
		} else if (this.pressed[1]) {
			ship.move(1);
			ship.accelerate();
		}
	}

}
