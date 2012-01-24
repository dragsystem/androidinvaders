package edu.tufts.cs.mchow.Menu;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.tufts.cs.mchow.R;

public class WinScreen extends MenuActivity {
	private TextView congrats;
	private TextView score;
	private TextView highscore;
	private LinearLayout fullScreen;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.win);
		congrats = (TextView)findViewById(R.id.congrats);
		score = (TextView)findViewById(R.id.score);
		highscore = (TextView)findViewById(R.id.highscore);
		score.setText(score.getText() + Integer.toString(getIntent().getIntExtra("score", 0)));
		
		changeTextViewFont(congrats);
		changeTextViewFont(score);
		changeTextViewFont(highscore);
		
		fullScreen = (LinearLayout)findViewById(R.id.full_screen);
		fullScreen.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}
}
