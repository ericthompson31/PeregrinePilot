package com.examples.peregrinepilot;

import android.app.Activity;
import android.os.Bundle;

/**
 * This activity will display an image and some information about Peregrine Engineering
 * @author Eric Thompson
 */
public class FlightActivity extends Activity 
{
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight);
    }

}