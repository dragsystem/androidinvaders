package edu.tufts.cs.mchow;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class AlienShot extends GameSprite {
	private int id;
	private static final int SHOT_SPEED = 3;

	public AlienShot(GameEngine ge, double x, double y, int id) {
		super(ge, x, y);
		this.id = id;
		this.setImage(BitmapFactory.decodeResource(ge.getContext()
				.getResources(), R.drawable.ball_red));
		this.set((float) x, (float) y, (float) (width + x),
				(float) (height + y));
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
			c.drawBitmap(this.getImage(), src, dst, p);
			// For debugging
			// p.setColor(Color.RED);
			// c.drawRect(this, p);
		}
	}

	@Override
	public void update() {
		if (this.y < gameEngine.FRAME_HEIGHT && active) {
			y += SHOT_SPEED;
			this.set((float) x, (float) y, (float) (width + x),
					(float) (height + y));
		} else {
			active = false;
		}
	}

	@Override
	public void hit() {
		active = false;
	}

	@Override
	public boolean equals(Object o) {
		AlienShot s = (AlienShot) o;
		if (s.id == this.id) {
			return true;
		}
		return false;
	}
}
