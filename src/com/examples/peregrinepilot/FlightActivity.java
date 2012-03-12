/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.examples.peregrinepilot;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.WindowManager.LayoutParams;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Demonstrates how to use a seek bar
 */
public class FlightActivity extends Activity implements
		SeekBar.OnSeekBarChangeListener {

	SeekBar mSeekBar;
	TextView mProgressText;
	TextView mTrackingText;
	private TextView accText;
	private TextView directmodeTextLR;
	private TextView directmodeTextFB;
	private TextView stagesmodeTextLR;
	private TextView stagesmodeTextFB;
	private TextView dampmodeTextLR;
	private TextView dampmodeTextFB;
	private SensorManager myManager;
	private List<Sensor> sensors;
	private Sensor accSensor;
	private float oldX, oldY, oldZ = 0f;
	private int dampVal; //Value to damp flight adjustments
	private TextView dampValTextFB;
	int counter = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(LayoutParams.FLAG_FULLSCREEN | LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.flight);

		//throttle values
		mSeekBar = (SeekBar) findViewById(R.id.seek);
		mSeekBar.setOnSeekBarChangeListener(this);
		mProgressText = (TextView) findViewById(R.id.progress);
		mProgressText.setText("");
		mTrackingText = (TextView) findViewById(R.id.tracking);
		mTrackingText.setText("Ready");
		//accelerometer values
		accText = (TextView) findViewById(R.id.accText);
		directmodeTextLR = (TextView) findViewById(R.id.directmodeLR);
		directmodeTextFB = (TextView) findViewById(R.id.directmodeFB);
		stagesmodeTextLR = (TextView) findViewById(R.id.stagesmodeLR);
		stagesmodeTextFB = (TextView) findViewById(R.id.stagesmodeFB);
		dampmodeTextLR = (TextView) findViewById(R.id.dampmodeLR);
		dampmodeTextFB = (TextView) findViewById(R.id.dampmodeFB);
		dampValTextFB = (TextView) findViewById(R.id.dampValText);

		// Set Sensor + Manager
		myManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sensors = myManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
		if (sensors.size() > 0) {
			accSensor = sensors.get(0);
		}
	}

	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromTouch) {
		mProgressText.setText(" " + progress + "%");
		// + getString(R.string.seekbar_from_touch) + "=" + fromTouch);
	}

	public void onStartTrackingTouch(SeekBar seekBar) {
		mTrackingText.setText(getString(R.string.seekbar_tracking_on));
	}

	public void onStopTrackingTouch(SeekBar seekBar) {
		mTrackingText.setText(getString(R.string.seekbar_tracking_off));
	}

	private void updateTV(float x, float y, float z)
    {
	 float deltaThrottleFWD = 30*(x - oldX);
     float thisX = x - oldX * 10;
     float thisY = y - oldY * 10;
     float thisZ = z - oldZ * 10;
     
     int throttleFWD = Math.round((thisX * 2) - 14);
     int throttleBK = Math.abs(Math.round((thisX * 2) + 14));
     int throttleRIGHT = Math.round((thisY * 2) - 14);
     int throttleLEFT = Math.abs(Math.round((thisY * 2) + 14));
     
     
     //We need to have a roll and a pitch component.  It seems we can ignore the z component, as its
     //value will not be needed in determining our copter's heading.
     accText.setText("X: " + Math.round(thisX) + ";  Y:" + Math.round(thisY) + ";  Z: " + Math.round(thisZ));

     //***********************MODE = DIRECT*************************
     //Handle the stable FORWARD/BACKWARD case.
     if((Math.round(thisX) < 7) && (Math.round(thisX) > -7)){
    	 directmodeTextFB.setText("STABLE");
     }
     //Not stable...
     else{
    	 //Handle the backward case
    	 if(Math.round(thisX) <= -7){
    		 directmodeTextFB.setText("BACKWARD: " + throttleBK + "%");
    		 if(Math.round(thisX) < -60)
    			 directmodeTextFB.setText("BACKWARD: 100%");
    	 }
    	 //Handle the forward case
    	 else{
    		 directmodeTextFB.setText("FORWARD: " + throttleFWD + "%");
    		 if(Math.round(thisX) > 60)
    			 directmodeTextFB.setText("FORWARD: 100%");
    	 }
     }
     
     //Handle the stable LEFT/RIGHT case.
     if((Math.round(thisY) < 7) && (Math.round(thisY) > -7)){
    	 directmodeTextLR.setText("STABLE");
     }
     else{
    	 //Handle the right case
    	 if(Math.round(thisY) <= -7){
    		 directmodeTextLR.setText("RIGHT: " + throttleLEFT + "%");
    		 if(Math.round(thisY) < -60)
    			 directmodeTextLR.setText("RIGHT: 100%");
    	 }
    	 //Handle the left case
    	 else{
    		 directmodeTextLR.setText("LEFT: " + throttleRIGHT + "%");
    		 if(Math.round(thisY) > 60)
    			 directmodeTextLR.setText("LEFT: 100%");
    	 }
     }
   //***********************MODE = STAGES*************************
     if((Math.round(thisX) < 7) && (Math.round(thisX) > -7)){
    	 stagesmodeTextFB.setText("STABLE");
     }
     else{
    	 if(Math.round(thisX) < -7){
    		 stagesmodeTextFB.setText("BACKWARD: SLOW");
    		 if(Math.round(thisX) < -25){
    			 stagesmodeTextFB.setText("BACKWARD: NORMAL");
    			 if(Math.round(thisX) < -43){
    				 stagesmodeTextFB.setText("BACKWARD: FAST");
    			 }
    		 }
    	 }
    	 if(Math.round(thisX) > 7){
    		 stagesmodeTextFB.setText("FORWARD: SLOW");
    		 if(Math.round(thisX) > 25){
    			 stagesmodeTextFB.setText("FORWARD: NORMAL");
    			 if(Math.round(thisX) > 43){
    				 stagesmodeTextFB.setText("FORWARD: FAST");
    			 }
    		 }
    	 }
     }
     
     if((Math.round(thisY) < 7) && (Math.round(thisY) > -7)){
    	 stagesmodeTextLR.setText("STABLE");
     }
     else{
    	 if(Math.round(thisY) < -7){
    		 stagesmodeTextLR.setText("RIGHT: SLOW");
    		 if(Math.round(thisY) < -25){
    			 stagesmodeTextLR.setText("RIGHT: NORMAL");
    			 if(Math.round(thisY) < -43){
    				 stagesmodeTextLR.setText("RIGHT: FAST");
    			 }
    		 }
    	 }
    	 if(Math.round(thisY) > 7){
    		 stagesmodeTextLR.setText("LEFT: SLOW");
    		 if(Math.round(thisY) > 25){
    			 stagesmodeTextLR.setText("LEFT: NORMAL");
    			 if(Math.round(thisY) > 43){
    				 stagesmodeTextLR.setText("LEFT: FAST");
    			 }
    		 }
    	 }
     }
   //*********************MODE = DAMPENING***********************
     
     if(deltaThrottleFWD > 12 || deltaThrottleFWD < -12){
    	 dampmodeTextFB.setText("MAX RATE OF CHANGE");
     }
     if(deltaThrottleFWD < 3 && deltaThrottleFWD > -3){
    	 dampmodeTextFB.setText("NOT CHANGING");
     }
     else{
    	 counter++;
    	 if(counter == 5){
    		 dampmodeTextFB.setText(String.valueOf(deltaThrottleFWD));
    		 counter = 0;
    	 }
    	 
     }
    dampVal = 1;
     
    oldX = x;
    oldY = y;
    oldZ = z;
    }

	private final SensorEventListener mySensorListener = new SensorEventListener() {
		public void onSensorChanged(SensorEvent event) {
			updateTV(event.values[0], event.values[1], event.values[2]);
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};

	@Override
	protected void onResume() {
		super.onResume();
		myManager.registerListener(mySensorListener, accSensor,
				SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	protected void onStop() {
		myManager.unregisterListener(mySensorListener);
		super.onStop();
	}
}