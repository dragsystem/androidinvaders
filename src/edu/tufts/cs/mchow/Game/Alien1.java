package edu.tufts.cs.mchow.Game;

import java.util.Random;

import android.graphics.Canvas;
import edu.tufts.cs.mchow.R;

public class Alien1 extends Alien {

	public Alien1(GameEngine ge, double x, double y) {
		super(ge, x, y);
		hp = MAX_HP = 1;
		points = 10;
		this.setImage(R.drawable.alien1);
		frames = 11;
		Random r = new Random();
		curFrame = r.nextInt(frames);
		width = width/frames;
		this.set((float) x, (float) y, (float) (width + x),
				(float) (height + y));
	}

	@Override
	public void hit() {
		super.hit();
		if (gameEngine.alienType == 1)
			gameEngine.numInARow++;
		else if (gameEngine.numInARow < 4) {
			gameEngine.numInARow = 1;
			gameEngine.alienType = 1;
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
