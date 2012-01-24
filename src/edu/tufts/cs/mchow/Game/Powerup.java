package edu.tufts.cs.mchow.Game;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import edu.tufts.cs.mchow.R;

public class Powerup extends GameSprite {
	private static final int SHIELD = 0;
	private static final int DOUBLESHOT = 1;
//	private static final int FULL_ENERGY = 2;
//	private static final int FREEZE = 3;
	private int type;

	private static final int PSPEED = 4;
	private int framesLeft;

	public Powerup(GameEngine gameEngine, double x, double y) {
		super(gameEngine, x, y);
		active = true;
		Random r = new Random();
		type = r.nextInt(2);
		points = 0;
		framesLeft = 200;
		
		switch (type) {
		case SHIELD:
			this.setImage(R.drawable.pshield);
			break;
		case DOUBLESHOT:
			this.setImage(R.drawable.pdouble);
			break;
		}
		
		if(this.x<0)
			this.x=0;
		else if(this.x > gameEngine.FRAME_WIDTH - width)
			this.x = gameEngine.FRAME_WIDTH - width;
		this.set((float) x, (float) y,
				(float) (width + x), (float) (height + y));
	}

	@Override
	public void draw(Canvas c) {
		if (active) {
			Rect src = new Rect(0, 0, width, height);
			RectF dst = new RectF((float) (x * convertW),
					(float) (y * convertH), (float) ((x + width) * convertW),
					(float) ((y + height) * convertH));
			Paint p = new Paint();
			p.reset();
			p.setColor(Color.WHITE);
			p.setTypeface(Typeface.createFromAsset(gameEngine.getContext().getAssets(), "fonts/TECHNOID.TTF"));
			if(x > gameEngine.FRAME_WIDTH/2) {
				p.setTextAlign(Paint.Align.RIGHT);
			} else {
				p.setTextAlign(Paint.Align.LEFT);
			}
			p.setTextSize(20);
			c.drawBitmap(this.getImage(), src, dst, p);
			// For debugging
			// p.setColor(Color.GREEN);
			// c.drawRect(this, p);
			String text;
			switch (type) {
			case DOUBLESHOT:
				text = "DOUBLE";
				break;
			default:
				text = "SHIELD";
				break;
			}
			c.drawText(text, (float)(x+width/2)*convertW, (float)(y-10)*convertH, p);
		}
	}

	@Override
	public void hit() { // only called on collision with player
		switch (type) {
		case SHIELD:
			gameEngine.getPlayerShip().shieldOn();
			break;
		case DOUBLESHOT:
			gameEngine.setDoubleShot(true);
			break;
		}
		active = false;
	}

	@Override
	public void update() {
		if (this.y < gameEngine.FRAME_HEIGHT - 25 - PSPEED)
			y += PSPEED;
		else {
			y = gameEngine.FRAME_HEIGHT - 25;
		}
		this.set((float) x + width / 2 - 7, (float) y + height - 14,
				(float) (width / 2 + x + 7), (float) (height + y));
		framesLeft--;
		if (framesLeft == 0)
			active = false;
	}

}
