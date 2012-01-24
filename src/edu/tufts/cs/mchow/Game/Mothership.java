package edu.tufts.cs.mchow.Game;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import edu.tufts.cs.mchow.R;

public class Mothership extends GameSprite {
	private static final int SHIP_SPEED = 3;
	private static final int frames = 5;
	private int dir, currentFrame;

	public Mothership(GameEngine ge, int x, int y) {
		super(ge, x, y);
		active = true;
		Random r = new Random();
		dir = (r.nextInt(2) == 0) ? 1 : -1;
		super.x = (dir == 1) ? 0 : gameEngine.FRAME_WIDTH;
		super.y = 10;
		points = 20;
		this.setImage(R.drawable.mothership);
		currentFrame = 0;
		width = width/frames;
		this.set(x, y, (width + x), (height + y));
	}

	@Override
	public void draw(Canvas c) {
		if (active) {
			currentFrame = (currentFrame+5-dir)%frames;
			Rect src = new Rect(currentFrame*width, 0, (currentFrame+1)*width, height);
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

	@Override
	public void hit() {
		active = false;
		gameEngine.addToScore(points);
		gameEngine.makePowerup(x, y);
	}

	@Override
	public void update() {
		if (this.x >= -60 && this.x <= gameEngine.FRAME_WIDTH && active) {
			x += SHIP_SPEED * dir;
			this.set((float) x, (float) y, (float) (width + x),
					(float) (height + y));
		} else {
			active = false;
		}
	}

}
