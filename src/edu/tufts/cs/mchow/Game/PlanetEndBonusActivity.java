package edu.tufts.cs.mchow.Game;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import edu.tufts.cs.mchow.R;
import edu.tufts.cs.mchow.Menu.WinScreen;

public class PlanetEndBonusActivity extends Activity {
	private int startTime, startShots, minShots, maxTime;
	private int endShots, endTime; 
	private int score, accuracyBonus, timeBonus, totalBonus;
	private boolean winGame;
	
	public static final String EXTRA_STARTTIME = "startTime";
	public static final String EXTRA_STARTSHOTS = "startShots";
	public static final String EXTRA_MINSHOTS = "MIN_SHOTS";
	public static final String EXTRA_MINTIME = "MIN_TIME";
	public static final String EXTRA_SCORE = "score";
	public static final String EXTRA_ENDSHOTS = "endShots";
	public static final String EXTRA_ENDTIME = "endTime";
	public static final String EXTRA_GAMEWIN = "gameWin";
	private Typeface f;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.peb);
		findViewById(R.id.peb_full).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				exit();
			}
		});
		
		Intent i = getIntent();
		startTime = i.getIntExtra(EXTRA_STARTTIME, -1);
		startShots = i.getIntExtra(EXTRA_STARTSHOTS, -1);
		minShots = i.getIntExtra(EXTRA_MINSHOTS, -1);
		maxTime = 10 * 60 * 30; //10 minutes * 60sec/min * 30fps
		score = i.getIntExtra(EXTRA_SCORE, -1);
		endShots = i.getIntExtra(EXTRA_ENDSHOTS, -1);
		endTime = i.getIntExtra(EXTRA_ENDTIME, -1);
		winGame = i.getBooleanExtra(EXTRA_GAMEWIN, false);
		calcBonus();
		drawPEB();
	}
	
	@Override
	public void onBackPressed() {
		exit();
	}
	
	private void exit() {
		if(winGame) {
			Intent i = new Intent(this, WinScreen.class);
			i.putExtra("score", score);
			startActivity(i);
		} else {
			finish();
		}
	}
	
	public void onPause() {
		super.onPause();
		finish();
	}
	
	private void calcBonus() {
		int shots = endShots - startShots;
		int time = endTime - startTime;
		if(shots==0 || time==0) {
			//something is clearly wrong - no bonus for you
			accuracyBonus = timeBonus = 0;
		} else {
			accuracyBonus = minShots*7500/shots;
			timeBonus = Math.max(0, (maxTime-time)/3);
		}
		totalBonus = accuracyBonus + timeBonus;
		addScoreToGame(totalBonus);
	}
	
	private void drawPEB() {
		final TextView totalScoreView = (TextView)findViewById(R.id.totalscore);
		final TextView hitBonusView = (TextView)findViewById(R.id.hitbonus);
		final TextView timeBonusView = (TextView)findViewById(R.id.timebonus);
		TextView totalScoreTitleView = (TextView)findViewById(R.id.totalscoretitle);
		TextView hitBonusTitleView = (TextView)findViewById(R.id.hitbonustitle);
		TextView timeBonusTitleView = (TextView)findViewById(R.id.timebonustitle);
		changeTextViewFont(totalScoreView);
		changeTextViewFont(hitBonusView);
		changeTextViewFont(timeBonusView);
		changeTextViewFont(totalScoreTitleView);
		changeTextViewFont(hitBonusTitleView);
		changeTextViewFont(timeBonusTitleView);
		changeTextViewFont((TextView)findViewById(R.id.click_to_continue));
		
		final int AB = accuracyBonus;
		final int TB = timeBonus;
		totalScoreView.setText(Integer.toString(score));
		totalScoreView.postDelayed(new Runnable() {
			public void run() {
				hitBonusView.setText(Integer.toString(AB));
				totalScoreView.setText(Integer.toString(score + AB));
				hitBonusView.postDelayed(new Runnable() {
					public void run() {
						timeBonusView.setText(Integer.toString(TB));
						totalScoreView.setText(Integer.toString(score + AB + TB));
					}
				}, 1000);
			}
		}, 1000);
	}
	
	private void addScoreToGame(int points) {
		try {
			AndroidInvaders.getInstance().getGameEngine().addToScore(points);
		} catch (ClassCastException cce) {
			Log.e("PlanetEndBonusActivity", "PEB was not launched from AndroidInvaders Activity, wtf?");
		} catch (NullPointerException npe) {
			Log.e("PlanetEndBonusActivity", "Game Thread was null, wtf?");
		}
	}
	
	protected void changeTextViewFont(TextView tv) {
		if(f==null)
			f = Typeface.createFromAsset(getAssets(), "fonts/TECHNOID.TTF");
		if(tv!=null)
			tv.setTypeface(f);
	}
}