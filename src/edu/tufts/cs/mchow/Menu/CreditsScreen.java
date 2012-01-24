package edu.tufts.cs.mchow.Menu;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import edu.tufts.cs.mchow.R;

public class CreditsScreen extends MenuActivity {
	private RelativeLayout fullScreen;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.credits);
		
		changeTextViewFont((TextView)findViewById(R.id.title));
		changeTextViewFont((TextView)findViewById(R.id.version_num));
		changeTextViewFont((TextView)findViewById(R.id.created_by));
		
		fullScreen = (RelativeLayout)findViewById(R.id.full_screen);
		fullScreen.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}

}
