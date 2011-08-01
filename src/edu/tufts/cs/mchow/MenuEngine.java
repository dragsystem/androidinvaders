package edu.tufts.cs.mchow;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.view.Display;
import java.math.*;

public class MenuEngine {
	private Context context;
	private Display display;
	private GameThread thread;
	private float FRAME_WIDTH, FRAME_HEIGHT;
	private float convertW, convertH;
	int alpha = 0;
	private int currentScreen;
	private ArrayList <BitmapDrawable> pics;
	private Bitmap hiscoretext;
	private NumDrawer sd;
	private int difficulty;
	
	public MenuEngine(Context context, Display display) {
		this.context = context;
		this.display = display;
		FRAME_WIDTH = display.getWidth();
		FRAME_HEIGHT = display.getHeight();
		currentScreen = 0;
		Rect full = new Rect(0, 0, display.getWidth(), display.getHeight());
		pics = new ArrayList <BitmapDrawable>();
		pics.add(0, new BitmapDrawable(context.getResources(), 
				BitmapFactory.decodeResource(context.getResources(), R.drawable.menuscreen)));
		pics.add(1, new BitmapDrawable(context.getResources(),
				BitmapFactory.decodeResource(context.getResources(), R.drawable.gameover)));
		pics.add(2, new BitmapDrawable(context.getResources(),
				BitmapFactory.decodeResource(context.getResources(), R.drawable.youwin)));
		pics.add(3, new BitmapDrawable(context.getResources(), 
				BitmapFactory.decodeResource(context.getResources(), R.drawable.startplay)));
		pics.add(4, new BitmapDrawable(context.getResources(), 
				BitmapFactory.decodeResource(context.getResources(), R.drawable.about)));
		pics.add(5, new BitmapDrawable(context.getResources(), 
				BitmapFactory.decodeResource(context.getResources(), R.drawable.howto)));
		pics.add(6, new BitmapDrawable(context.getResources(), 
				BitmapFactory.decodeResource(context.getResources(), R.drawable.howto2)));
		pics.add(7, new BitmapDrawable(context.getResources(), 
				BitmapFactory.decodeResource(context.getResources(), R.drawable.highscores)));
		pics.add(8, new BitmapDrawable(context.getResources(), 
				BitmapFactory.decodeResource(context.getResources(), R.drawable.difficulty)));
		pics.add(9, new BitmapDrawable(context.getResources(), 
				BitmapFactory.decodeResource(context.getResources(), R.drawable.difficulty)));
		for(int i=0; i<pics.size(); i++) {
			pics.get(i).setBounds(full);
		}
		hiscoretext = BitmapFactory.decodeResource(context.getResources(), R.drawable.hiscoretext);
	}
	
	public void setThread(GameThread thread) {
		this.thread = thread;
		convertW = thread.getGameEngine().convertW;
		convertH = thread.getGameEngine().convertH;
		sd = new NumDrawer(convertW, convertH, context);
	}
	
	public void setScreen(int screen) {
		currentScreen = screen;
	}
	
	public void draw(Canvas canvas) {
		if(alpha < 255)
			alpha += 15;
		pics.get(currentScreen).setAlpha(alpha);
		pics.get(currentScreen).draw(canvas);
		if(currentScreen==1) {
			sd.drawNum(thread.score, 300, 101, canvas, false);
			if(thread.score >= thread.getScores(thread.getGameEngine().difficulty)[4]) {
				Paint p = new Paint();
				RectF dst = new RectF(337*convertW, 123*convertH, 461*convertW, 131*convertH);
				canvas.drawBitmap(hiscoretext, null, dst, p);
			}
		}
		if(currentScreen==2) {
			sd.drawNum(thread.score, 143, 53, canvas, false);
			if(thread.score >= thread.getScores(thread.getGameEngine().difficulty)[4]) {
				Paint p = new Paint();
				RectF dst = new RectF(160*convertW, 73*convertH, 284*convertW, 81*convertH);
				canvas.drawBitmap(hiscoretext, null, dst, p);
			}
		}
		if(currentScreen==7) {
			int scores[] = thread.getScores(difficulty);
			for(int i=0; i<5; i++) {
				sd.drawNum(scores[i], 140, 30+45*(i+1), canvas, true);
			}
		}
	}
	
	public void doTouch(float x, float y) {
		switch(currentScreen){
		case 1:
			alpha = 0;
			currentScreen = 0;
			break;
		case 2:
			alpha = 0;
			currentScreen = 0;
			break;
		case 3:
			if (x > FRAME_WIDTH*0.19 && x < FRAME_WIDTH*0.81) {
				if (y > (FRAME_HEIGHT * 0.13)
						&& (y < FRAME_HEIGHT * 0.24)) { // NEW GAME
					alpha = 0;
					currentScreen = 8;
				}
				else if ((y > FRAME_HEIGHT*0.34)
						&& (y < FRAME_HEIGHT*0.45)) { // CONTINUE
					thread.continueGame();
				}
				else if ((y > FRAME_HEIGHT*0.55)
						&& (y < FRAME_HEIGHT*0.67)) { // HIGH SCORES
					alpha = 0;
					currentScreen = 9;
				}
				else if ((y > FRAME_HEIGHT*0.77)
						&& (y < FRAME_HEIGHT*0.88)) { // BACK
					alpha = 0;
					currentScreen = 0;					
				}				
			}
			break;
		case 4:
			alpha = 0;
			currentScreen = 0;
			break;
		case 5:
			alpha = 0;
			currentScreen = 6;
			break;
		case 6:
			alpha = 0;
			currentScreen = 0;
			break;
		case 7:
			alpha = 0;
			currentScreen = 3;
			break;
		case 8:
			if (x > FRAME_WIDTH*0.20 && x < FRAME_WIDTH*0.80) {
				if (y > (FRAME_HEIGHT * 0.15)
						&& (y < FRAME_HEIGHT * 0.26)) { // LIEUTENANT
					thread.newGame(0);
					currentScreen = 3;
				}
				else if ((y > FRAME_HEIGHT*0.43)
						&& (y < FRAME_HEIGHT*0.54)) { // CAPTAIN
					thread.newGame(1);
					currentScreen = 3;
				}
				else if ((y > FRAME_HEIGHT*0.72)
						&& (y < FRAME_HEIGHT*0.83)) { // ADMIRAL
					thread.newGame(2);
					currentScreen = 3;
				}
			}
			break;
		case 9:
			if (x > FRAME_WIDTH*0.20 && x < FRAME_WIDTH*0.80) {
				if (y > (FRAME_HEIGHT * 0.15)
						&& (y < FRAME_HEIGHT * 0.26)) { // LIEUTENANT
					difficulty = 0;
					currentScreen = 7;
					alpha = 0;
				}
				else if ((y > FRAME_HEIGHT*0.43)
						&& (y < FRAME_HEIGHT*0.54)) { // CAPTAIN
					difficulty = 1;
					currentScreen = 7;
					alpha = 0;
				}
				else if ((y > FRAME_HEIGHT*0.72)
						&& (y < FRAME_HEIGHT*0.83)) { // ADMIRAL
					difficulty = 2;
					currentScreen = 7;
					alpha = 0;
				}
			}
			break;
		default:
			if (x > FRAME_WIDTH*0.63) {
				if (y > (FRAME_HEIGHT * 0.49)
						&& (y < FRAME_HEIGHT * 0.59)) { // PLAY
					alpha = 0;
					currentScreen = 3;
				}
				else if ((y > FRAME_HEIGHT*0.65)
						&& (y < FRAME_HEIGHT*0.74)) { // HELP
					alpha = 0;
					currentScreen = 5;
				}
				else if ((y > FRAME_HEIGHT*0.81)
						&& (y < FRAME_HEIGHT*0.9)) { // CREDITS
					alpha = 0;
					currentScreen = 4;
				}
			}
			break;
		}
	}
	
}
