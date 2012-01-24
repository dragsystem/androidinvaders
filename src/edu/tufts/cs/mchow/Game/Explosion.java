package edu.tufts.cs.mchow.Game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import edu.tufts.cs.mchow.R;

public class Explosion extends GameSprite {
	int frame;
	
public Explosion(GameEngine gameEngine, double x, double y) {
		super(gameEngine, x, y);
		this.setImage(R.drawable.explosion);
		width = width/25;
		frame = 0;
		this.set((float) x, (float) y, (float) (width + x),
				(float) (height + y));
	}

	@Override
	public void draw(Canvas c) {
		if(!active)
			return;
		Rect src = new Rect(width*frame, 0, width*(++frame), height);
		RectF dst = new RectF((float) (x * convertW),
				(float) (y * convertH), (float) ((x + width) * convertW),
				(float) ((y + height) * convertH));
		Paint p = new Paint();
		p.reset();
		c.drawBitmap(this.getImage(), src, dst, p);
		if(frame==25)
			active = false;
	}

	@Override
	public void hit() {
		//should never be called
	}

	@Override
	public void update() {
		//updating is done in draw()
	}

}
