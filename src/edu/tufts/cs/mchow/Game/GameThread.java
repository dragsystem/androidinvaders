package edu.tufts.cs.mchow.Game;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import edu.tufts.cs.mchow.Menu.LoseScreen;
import edu.tufts.cs.mchow.Menu.WinScreen;
import edu.tufts.cs.mchow.Settings.Settings;

public class GameThread extends Thread {
	private static final int DELAY = 33; // ~30 FPS
	private GameEngine ge;
	private SurfaceHolder surfaceHolder;
	private boolean running;
	public int score;
	public int scores0[];
	public int scores1[];
	public int scores2[];
	

	// Used to keep track of time between updates and amount of time to sleep
	// for
	long lastLoopTime, sleepTime;

	public GameThread(SurfaceHolder surfaceHolder, Context context,
			GameEngine ge) {
		running = false;
		this.surfaceHolder = surfaceHolder;
		this.ge = ge;
		scores0 = new int[5];
		scores1 = new int[5];
		scores2 = new int[5];
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
	
	public void gameOver() {
		recordScore();
		Intent i = new Intent(AndroidInvaders.getInstance(), LoseScreen.class);
		i.putExtra("score", score);
		AndroidInvaders.getInstance().finish();
		AndroidInvaders.getInstance().startActivity(i);
		running = false;
		AndroidInvaders.resetGamePrefs();
		endGame();
	}
	
	public void winGame() {
		recordScore();
		Intent i = new Intent(AndroidInvaders.getInstance(), WinScreen.class);
		i.putExtra("score", score);
		AndroidInvaders.getInstance().finish();
		AndroidInvaders.getInstance().startActivity(i);
		running = false;
		AndroidInvaders.resetGamePrefs();
		endGame();
	}
	
	private void endGame() {
		ge.reset(0);
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
	}
	
	public void continueGame() {
		ge.getPrefs();
		if(ge.getGameClock() == 0) {
			ge.reset(ge.difficulty);
		}
		ge.nextLevel();
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
			ge.draw(canvas);
		}
	}

	public boolean doTouch(MotionEvent event) {
		int eventAction = event.getAction();
		if (eventAction == MotionEvent.ACTION_DOWN) {
			if(!Settings.getBoolean("tap_fire_enabled", false)) {
				if (event.getX(0) > ge.getDisplay().getWidth() / 2)
					ge.fire();
				else
					ge.specialFire();
			} else {
				ge.specialFire();
			}
		}
		return true;
	}

	public void doSensor(float value) {
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
