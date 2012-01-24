package edu.tufts.cs.mchow.Menu;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import edu.tufts.cs.mchow.R;

public class HelpScreen extends MenuActivity {
	LinearLayout fullScreen;
	private int screenNum;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.blank);
		screenNum = 0;
		
		fullScreen = (LinearLayout)findViewById(R.id.full_screen);
		fullScreen.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				switch (screenNum) {
				case 0:
					fullScreen.setBackgroundDrawable(getResources().getDrawable(R.drawable.howto2));
					screenNum = 1;
					break;
				case 1:
					finish();
				}
			}
		});
		fullScreen.setBackgroundDrawable(getResources().getDrawable(R.drawable.howto));
	}
}
