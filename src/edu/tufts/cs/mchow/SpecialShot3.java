package edu.tufts.cs.mchow;

import android.graphics.BitmapFactory;

public class SpecialShot3 extends SpecialShot {
	private static final int SHOT_SPEED = 9;
	private boolean goRight;

	public SpecialShot3(GameEngine ge, double x, double y) {
		super(ge, x, y);
		this.setImage(BitmapFactory.decodeResource(ge.getContext()
				.getResources(), R.drawable.shot4));
		this.set((float) x, (float) y, (float) (width + x),
				(float) (height + y));
		goRight = false;
	}

	@Override
	public void hit(double alienX, double alienY) {
		if (!goRight) {
			x = alienX;
			y = alienY + 7;
			this.set((float) (x + width - size), (float) y,
					(float) (width + x), (float) (size + y));
			goRight = true;
			this.setImage(BitmapFactory.decodeResource(gameEngine.getContext()
					.getResources(), R.drawable.shot4b));
		}
	}

	@Override
	public void update() {
		if (this.y > 0 && this.x < gameEngine.FRAME_WIDTH && active) {
			if (!goRight)
				y -= SHOT_SPEED;
			else
				x += SHOT_SPEED;
			this.set((float) (x + width - size), (float) y,
					(float) (width + x), (float) (size + y));
		} else {
			active = false;
		}
	}
}
