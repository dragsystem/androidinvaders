package edu.tufts.cs.mchow.Aliens;

import java.util.Random;

import android.graphics.Canvas;

import edu.tufts.cs.mchow.R;
import edu.tufts.cs.mchow.Game.GameEngine;

public class Alien5 extends Alien {

	public Alien5(GameEngine ge, double x, double y) {
		super(ge, x, y);
		hp = MAX_HP = 2;
		points = 10;
		type = 5;
		this.setImage(R.drawable.alien5);
		frames = 11;
		Random r = new Random();
		curFrame = r.nextInt(frames);
		width = width/frames;
		this.set((float) x, (float) y, (float) (width + x),
				(float) (height + y));
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
