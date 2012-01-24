package edu.tufts.cs.mchow.Game;

import android.app.Application;

public class TriageApplication extends Application {
	private static TriageApplication instance = null;
	public static final String PREFS = "AndInvPrefs";
	
	public void onCreate() {
		super.onCreate();
		instance = this;
	}
	
	public static TriageApplication getInstance() {
		return instance;
	}
}
