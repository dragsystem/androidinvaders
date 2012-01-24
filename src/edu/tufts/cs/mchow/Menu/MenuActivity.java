package edu.tufts.cs.mchow.Menu;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends Activity {
	private Typeface f;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	
	protected void changeButtonFont(Button b) {
		if(f==null)
			f = Typeface.createFromAsset(getAssets(), "fonts/TECHNOID.TTF");
		if(b!=null)
			b.setTypeface(f);
	}
	
	protected void changeTextViewFont(TextView tv) {
		if(f==null)
			f = Typeface.createFromAsset(getAssets(), "fonts/TECHNOID.TTF");
		if(tv!=null)
			tv.setTypeface(f);
	}
}
