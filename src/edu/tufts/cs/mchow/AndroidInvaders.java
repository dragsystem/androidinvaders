package edu.tufts.cs.mchow;

import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class AndroidInvaders extends Activity implements SensorEventListener {
	private AnimationView av;

	private SensorManager sm = null;
	private List<Sensor> sensors;
	private Sensor sensor;
	private float mOrientationValues[] = new float[3];
	
	public static final String PREFS = "AndInvPrefs";

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		av.getGameThread().setShowMenu(true);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		av.getGameThread().setShowMenu(true);
		return true;
	}

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
			sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
		}

		SharedPreferences settings = getSharedPreferences(PREFS, 0);
		av.getGameEngine().getPrefs(settings.getInt("levelNum", 0), 
				settings.getInt("score", 0), 
				settings.getInt("clock", 0),
				settings.getInt("lives", 0),
				settings.getBoolean("shield", false),
				settings.getBoolean("doubleShot", false),
				settings.getInt("alienType", 0),
				settings.getInt("numInARow", 0),
				settings.getInt("highScore", 0));
		av.setDifficulty(settings.getInt("difficulty", 0));
		int[] scores0 = new int[5];
		int[] scores1 = new int[5];
		int[] scores2 = new int[5];
		for(int i=0; i<5; i++) {
			String s = "score0" + i;
			scores0[i] = settings.getInt(s, 0);
			s = "score1" + i;
			scores1[i] = settings.getInt(s, 0);
			s = "score2" + i;
			scores2[i] = settings.getInt(s, 0);
		}
		av.setHighScores(scores0, scores1, scores2);
	}

	@Override
	public void onPause() {
		super.onPause();
		SharedPreferences settings = getSharedPreferences(PREFS, 0);
	    SharedPreferences.Editor editor = settings.edit();
	    GameEngine ge = av.getGameEngine();
	    editor.putInt("levelNum", ge.pLevel);
	    editor.putInt("score", ge.pScore);
	    editor.putInt("clock", ge.pClock);
	    editor.putInt("lives", ge.pLives);
	    editor.putBoolean("shield", ge.pShield);
	    editor.putBoolean("doubleShot", ge.pDoubleShot);
	    editor.putInt("alienType", ge.pAType);
	    editor.putInt("numInARow", ge.pNumKilled);
	    editor.putInt("highScore", ge.pHighScore);
	    editor.putInt("difficulty", ge.difficulty);
	    if(av.threadExists()) {
	    	int[] scores0 = av.getThread().getScores(0);
	    	int[] scores1 = av.getThread().getScores(1);
	    	int[] scores2 = av.getThread().getScores(2);
			for(int i=0; i<5; i++) {
				String s = "score0" + i;
				editor.putInt(s, scores0[i]);
				s = "score1" + i;
				editor.putInt(s, scores1[i]);
				s = "score2" + i;
				editor.putInt(s, scores2[i]);
			}
	    }
	    
	    editor.commit();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		sm.unregisterListener(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == SensorManager.SENSOR_ORIENTATION) {
			for (int i = 0; i < 3; i++) {
				mOrientationValues[i] = event.values[i];
			}
			try {
				GameThread thread = av.getGameThread();
				if(thread.isAtMenu()) {
					//	&& Math.abs(mOrientationValues[1])>Math.abs(mOrientationValues[0])) {
					//this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
					//this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
				}
				else {
					thread.doSensor(mOrientationValues[1]);
					//this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
				}
			} catch (NullPointerException ex) {
				// Log error
			}
		}

	}
}
