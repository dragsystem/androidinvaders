package edu.tufts.cs.mchow.Menu;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import edu.tufts.cs.mchow.R;
import edu.tufts.cs.mchow.Settings.SettingsScreen;

public class MainMenu extends MenuActivity {
	private Button playButton;
	private Button helpButton;
	private Button settingsButton;
	private Button creditsButton;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);
		playButton = (Button)findViewById(R.id.play_btn);
		helpButton = (Button)findViewById(R.id.help_btn);
		settingsButton = (Button)findViewById(R.id.settings_btn);
		creditsButton = (Button)findViewById(R.id.credits_btn);
		
		playButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(MainMenu.this, PlayMenu.class);
				startActivity(i);
			}
		});
		helpButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(MainMenu.this, HelpScreen.class);
				startActivity(i);
			}
		});
		settingsButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(MainMenu.this, SettingsScreen.class);
				startActivity(i);
			}
		});
		creditsButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(MainMenu.this, CreditsScreen.class);
				startActivity(i);
			}
		});
		changeButtonFont(playButton);
		changeButtonFont(helpButton);
		changeButtonFont(settingsButton);
		changeButtonFont(creditsButton);
		changeTextViewFont((TextView)findViewById(R.id.title_bar));
	}
}
