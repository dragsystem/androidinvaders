package edu.tufts.cs.mchow.Game;

import java.util.Random;

import android.graphics.Canvas;
import edu.tufts.cs.mchow.R;

public class Alien4 extends Alien {

	public Alien4(GameEngine ge, double x, double y) {
		super(ge, x, y);
		hp = MAX_HP = 1;
		points = 10;
		this.setImage(R.drawable.alien4);
		frames = 10;
		Random r = new Random();
		curFrame = r.nextInt(frames);
		width = width/frames;
		this.set((float) x, (float) y, (float) (width + x),
				(float) (height + y));
	}

	@Override
	public void hit() {
		super.hit();
		if (gameEngine.alienType == 4)
			gameEngine.numInARow++;
		else if (gameEngine.numInARow < 4) {
			gameEngine.numInARow = 1;
			gameEngine.alienType = 4;
		}
	}
	
	@Override
	public void draw(Canvas c) {
		super.draw(c);
		curFrame++;
		if(curFrame == frames)
			curFrame = 0;
	}
}
