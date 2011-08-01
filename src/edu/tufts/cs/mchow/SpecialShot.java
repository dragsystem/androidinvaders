package edu.tufts.cs.mchow;

import android.graphics.Canvas; //import android.graphics.Color;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class SpecialShot extends GameSprite {
	protected static final int size = 5;

	public SpecialShot(GameEngine ge, double x, double y) {
		super(ge, x, y);
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
			//p.setColor(Color.RED);
			//c.drawRect(this, p);
		}
	}

	
	@Override
	public void hit() {
	}

	public void hit(double x, double y) {
	}

	@Override
	public void update() {
	}

}
