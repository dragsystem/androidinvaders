package edu.tufts.cs.mchow;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class Box extends GameSprite {

	public Box(GameEngine gameEngine, double x, double y) {
		super(gameEngine, x, y);
		this.setImage(BitmapFactory.decodeResource(this.getGameEngine()
				.getContext().getResources(), R.drawable.boxes));
		this.setHp(15);
		width = width/hp;
		this.set((float) x, (float) y, (float) (width + x),
				(float) (height + y));
	}

	@Override
	public void draw(Canvas c) {
		if (active) {
			Rect src = new Rect(width*(15-hp), 0, width*(16-hp), height);
			RectF dst = new RectF((float) (x * convertW),
					(float) (y * convertH), (float) ((x + width) * convertW),
					(float) ((y + height) * convertH));
			Paint p = new Paint();
			p.reset();
			c.drawBitmap(this.getImage(), src, dst, p);
		}
	}

	@Override
	public void hit() {
		if (active) {
			hp--;
			if (hp == 0) {
				active = false;
			}
		}
	}

	@Override
	public void update() {
		// only called when hit from below by player
		y -= 10;
		this.set((float) x, (float) y, (float) (width + x),
				(float) (height + y));
	}

}
