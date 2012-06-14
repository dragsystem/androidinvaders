package edu.tufts.cs.mchow.Game;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.WindowManager;
import edu.tufts.cs.mchow.R;
import edu.tufts.cs.mchow.Menu.PlayMenu;
import edu.tufts.cs.mchow.Settings.MyLog;
import edu.tufts.cs.mchow.Settings.Settings;

public class AndroidInvaders extends Activity implements SensorEventListener {
	private AnimationView av;

	private SensorManager sm = null;
	private List<Sensor> sensors;
	private Sensor sensor;
	private float mOrientationValues[] = new float[3];
	private float prevOrientationValues[] = null;//new float[3];
	private float prevAccelValues[] = null;//new float[3];
	long lastJerk = 0;
	public static final String PREFS = TriageApplication.PREFS;
	private static AndroidInvaders instance = null;
	private boolean launchingPauseMenu;

	public static void resetGamePrefs() {
		MyLog.debug("AndroidInvaders", "resetting game prefs");
		Settings.putInt("levelNum", 0);
		Settings.putInt("planetNum", 0);
		Settings.putInt("score", 0);
		Settings.putInt("clock", 0);
		Settings.putInt("lives", 5);
		Settings.putBoolean("shield", false);
		Settings.putBoolean("doubleShot", false);
		Settings.putInt("alienType", 0);
		Settings.putInt("numInARow", 0);
		Settings.putInt("highScore", 0);
		Settings.putInt("totalPlanetShots", 0);
		Settings.putInt("planetTimeElapsed", 0);
	}
	
	public static AndroidInvaders getInstance() {
		return instance;
	}

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.keep_screen_on);
		instance = this;
		Display display = getWindowManager().getDefaultDisplay();
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		av = new AnimationView(this, display);
		setContentView(av);

		sm = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
		this.sensors = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);
		if (sensors.size() > 0) {
			sensor = sensors.get(0);
		}
		//prevAccelValues[0] = prevAccelValues[1] = prevAccelValues[2] = 0;

		av.setDifficulty(Settings.getInt("difficulty", 0));
		int[] scores0 = new int[5];
		int[] scores1 = new int[5];
		int[] scores2 = new int[5];
		for(int i=0; i<5; i++) {
			String s = "score0" + i;
			scores0[i] = Settings.getInt(s, 0);
			s = "score1" + i;
			scores1[i] = Settings.getInt(s, 0);
			s = "score2" + i;
			scores2[i] = Settings.getInt(s, 0);
		}
		av.getGameEngine().getPrefs();
		av.getGameEngine().start();
		av.setHighScores(scores0, scores1, scores2);
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onRestart() {
		super.onRestart();
	}

	@Override
	public void onResume() {
		super.onResume();
		if (sensor != null) {
			sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
		}
		launchingPauseMenu = false;
		
		if(av.threadExists()) {
			av.getGameThread().setRunning(false);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		
		if(av.threadExists() && av.getGameThread().isRunning() && !launchingPauseMenu) {
			startActivity(new Intent(AndroidInvaders.this, PlayMenu.class));
			av.getGameThread().setRunning(false);
		}
		
		GameEngine ge = av.getGameEngine();
		Settings.putInt("levelNum", ge.pLevel);
		Settings.putInt("planetNum", ge.pPlanet);
		Settings.putInt("score", ge.pScore);
		Settings.putInt("clock", ge.pClock);
		Settings.putInt("lives", ge.pLives);
		Settings.putBoolean("shield", ge.pShield);
		Settings.putBoolean("doubleShot", ge.pDoubleShot);
		Settings.putInt("alienType", ge.pAType);
		Settings.putInt("numInARow", ge.pNumKilled);
		Settings.putInt("highScore", ge.pHighScore);
		Settings.putInt("difficulty", ge.difficulty);
		Settings.putInt("totalPlanetShots", ge.totalPlanetShots);
		Settings.putInt("planetTimeElapsed", ge.planetTimeElapsed);
		if(av.threadExists()) {
			int[] scores0 = av.getThread().getScores(0);
			int[] scores1 = av.getThread().getScores(1);
			int[] scores2 = av.getThread().getScores(2);
			for(int i=0; i<5; i++) {
				String s = "score0" + i;
				Settings.putInt(s, scores0[i]);
				s = "score1" + i;
				Settings.putInt(s, scores1[i]);
				s = "score2" + i;
				Settings.putInt(s, scores2[i]);
			}
		}

	}

	@Override
	public void onStop() {
		super.onStop();
		sm.unregisterListener(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		MyLog.debug("AndroidInvaders", "onDestroy");
		instance = null;
	}
	
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == SensorManager.SENSOR_ORIENTATION) {
			float accelValues[] = new float[3];
			if (null == prevOrientationValues) {
				prevOrientationValues = new float[3];
				prevOrientationValues[0] = mOrientationValues[0];
				prevOrientationValues[1] = mOrientationValues[1];
				prevOrientationValues[2] = mOrientationValues[2];
			}
			for (int i = 0; i < 3; i++) {
				mOrientationValues[i] = event.values[i];
				accelValues[i] = Math.abs(prevOrientationValues[i] - mOrientationValues[i]);
			}
			//try {
			if (null == prevAccelValues) {
				prevAccelValues = new float[3];
				prevAccelValues[0] = prevAccelValues[0];
				prevAccelValues[1] = prevAccelValues[1];
				prevAccelValues[2] = prevAccelValues[2];
			}
			float jerk[] = {Math.abs(accelValues[0]-prevAccelValues[0]), Math.abs(accelValues[1]-prevAccelValues[1]), Math.abs(accelValues[2]-prevAccelValues[2])};

			for (int i = 0; i < 3; i++) {
				if(jerk[i] > 10)
					MyLog.debug("Jerk", Integer.toString(i) + " " + Double.toString(jerk[i]));
				prevOrientationValues[i] = mOrientationValues[i];
				prevAccelValues[i] = accelValues[i];
			}
			if(Settings.getBoolean("tap_fire_enabled", false)) {
				int offset = 3 - Integer.parseInt(Settings.getString("tap_fire_sensitivity", "3"));
				if(jerk[2] > (8+offset)) {
					long t = System.currentTimeMillis();
					if ((t-lastJerk) > 80) {
						MyLog.debug("FireEvent", "Fire!");
						av.getGameEngine().fire();
						lastJerk = t;
					}
				}
			}


			if(av != null) {
				GameThread thread = av.getGameThread();
				if(thread != null) {
					thread.doSensor(mOrientationValues[1]);
				}
			}
			//			} catch (NullPointerException ex) {
			//				Log.e("AndroidInvaders", ex.toString());
			//			}
		}
	}
	
	public boolean onPrepareOptionsMenu(Menu m) {
		launchingPauseMenu = true;
		startActivity(new Intent(AndroidInvaders.this, PlayMenu.class));
		return true;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        	launchingPauseMenu = true;
        	av.getGameThread().setRunning(false);
        	startActivity(new Intent(AndroidInvaders.this, PlayMenu.class));
        	return true;
        }
        return false;
	}
    
	public GameThread getGameThread() {
		return av.getGameThread();
	}
        
	public GameEngine getGameEngine() {
		return av.getGameEngine();
	}
}
