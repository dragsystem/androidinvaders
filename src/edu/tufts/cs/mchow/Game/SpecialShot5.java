package edu.tufts.cs.mchow.Game;

import edu.tufts.cs.mchow.R;
import edu.tufts.cs.mchow.Aliens.Alien;

public class SpecialShot5 extends SpecialShot {
	private static final int SHOT_SPEED = 9;
	private Alien target;

	public SpecialShot5(GameEngine ge, double x, double y, Alien target) {
		super(ge, x, y);
		this.setImage(R.drawable.ball_green);
		this.set((float) x, (float) y, (float) (size + x), (float) (size + y));
		this.target = target;
	}

	@Override
	public void update() {
		if(!target.isActive()) {active = false;}
		if(!active) {return;}
		
		double diffX = target.x - x;
		double diffY = target.y - y;
		double convert = SHOT_SPEED / Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));
		if (this.y > 0 && this.y < gameEngine.FRAME_HEIGHT && this.x > 0 && this.x < gameEngine.FRAME_WIDTH) {
			x += diffX * convert;
			y += diffY * convert;
			this.set((float) x, (float) y, (float) (size + x),
					(float) (size + y));
		} else {
			active = false;
		}
	}
	
	@Override
	public void hit(double x, double y) {
		active = false;
	}

	public void hit(Alien baddie) {
		if(baddie.equals(target)) {
			baddie.hit();
			hit(baddie.getX() + baddie.getWidth() / 2, baddie.getY()
					+ baddie.getHeight() / 2);
			gameEngine.explosions.add(new Explosion(gameEngine, baddie.getX(), baddie.getY()));
		}
	}
}
