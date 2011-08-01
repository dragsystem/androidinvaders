package edu.tufts.cs.mchow;

import android.graphics.BitmapFactory;

public class SpecialShot1 extends SpecialShot {
	private static final int SHOT_SPEED = 9;

	public SpecialShot1(GameEngine ge, double x, double y) {
		super(ge, x, y);
		this.setImage(BitmapFactory.decodeResource(ge.getContext()
				.getResources(), R.drawable.shot2));
		this.set((float) x, (float) y, (float) (size + x), (float) (size + y));
	}

	@Override
	public void update() {
		if (this.y > 0 && active) {
			y -= SHOT_SPEED;
			this.set((float) x, (float) y, (float) (size + x),
					(float) (size + y));
		} else {
			active = false;
		}
	}
}
