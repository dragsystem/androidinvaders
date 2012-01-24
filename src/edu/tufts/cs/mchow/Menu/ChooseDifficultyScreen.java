package edu.tufts.cs.mchow.Menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import edu.tufts.cs.mchow.R;
import edu.tufts.cs.mchow.Game.AndroidInvaders;
import edu.tufts.cs.mchow.Settings.MyLog;

public class ChooseDifficultyScreen extends MenuActivity {
	private Button easyButton;
	private Button mediumButton;
	private Button hardButton;
	public static final int PLAY_PATH = 0;
	public static final int HIGHSCORE_PATH = 1;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_difficulty);
		final int pathType = getIntent().getIntExtra("path_type", 0);
		easyButton = (Button)findViewById(R.id.easy);
		mediumButton = (Button)findViewById(R.id.medium);
		hardButton = (Button)findViewById(R.id.hard);
		
		
		easyButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				launchIntent(0, pathType);
			}
		});
		mediumButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				launchIntent(1, pathType);
			}
		});
		hardButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				launchIntent(2, pathType);
			}
		});
		changeButtonFont(easyButton);
		changeButtonFont(mediumButton);
		changeButtonFont(hardButton);
	}
	
	private void launchIntent(int d, final int pt) {
		Intent i = null;
		switch(pt) {
		case PLAY_PATH:
			AndroidInvaders.resetGamePrefs();
			i = new Intent(ChooseDifficultyScreen.this, AndroidInvaders.class);
			break;
		case HIGHSCORE_PATH:
			i = new Intent(ChooseDifficultyScreen.this, HighScoreScreen.class);
			break;
		}
		i.putExtra("difficulty", d);
		startActivity(i);
		if(pt==PLAY_PATH) {
			MyLog.debug("ChooseDifficultyScreen", "set result 0");
			setResult(1, null);
		} else {
			MyLog.debug("ChooseDifficultyScreen", "set result 1");
			setResult(0, null);
		}
		finish();
	}
	
}
