
package edu.tufts.cs.mchow.Menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import edu.tufts.cs.mchow.R;
import edu.tufts.cs.mchow.Game.AndroidInvaders;
import edu.tufts.cs.mchow.Settings.MyLog;
import edu.tufts.cs.mchow.Settings.Settings;

public class PlayMenu extends MenuActivity {
	private Button continueButton;
	private Button newButton;
	private Button highScoreButton;
	private TextView paused;
	private static PlayMenu instance = null;
	
	public static PlayMenu getInstance() {
		return instance;
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play_menu);
		instance = this;
		
		paused = (TextView)findViewById(R.id.paused);
		continueButton = (Button)findViewById(R.id.continue_game);
		newButton = (Button)findViewById(R.id.new_game);
		highScoreButton = (Button)findViewById(R.id.high_scores);
		
		continueButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(AndroidInvaders.getInstance() == null) {
					Intent i = new Intent(PlayMenu.this, AndroidInvaders.class);
					startActivity(i);
				}
				finish();
			}
		});
		newButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(AndroidInvaders.getInstance() != null) {
					AndroidInvaders.getInstance().finish();
				}
				finish();
				Intent i = new Intent(PlayMenu.this, ChooseDifficultyScreen.class);
				i.putExtra("path_type", ChooseDifficultyScreen.PLAY_PATH);
				startActivityForResult(i, 0);
			}
		});
		highScoreButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(PlayMenu.this, ChooseDifficultyScreen.class);
				i.putExtra("path_type", ChooseDifficultyScreen.HIGHSCORE_PATH);
				startActivityForResult(i, 0);
			}
		});
		changeTextViewFont(paused);
		changeButtonFont(continueButton);
		changeButtonFont(newButton);
		changeButtonFont(highScoreButton);
	}
	
	protected void onActivityResult(int req, int res, Intent d) {
		super.onActivityResult(req, res, d);
		if(res==1) {
			MyLog.debug("PlayMenu", "returning from game, calling finish()");
			finish();
		}
	}
	
	public void onResume() {
		super.onResume();
		AndroidInvaders AI = AndroidInvaders.getInstance();
		if(Settings.getInt("clock", 0) == 0 && AI == null) {
			continueButton.setVisibility(View.GONE);

		} else {
			continueButton.setVisibility(View.VISIBLE);
		}
		if(AI == null) {
			paused.setVisibility(View.INVISIBLE);
		}
//		if(AI != null) {
//			GameEngine ge = AI.getGameEngine();
//			if(ge.getGameClock() != 0) {
//				continueButton.setVisibility(View.VISIBLE);
//			} else {
//				continueButton.setVisibility(View.GONE);
//			}
//		} else {
//			continueButton.setVisibility(View.GONE);
//		}
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        	finish();
        	AndroidInvaders ai = AndroidInvaders.getInstance();
        	if (ai != null) {
        		ai.finish();
        	}
        	return true;
        }
        return false;
	}
}
