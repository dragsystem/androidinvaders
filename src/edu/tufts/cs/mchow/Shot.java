package edu.tufts.cs.mchow;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class Shot extends GameSprite {
	private int id;
	private static final int SHOT_SPEED = 9;

	public Shot(GameEngine ge, double x, double y, int id) {
		super(ge, x, y);
		this.id = id;
		this.setImage(BitmapFactory.decodeResource(ge.getContext()
				.getResources(), R.drawable.shot));
		this.set((float) x, (float) y, (float) (width + x),
						(float) (width + y));
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
		if (this.y > 0 && active) {
			y -= SHOT_SPEED;
			this.set((float) x, (float) y, (float) (width + x),
					(float) (width + y));
		} else {
			active = false;
		}
	}

	@Override
	public void hit() {
	}

	@Override
	public boolean equals(Object o) {
		Shot s = (Shot) o;
		if (s.id == this.id) {
			return true;
		}
		return false;
	}
}
