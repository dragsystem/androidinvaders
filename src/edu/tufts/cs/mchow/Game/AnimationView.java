package edu.tufts.cs.mchow.Game;

import android.content.Context;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AnimationView extends SurfaceView implements SurfaceHolder.Callback {
	// The game engine
	private GameEngine ge;
	// The thread for the game loop
	private GameThread thread;

	private int scores0[];
	private int scores1[];
	private int scores2[];
	private int difficulty;
	// Don't want to draw if the surface doesn't exist
	// private boolean mSurfaceExists;

	private Context ct;

	public AnimationView(Context context, Display display) {
		super(context);

		// Set up game engine;
		ge = new GameEngine(context, display);
		// Initialize our screen holder
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);

		// Initialize thread
		setFocusable(true);
		this.ct = context;

	}
	
	public void setHighScores(int[] scores0, int[] scores1, int[] scores2) {
		this.scores0 = scores0;
		this.scores1 = scores1;
		this.scores2 = scores2;
	}
	
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	
	public boolean threadExists() {
		return (thread != null);
	}
	
	public GameThread getThread() {
		return thread;
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// Initialize thread every time window comes to foreground
		thread = new GameThread(holder, ct, ge);
		thread.start();
		thread.setRunning(true);
		ge.setThread(thread);
		thread.setHighScores(scores0, scores1, scores2);
		thread.setDifficulty(difficulty);
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// Interesting...
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		thread.setRunning(false);
		thread.interrupt();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(thread != null) {
			thread.doTouch(event);
		}
		return true;
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent e) {
		if(thread == null)
			return true;
		
		if(keyCode == KeyEvent.KEYCODE_DPAD_LEFT)
			thread.doSensor(-2);
		else if(keyCode == KeyEvent.KEYCODE_DPAD_RIGHT)
			thread.doSensor(2);
		else if(keyCode == KeyEvent.KEYCODE_SPACE)
			thread.getGameEngine().fire();
		else if(keyCode == KeyEvent.KEYCODE_S)
			thread.getGameEngine().specialFire();
		return true;
	}

	public GameEngine getGameEngine() {
		return ge;
	}

	public GameThread getGameThread() {
		return thread;
	}

}
