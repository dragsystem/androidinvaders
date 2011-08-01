package edu.tufts.cs.mchow;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class PlayerShip extends GameSprite {
	private static final int INVINCIBLE_FRAMES = 50;
	private int invincibleFramesLeft;
	private boolean visible;
	private boolean shield;
	private int moveAmount;

	public PlayerShip(GameEngine ge) {
		super(ge);
		this.setImage(BitmapFactory.decodeResource(ge.getContext()
				.getResources(), R.drawable.ship1));
		visible = true;
		shield = false;
		invincibleFramesLeft = 0;
		x = gameEngine.FRAME_WIDTH / 2;
		y = gameEngine.FRAME_HEIGHT - 40;
		this.set((float) x, (float) y, (float) (x + width),
				(float) (y + height));
	}

	public void reset() {
		invincibleFramesLeft = INVINCIBLE_FRAMES;
	}

	@Override
	public void draw(Canvas c) {
		if(gameEngine.respawning || gameEngine.movingUp)
			return;
		if (invincibleFramesLeft > 0) {
			visible = !visible;
			invincibleFramesLeft--;
		} else
			visible = true;
		if (visible) {
			RectF dst = new RectF((float) (x * convertW),
					(float) (y * convertH), (float) ((x + width) * convertW),
					(float) ((y + height) * convertH));
			Paint p = new Paint();
			p.reset();
			c.drawBitmap(this.getImage(), null, dst, p);
			// For debugging
			/*
			 * p.setColor(Color.BLUE); c.drawRect(this, p);
			 */
		}
	}

	public void move(int amt) {
		moveAmount = amt;
	}

	@Override
	public void update() {
		if(gameEngine.respawning)
			return;
		x += moveAmount;
		if (x < 0)
			x = 0;
		if (x > gameEngine.FRAME_WIDTH - width)
			x = gameEngine.FRAME_WIDTH - width;

		this.set((float) x, (float) y, (float) (width + x),
				(float) (height + y));
	}

	@Override
	public void hit() {
		if (invincibleFramesLeft == 0) {
			if (shield) {
				shield = false;
				this.setImage(BitmapFactory.decodeResource(gameEngine
						.getContext().getResources(), R.drawable.ship1));
				return;
			}
			gameEngine.decreaseLives();
			this.reset();
			gameEngine.setDoubleShot(false);
			gameEngine.respawning = gameEngine.movingUp = true;
			gameEngine.moveUpFrames = 0;
		}
	}

	public void shieldOn() {
		this.setImage(BitmapFactory.decodeResource(gameEngine.getContext()
				.getResources(), R.drawable.ship1b));
		shield = true;
	}
	
	public boolean isShieldOn() {
		return shield;
	}
}
