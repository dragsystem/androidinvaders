package edu.tufts.cs.mchow.Aliens;

import java.util.Random;

import edu.tufts.cs.mchow.Game.GameEngine;
import edu.tufts.cs.mchow.Game.GameSprite;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class Alien extends GameSprite {
	private boolean moveRight;
	private double moveAmt;
	protected int frames, curFrame, frameDir;
	protected int type;

	public Alien(GameEngine ge, double x, double y) {
		super(ge, x, y);
		moveRight = true;
		moveAmt = 10;
		frameDir = 1;
	}

	@Override
	public void draw(Canvas c) {
		if (active) {
			Rect src = new Rect(width*curFrame, 0, width*(curFrame+1), height);
			RectF dst = new RectF((float) (x * convertW),
					(float) (y * convertH), (float) ((x + width) * convertW),
					(float) ((y + height) * convertH));
			Paint p = new Paint();
			p.reset();
			c.drawBitmap(this.getImage(), src, dst, p);
			// For debugging
			//p.setColor(Color.GREEN);
			//c.drawRect(this, p);
		}
	}

	public void fire() {
		int chance = 2 ^ gameEngine.getBaddiesLeft() * 40;
		Random r = new Random();
		if (r.nextInt(chance) == 0)
			gameEngine.alienFire(x + width / 2, y);
	}

	@Override
	public void update() {
		if (gameEngine.loadingEnemies)
			return;
		if (moveRight)
			x += moveAmt;
		else
			x -= moveAmt;

		this.set((float) x, (float) y, (float) (width + x),
				(float) (height + y));
		if (active)
			fire();
	}

	public void moveDown() {
		y += 8;
	}

	public void setMoveAmt(double move) {
		moveAmt = move;
	}

	public void changeDir() {
		moveRight = !moveRight;
	}

	@Override
	public void hit() {
		if (active) {
			hp--;
			if (hp == 0) {
				active = false;
				gameEngine.addToScore(points);
			}
		}
		int bl = gameEngine.getBaddiesLeft();
		if (!active && ((bl == 1 && gameEngine.getLevelNum() != 9) || bl == 13)) {
			gameEngine.addMothership();
		}

		if (gameEngine.alienType == type)
			gameEngine.numInARow++;
		else if (gameEngine.numInARow < 4) {
			gameEngine.numInARow = 1;
			gameEngine.alienType = type;
		}
	}
	
	public int getType() {
		return type;
	}
}
