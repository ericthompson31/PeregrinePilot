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
	//private TextView stagesmodeText;
	private SensorManager myManager;
	private List<Sensor> sensors;
	private Sensor accSensor;
	private float oldX, oldY, oldZ = 0f;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.flight);

		mSeekBar = (SeekBar) findViewById(R.id.seek);
		mSeekBar.setOnSeekBarChangeListener(this);
		mProgressText = (TextView) findViewById(R.id.progress);
		mProgressText.setText("");
		mTrackingText = (TextView) findViewById(R.id.tracking);
		mTrackingText.setText("Ready");

		accText = (TextView) findViewById(R.id.accText);
		directmodeTextLR = (TextView) findViewById(R.id.directmodeLR);
		directmodeTextFB = (TextView) findViewById(R.id.directmodeFB);

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
     float thisX = x - oldX * 10;
     float thisY = y - oldY * 10;
     float thisZ = z - oldZ * 10;
     
     //int mathX = Math.round(thisX);
     int throttleFWD = Math.round((thisX * 2) - 14);
     int throttleBK = Math.abs(Math.round((thisX * 2) + 14));
     int throttleRIGHT = Math.round((thisY * 2) - 14);
     int throttleLEFT = Math.abs(Math.round((thisY * 2) + 14));
     //int mathY = Math.round(thisY);
     
     //We need to have a roll and a pitch component.  It seems we can ignore the z component, as its
     //value will not be needed in determining our copter's heading.
     accText.setText("X: " + Math.round(thisX) + ";  Y:" + Math.round(thisY) + ";  Z: " + Math.round(thisZ));
     //directmodeTextLR.setText("STABLE");
     //Handle the stable FORWARD/BACKWARD case.
     if((Math.round(thisX) < 7) && (Math.round(thisX) > -7)){
    	 directmodeTextFB.setText("STABLE");
     }
     //Not stable...
     else{
    	 //Handle the backward case
    	 if(Math.round(thisX) <= -7){
    		 if(Math.round(thisX) < -60)
    			 directmodeTextFB.setText("BACKWARD: 100%");
    		 directmodeTextFB.setText("BACKWARD: " + throttleBK + "%");
    	 }
    	 //Handle the forward case
    	 else{
    		 if(Math.round(thisX) > 60)
    			 directmodeTextFB.setText("FORWARD: 100%");
    		 directmodeTextFB.setText("FORWARD: " + throttleFWD + "%");
    	 }
     }
     if((Math.round(thisY) < 7) && (Math.round(thisY) > -7)){
    	 directmodeTextLR.setText("STABLE");
     }
     else{
    	 //Handle the backward case
    	 if(Math.round(thisY) <= -7){
    		 if(Math.round(thisY) < -60)
    			 directmodeTextLR.setText("RIGHT: 100%");
    		 directmodeTextLR.setText("RIGHT: " + throttleLEFT + "%");
    	 }
    	 //Handle the forward case
    	 else{
    		 if(Math.round(thisY) > 60)
    			 directmodeTextLR.setText("LEFT: 100%");
    		 directmodeTextLR.setText("LEFT: " + throttleRIGHT + "%");
    	 }
     }
     //directmodeTextLR.setText("X: " + Math.round(thisX));
     //Check the stable FORWARD/BACKWARD case
     /*if((mathX < 8) && (mathX > -8)){
    	 directmodeTextFB.setText("STABLE");
     }
     else{
    	 //Handle the positive/forward case
    	 if(mathX >= 8){
    		 if(mathX >= 60){
        		 directmodeTextFB.setText("FORWARD: 100%");
    		 }
    		 directmodeTextFB.setText("FORWARD: " + ((mathX * 1.923) - (15.23)) + "%");
    	 }
    	 else{
    		 if(mathX <= 60){
    			 directmodeTextFB.setText("BACKWARD: 100%");
    		 }
    		 directmodeTextFB.setText("BACKWARD: " + ((mathX * 1.923) - (15.23)) + "%");
    	 }
    	 
    	 
     }
     if((Math.round(thisY) < 8) && (Math.round(thisY) > -8)){
    	 directmodeTextLR.setText("STABLE");
     }
     if((mathY < 8) && (mathY > -8)){
    	 directmodeTextLR.setText("STABLE");
     }
     else{
    	 //Handle the positive/forward case
    	 if(mathY >= 8){
    		 if(mathY >= 60){
        		 directmodeTextLR.setText("LEFT: 100%");
    		 }
    		 directmodeTextLR.setText("LEFT: " + ((mathY * 1.923) - (15.23)) + "%");
    	 }
    	 else{
    		 if(mathY <= 60){
    			 directmodeTextLR.setText("RIGHT: 100%");
    		 }
    		 directmodeTextLR.setText("RIGHT: " + ((mathY * 1.923) - (15.23)) + "%");
    	 }
     }*/
     //stagesmodeText.setText("Stages...");
     
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