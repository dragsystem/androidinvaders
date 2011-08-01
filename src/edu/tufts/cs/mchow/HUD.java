package edu.tufts.cs.mchow;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF; //import android.awt.AndroidGraphicsConfiguration;
import android.graphics.Paint;

public class HUD extends GameSprite {
	private Bitmap numbers;
	private Bitmap greenBox;
	private Bitmap blueBox;
	private Bitmap redBox;
	private Bitmap yellowBox;
	private Bitmap vertPic;
	private Bitmap leftPic;
	private Bitmap rightPic;
	private Bitmap splitPic;
	private int alienType;
	private int numInARow;

	public HUD(GameEngine gameEngine) {
		super(gameEngine);
		numbers = BitmapFactory.decodeResource(gameEngine.getContext()
				.getResources(), R.drawable.numbers);
		greenBox = BitmapFactory.decodeResource(gameEngine.getContext()
				.getResources(), R.drawable.box_green);
		blueBox = BitmapFactory.decodeResource(gameEngine.getContext()
				.getResources(), R.drawable.box_blue);
		redBox = BitmapFactory.decodeResource(gameEngine.getContext()
				.getResources(), R.drawable.box_red);
		yellowBox = BitmapFactory.decodeResource(gameEngine.getContext()
				.getResources(), R.drawable.box_yellow);
		vertPic = BitmapFactory.decodeResource(gameEngine.getContext()
				.getResources(), R.drawable.vert);
		leftPic = BitmapFactory.decodeResource(gameEngine.getContext()
				.getResources(), R.drawable.left);
		rightPic = BitmapFactory.decodeResource(gameEngine.getContext()
				.getResources(), R.drawable.right);
		splitPic = BitmapFactory.decodeResource(gameEngine.getContext()
				.getResources(), R.drawable.split);
		alienType = 0;
		numInARow = 0;
	}

	public void setHUD(int type, int num) {
		alienType = type;
		numInARow = num < 4 ? num : 4;
	}

	@Override
	public void draw(Canvas c) {
		int lives = gameEngine.getLives();
		for (int i = 1; i < 3; i++) {
			int digit = findDigit(lives, i);
			drawDigit(c, digit, 55 - i * 26, 3, false);
		}

		int score = gameEngine.getScore();
		for (int i = 1; i < 9; i++) {
			int digit = findDigit(score, i);
			drawDigit(c, digit,	(gameEngine.FRAME_WIDTH/2 + 26*(4-i)) * convertW,
					3 * convertH, false);
		}

		switch (alienType) {
		case 1:
			drawBox(c, greenBox, vertPic);
			break;
		case 2:
			drawBox(c, blueBox, leftPic);
			break;
		case 3:
			drawBox(c, redBox, rightPic);
			break;
		case 4:
			drawBox(c, yellowBox, splitPic);
			break;
		}
		
		if(gameEngine.loadingEnemies) {
			drawDigit(c, gameEngine.getLevelNum(), 258, 148, true);
		}
	}

	private void drawDigit(Canvas c, int num, float x, float y, boolean big) {
		Paint p = new Paint();
		p.reset();
		Rect src = new Rect(26*num, 0, 26*(num+1), 12);
		RectF dst;
		if(big) {
			dst = new RectF(x * convertW, y * convertH, (x + 52) * convertW,
					(y + 24) * convertH);
		}
		else {
			dst = new RectF(x * convertW, y * convertH, (x + 26) * convertW,
				(y + 12) * convertH);
		}
		c.drawBitmap(numbers, src, dst, p);
	}

	private void drawBox(Canvas c, Bitmap box, Bitmap text) {
		if (numInARow == 0)
			return;
		Paint p = new Paint();
		p.reset();
		RectF r = new RectF(466 * convertW, 3 * convertH,
				(466 + numInARow * 25) * convertW, 15 * convertH);
		c.drawBitmap(box, null, r, p);
		if(numInARow > 3) {
			RectF dst = new RectF(469*convertW, 17*convertH, 562*convertW, 24*convertH);
			c.drawBitmap(text, null, dst, p);
		}
	}

	public int findDigit(int num, int place) { // place 1 is the ones place, 2
												// is the tens, etc.
		double step1 = num / (Math.pow(10, place - 1));
		return ((int) step1) % 10;
	}

	@Override
	public void hit() {
	}

	@Override
	public void update() {
	}

}
