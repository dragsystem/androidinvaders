package edu.tufts.cs.mchow;

import android.graphics.BitmapFactory;

public class SpecialShot4 extends SpecialShot {
	private static final double SHOT_SPEED = 9;
	private static final double SHOT_SPREAD = SHOT_SPEED*1.3;
	private boolean spread;
	private int dir;
	private SpecialShot4 partner;

	public SpecialShot4(GameEngine ge, double x, double y, int direction) {
		super(ge, x, y);
		dir = direction;
		spread = false;
		//if (dir == 1) {
			this.setImage(BitmapFactory.decodeResource(ge.getContext()
					.getResources(), R.drawable.shot5));
			this.set((float) x, (float) y, (float) (size + x),
					(float) (size + y));
//		} else {
//			this.setImage(BitmapFactory.decodeResource(ge.getContext()
//					.getResources(), R.drawable.shot5c));
//			this.set((float) x, (float) y, (float) (size + x),
//					(float) (size + y));
//		}
	}

	public void setPartner(SpecialShot4 p) {
		partner = p;
	}
	
	@Override
	public void hit(double alienX, double alienY) {
		if (!spread) {
			x = alienX;
			y = alienY;
			spread = true;
			if(dir==1) {
				this.setImage(BitmapFactory.decodeResource(gameEngine.getContext()
						.getResources(), R.drawable.shot5b));
				this.set((float) (x + width - size), (float) y,
						(float) (width + x), (float) (size + y));
			} else {
				this.setImage(BitmapFactory.decodeResource(gameEngine.getContext()
						.getResources(), R.drawable.shot5c));
				this.set((float) x, (float) y, (float) (size + x),
						(float) (size + y));
			}
			x-=width/2;
			if(partner!=null)
				partner.hit(alienX, alienY);
			//gameEngine.addSpecialShot(new SpecialShot4(gameEngine, x, y, -1));
			//x -= width;
		}
	}

	@Override
	public void update() {
		if (this.y > 0 && active) {
			if (spread)
				x += SHOT_SPREAD * dir;
			y -= SHOT_SPEED;
			if (dir == 1)
				this.set((float) (x + width - size), (float) y,
						(float) (width + x), (float) (size + y));
			else
				this.set((float) x, (float) y, (float) (size + x),
						(float) (size + y));
		} else {
			active = false;
		}
	}
}
