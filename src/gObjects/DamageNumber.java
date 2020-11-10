package gObjects;

import java.awt.Color;
import java.awt.Graphics;

public class DamageNumber {

	private int val;

	private int x, y, travelDist, totalDist;

	public DamageNumber(int val, int x, int y) {
		this.val = val;
		this.x = x;
		this.y = y;
		this.travelDist = 25;
		this.totalDist = 0;
	}
	
	public void tick() {
		this.y--;
		this.totalDist++;
	}

	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.drawString("+" + this.val, x, y);
	}
	
	public boolean hasTraveledDist() {
		return totalDist >= travelDist;
	}

}
