package edu.tufts.cs.mchow.Menu;

import android.os.Bundle;
import android.widget.TextView;
import edu.tufts.cs.mchow.R;
import edu.tufts.cs.mchow.Settings.Settings;

public class HighScoreScreen extends MenuActivity{
	private TextView[] scores = new TextView[5];
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.high_scores);
		scores[0] = (TextView)findViewById(R.id.score0);
		scores[1] = (TextView)findViewById(R.id.score1);
		scores[2] = (TextView)findViewById(R.id.score2);
		scores[3] = (TextView)findViewById(R.id.score3);
		scores[4] = (TextView)findViewById(R.id.score4);
		int diff = getIntent().getIntExtra("difficulty", 0);
		
		TextView title = (TextView)findViewById(R.id.title);
		changeTextViewFont(title);
		int i = 0;
		for(TextView tv : scores) {
			tv.setText(Integer.toString(Settings.getInt("score"+diff+Integer.toString(i++), 0)));
			changeTextViewFont(tv);
		}
	}
}
