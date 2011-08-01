package edu.tufts.cs.mchow;

import java.util.Random;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Alien2 extends Alien {

	public Alien2(GameEngine ge, double x, double y) {
		super(ge, x, y);
		hp = 1;
		points = 10;
		this.setImage(BitmapFactory.decodeResource(ge.getContext()
				.getResources(), R.drawable.alien3));
		frames = 9;
		Random r = new Random();
		curFrame = r.nextInt(frames);
		width = width/frames;
		this.set((float) x, (float) y, (float) (width + x),
				(float) (height + y));
	}

	@Override
	public void hit() {
		super.hit();
		if (gameEngine.alienType == 3)
			gameEngine.numInARow++;
		else if (gameEngine.numInARow < 4) {
			gameEngine.numInARow = 1;
			gameEngine.alienType = 3;
		}
	}
	
	@Override
	public void draw(Canvas c) {
		super.draw(c);
		if (curFrame == 0)
			frameDir = 1;
		else if (curFrame == frames-1)
			frameDir = -1;
		curFrame+=frameDir;
	}
}
