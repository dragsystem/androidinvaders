package edu.tufts.cs.mchow.Settings;


import edu.tufts.cs.mchow.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SettingsScreen extends PreferenceActivity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		addPreferencesFromResource(R.xml.settings);
		
        setTitle("Settings");
	}

}
