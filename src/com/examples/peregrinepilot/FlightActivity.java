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
     
     accText.setText("X: " + Math.round(thisX) + ";  Y:" + Math.round(thisY) + ";  Z: " + Math.round(thisZ));
     
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