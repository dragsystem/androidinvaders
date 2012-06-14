package edu.tufts.cs.mchow.Game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import edu.tufts.cs.mchow.R;

public class HUD extends GameSprite {
	private Bitmap greenBox;
	private Bitmap blueBox;
	private Bitmap redBox;
	private Bitmap yellowBox;
	private Bitmap grayBox;
//	private Bitmap vertPic;
//	private Bitmap leftPic;
//	private Bitmap rightPic;
//	private Bitmap splitPic;
	private int alienType;
	private int numInARow;

	public HUD(GameEngine gameEngine) {
		super(gameEngine);
		greenBox = BitmapFactory.decodeResource(gameEngine.getContext()
				.getResources(), R.drawable.box_green);
		blueBox = BitmapFactory.decodeResource(gameEngine.getContext()
				.getResources(), R.drawable.box_blue);
		redBox = BitmapFactory.decodeResource(gameEngine.getContext()
				.getResources(), R.drawable.box_red);
		yellowBox = BitmapFactory.decodeResource(gameEngine.getContext()
				.getResources(), R.drawable.box_yellow);
		grayBox = BitmapFactory.decodeResource(gameEngine.getContext()
				.getResources(), R.drawable.box_gray);
//		vertPic = BitmapFactory.decodeResource(gameEngine.getContext()
//				.getResources(), R.drawable.vert);
//		leftPic = BitmapFactory.decodeResource(gameEngine.getContext()
//				.getResources(), R.drawable.left);
//		rightPic = BitmapFactory.decodeResource(gameEngine.getContext()
//				.getResources(), R.drawable.right);
//		splitPic = BitmapFactory.decodeResource(gameEngine.getContext()
//				.getResources(), R.drawable.split);
		alienType = 0;
		numInARow = 0;
	}

	public void setHUD(int type, int num) {
		alienType = type;
		numInARow = num < 4 ? num : 4;
	}

	@Override
	public void draw(Canvas c) {
		Paint p = new Paint();
		p.reset();
		p.setColor(Color.WHITE);
		p.setTypeface(Typeface.createFromAsset(gameEngine.getContext().getAssets(), "fonts/TECHNOID.TTF"));
		p.setTextAlign(Paint.Align.LEFT);
		p.setTextSize(26);
		
		int lives = gameEngine.getLives();
		c.drawText(Integer.toString(lives), 3f * convertW, 15f * convertH, p);

		int score = gameEngine.getScore();
		for (int i = 1; i < 9; i++) {
			int digit = findDigit(score, i);
			drawDigit(c, digit,	(gameEngine.FRAME_WIDTH/2 + 26*(4-i)) * convertW,
					3 * convertH, false);
		}

		switch (alienType) {
		case 1:
			drawBox(c, greenBox, "VERT SHOT");
			break;
		case 2:
			drawBox(c, blueBox, "LEFT SHOT");
			break;
		case 3:
			drawBox(c, redBox, "RIGHT SHOT");
			break;
		case 4:
			drawBox(c, yellowBox, "SPLIT SHOT");
			break;
		case 5:
			drawBox(c, grayBox, "SWARM SHOT");
			break;
		}
		
		if(gameEngine.loadingEnemies) {
			drawDigit(c, gameEngine.getLevelNum(), 258, 148, true);
		}
	}

	private void drawDigit(Canvas c, int num, float x, float y, boolean big) {
		Paint p = new Paint();
		p.reset();
		p.setColor(Color.WHITE);
		p.setTypeface(Typeface.createFromAsset(gameEngine.getContext().getAssets(), "fonts/TECHNOID.TTF"));
		p.setTextAlign(Paint.Align.CENTER);
		if(big) {
			p.setTextSize(52);
			c.drawText(Integer.toString(num), x * convertW, (y + 24) * convertH, p);
		}
		else {
			p.setTextSize(26);
			c.drawText(Integer.toString(num), x * convertW, (y + 12) * convertH, p);
		}
	}

	private void drawBox(Canvas c, Bitmap box, String text) {
		if (numInARow == 0)
			return;
		Paint p = new Paint();
		p.reset();
		RectF r = new RectF(446 * convertW, 3 * convertH,
				(446 + numInARow * 30) * convertW, 15 * convertH);
		c.drawBitmap(box, null, r, p);
		if(numInARow > 3) {
//			RectF dst = new RectF(469*convertW, 17*convertH, 562*convertW, 24*convertH);
//			c.drawBitmap(text, null, dst, p);
			p.setTextSize(16);
			p.setTypeface(Typeface.createFromAsset(gameEngine.getContext().getAssets(), "fonts/TECHNOID.TTF"));
			p.setColor(Color.WHITE);
			p.setTextAlign(Paint.Align.RIGHT);
			c.drawText(text, 566f*convertW, 24f*convertH, p);
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
