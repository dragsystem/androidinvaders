package edu.tufts.cs.mchow;

import java.util.ArrayList;

import android.view.*;
import android.content.Context;
import android.graphics.*;

public class GameThread extends Thread {
	private static final String DEBUG_TAG = "***** Animation:";
	private static final int DELAY = 33; // ~30 FPS
	private GameEngine ge;
	private MenuEngine me;
	private SurfaceHolder surfaceHolder;
	private Context context;
	private boolean running;
	private boolean showMenu;
	private int difficulty;
	public int score;
	public int scores0[];
	public int scores1[];
	public int scores2[];
	

	// Used to keep track of time between updates and amount of time to sleep
	// for
	long lastLoopTime, sleepTime;

	public GameThread(SurfaceHolder surfaceHolder, Context context,
			GameEngine ge, MenuEngine me) {
		running = false;
		this.surfaceHolder = surfaceHolder;
		this.context = context;
		this.ge = ge;
		this.me = me;
		scores0 = new int[5];
		scores1 = new int[5];
		scores2 = new int[5];
		showMenu = true;
	}
	
	public int[] getScores(int diff) { //have it pass an int to get just one score list
		switch(diff) {
		case 0:
			return scores0;
		case 1:
			return scores1;
		case 2:
			return scores2;
		}
		return null;
	}
	
	public GameEngine getGameEngine() {
		return ge;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public boolean isRunning() {
		return running;
	}
	
	public void setShowMenu(boolean showMenu) {
		this.showMenu = showMenu;
	}
	
	public boolean getShowMenu() {
		return showMenu;
	}
	
	public void gameOver() {
		showMenu = true;
		me.setScreen(1);
		recordScore();
	}
	
	public void winGame() {
		showMenu = true;
		me.setScreen(2);
		recordScore();
	}
	
	private void recordScore() {
		int [] scores = new int [5];
		switch(ge.difficulty) {
		case 0:
			scores = scores0;
			break;
		case 1:
			scores = scores1;
			break;
		case 2:
			scores = scores2;
			break;
		}
		int holder = score;
		for(int i=0; i<5; i++) {
			if(holder>scores[i]) {
				int a = scores[i];
				scores[i] = holder;
				holder = a;
			}
		}
	}
	
	public void newGame(int difficulty) {
		ge.reset(difficulty);
		score = 0;
		showMenu = false;
	}
	
	public boolean isAtMenu() {
		return showMenu;
	}
	
	public void continueGame() {
		showMenu = false;
		if(ge.getGameClock() == 0)
			ge.reset(ge.difficulty);
	}

	@Override
	public void run() {
		long delta;
		while (running) {
			lastLoopTime = System.currentTimeMillis();
			Canvas c = null;
			// Lock canvas so nothing else can use it
			c = surfaceHolder.lockCanvas(null);
			synchronized (surfaceHolder) {
				doDraw(c); // update is now called from draw to prevent
				// updating when at menu screen
			}
//			Paint p = new Paint();
//			p.setColor(Color.YELLOW);
//			if (sleepTime < 0)
//				c.drawText(Long.toString(sleepTime), 20, 20, p);

			delta = System.currentTimeMillis() - lastLoopTime;
			this.sleepTime = DELAY - delta;
			try {
				if (sleepTime > 0) {
					sleep(sleepTime);
				}
			} catch (InterruptedException ex) {
				// Log error
			}
			if (c != null) {
				surfaceHolder.unlockCanvasAndPost(c);
			}
		}
	}

	public void doDraw(Canvas canvas) {
		synchronized (surfaceHolder) {
			if(showMenu)
				me.draw(canvas);
			else
				ge.draw(canvas);
		}
	}

	public boolean doTouch(MotionEvent event) {
		int eventAction = event.getAction();
		if (showMenu) {
			if (eventAction == MotionEvent.ACTION_DOWN) { // Touch on the screen event
				me.doTouch(event.getX(), event.getY());
			}
			return true;
		} else {
			if (eventAction == MotionEvent.ACTION_DOWN) {
				if (event.getX(0) > ge.getDisplay().getWidth() / 2)
					ge.fire();
				else
					ge.specialFire();
			}
		}
		return true;
	}

	public void doSensor(float value) {
		if(showMenu)
			return;
		int amt = 0;
		int sign = value > 0 ? 1 : -1;
		if (Math.abs(value) > 1 && Math.abs(value) < 2) {
			amt = sign * 3;
		} else if (Math.abs(value) >= 2) {
			amt = sign * 6;
		}
		ge.getPlayerShip().move(amt);
	}

	public void setHighScores(int[] scores0, int[] scores1, int[] scores2) {
		this.scores0 = scores0;
		this.scores1 = scores1;
		this.scores2 = scores2;
	}
	
	public void setDifficulty(int difficulty) {
		ge.difficulty = difficulty;
	}
}
