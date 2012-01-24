package edu.tufts.cs.mchow.Game;

import edu.tufts.cs.mchow.R;



public class SpecialShot2 extends SpecialShot {
	private static final int SHOT_SPEED = 9;
	private boolean goLeft;

	public SpecialShot2(GameEngine ge, double x, double y) {
		super(ge, x, y);
		this.setImage(R.drawable.shot3);
		this.set((float) x, (float) y, (float) (size + x), (float) (size + y));
		goLeft = false;
	}

	@Override
	public void hit(double alienX, double alienY) {
		if (!goLeft) {
			x = alienX;
			y = alienY + 7;
			this.set((float) x, (float) y, (float) (size + x),
					(float) (size + y));
			goLeft = true;
			this.setImage(R.drawable.shot3b);
		}
	}

	@Override
	public void update() {
		if (this.y > 0 && this.x > 0 && active) {
			if (!goLeft)
				y -= SHOT_SPEED;
			else
				x -= SHOT_SPEED;
			this.set((float) x, (float) y, (float) (size + x),
					(float) (size + y));
		} else {
			active = false;
		}
	}
}
