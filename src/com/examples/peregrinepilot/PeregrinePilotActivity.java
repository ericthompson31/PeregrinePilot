package com.examples.peregrinepilot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

public class PeregrinePilotActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // hide title bar
		getWindow().addFlags(LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);
	}

	public void aboutPE(View v) {
		Intent intent = new Intent(
				getApplicationContext(),
				com.examples.peregrinepilot.AboutPeregrineEngineeringActivity.class);
		startActivity(intent);
		//requestWindowFeature(Window.FEATURE_NO_TITLE); // hide title bar
	}

	public void configure(View v) {
		Intent intent = new Intent(getApplicationContext(),
				com.examples.peregrinepilot.ConfigureActivity.class);
		startActivity(intent);
		//requestWindowFeature(Window.FEATURE_NO_TITLE); // hide title bar
	}

	public void flight(View v) {
		Intent intent = new Intent(getApplicationContext(),
				com.examples.peregrinepilot.FlightActivity.class);
		startActivity(intent);
		//requestWindowFeature(Window.FEATURE_NO_TITLE); // hide title bar
	}
}