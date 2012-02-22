package com.examples.peregrinepilot;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class PeregrinePilotActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    public void showAboutMessage(View v)
    {
    	Toast.makeText(this, "Peregrine Pilot is now setting up...", Toast.LENGTH_LONG).show();
    }
}