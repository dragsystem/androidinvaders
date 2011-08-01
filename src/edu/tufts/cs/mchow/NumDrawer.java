package edu.tufts.cs.mchow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class NumDrawer {
	private float convertW, convertH;
	private Bitmap numbers;
	
	public NumDrawer(float convertW, float convertH, Context context) {
		this.convertW = convertW;
		this.convertH = convertH;
		numbers = BitmapFactory.decodeResource(context.getResources(), R.drawable.numbers);
	}
	
	public void drawNum(int num, int startX, int startY, Canvas c, boolean big) {
		Paint p = new Paint();
		p.setColor(Color.YELLOW);
		double d = Math.log(num)/Math.log(10) + 1;
		int digits = d>0 ? (int)d : 1;

		for(int i=0; i<digits; i++) {
			int digit = findDigit(num, digits-i);
			if(!big)
				drawDigit(c, digit, startX + 26*i, startY, big);
			else
				drawDigit(c, digit, startX + 32*i, startY, big);
		}
	}
	
	public int findDigit(int num, int place) { // place 1 is the ones place, 2
		// is the tens, etc.
		double step1 = num / (Math.pow(10, place - 1));
		return ((int) step1) % 10;
	}
	
	public void drawDigit(Canvas c, int num, float x, float y, boolean big) {
		//int i = 1/0;
		Paint p = new Paint();
		p.reset();
		Rect src = new Rect(26*num, 0, 26*(num+1), 12);
		RectF dst;
		if(!big)
			dst = new RectF(x*convertW, y*convertH, (x+26)*convertW,
				(y+12)*convertH);
		else
			dst = new RectF(x*convertW, y*convertH, (x+32)*convertW,
					(y+16)*convertH);
		c.drawBitmap(numbers, src, dst, p);
	}
}
