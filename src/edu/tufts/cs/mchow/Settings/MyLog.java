package edu.tufts.cs.mchow.Settings;

import android.util.Log;

public class MyLog {
	public static void debug(String tag, String msg) {
		Log.d(tag, "TRIAGE_"+msg);
	}
}
