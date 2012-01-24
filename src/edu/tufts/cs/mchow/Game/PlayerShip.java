package edu.tufts.cs.mchow.Game;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import edu.tufts.cs.mchow.R;

public class PlayerShip extends GameSprite {
	private static final int INVINCIBLE_FRAMES = 50;
	private int invincibleFramesLeft;
	private boolean visible;
	private boolean shield;
	private int moveAmount;
	private int frames, currentFrame;
	private int sFrames, sCurFrame;
	private BitmapDrawable shieldPic;

	public PlayerShip(GameEngine ge) {
		super(ge);
		this.setImage(R.drawable.ship1);
		shieldPic = new BitmapDrawable(ge.getContext().getResources(), 
				BitmapFactory.decodeResource(ge.getContext().getResources(), R.drawable.shield));
		visible = true;
		shield = false;
		invincibleFramesLeft = 0;
		x = gameEngine.FRAME_WIDTH / 2;
		y = gameEngine.FRAME_HEIGHT - 40;
		this.set((float) x, (float) y, (float) (x + width),
				(float) (y + height));
		frames = 8;
		sFrames = 20;
		currentFrame = sCurFrame = 0;
	}

	public void reset() {
		invincibleFramesLeft = INVINCIBLE_FRAMES;
	}

	@Override
	public void draw(Canvas c) {
		currentFrame = (currentFrame+1)%frames;
		sCurFrame = (sCurFrame+1)%sFrames;
		int vert = Math.abs(frames/2-currentFrame);
		int sAlpha = 200-3*(Math.abs(sFrames/2-sCurFrame));
		double newY = y+vert/2;
		if(gameEngine.respawning || gameEngine.movingUp)
			return;
		if (invincibleFramesLeft > 0) {
			visible = !visible;
			invincibleFramesLeft--;
		} else
			visible = true;
		if (visible) {
			RectF dst = new RectF((float) (x * convertW),
					(float) (newY * convertH), (float) ((x + width) * convertW),
					(float) ((newY + height) * convertH));
			Rect sDst = new Rect((int) ((x-4) * convertW),
					(int) ((newY-4) * convertH), (int) ((x+4 + width) * convertW),
					(int) ((newY+1 + height) * convertH));
			Paint p = new Paint();
			p.reset();
			c.drawBitmap(this.getImage(), null, dst, p);
			if(shield) {
				shieldPic.setAlpha(sAlpha);
				shieldPic.setBounds(sDst);
				shieldPic.draw(c);
			}
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
				return;
			}
			gameEngine.decreaseLives();
			this.reset();
			gameEngine.setDoubleShot(false);
			if(gameEngine.boss != null) {
				gameEngine.respawning = gameEngine.movingUp = true;
				gameEngine.moveUpFrames = 0;
			}
		}
	}

	public void shieldOn() {
		shield = true;
	}
	
	public boolean isShieldOn() {
		return shield;
	}
}
