
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

import android.app.Activity;
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
	}

	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromTouch) {
		mProgressText.setText(" " +progress+ "%");
				//+ getString(R.string.seekbar_from_touch) + "=" + fromTouch);
	}

	public void onStartTrackingTouch(SeekBar seekBar) {
		mTrackingText.setText(getString(R.string.seekbar_tracking_on));
	}

	public void onStopTrackingTouch(SeekBar seekBar) {
		mTrackingText.setText(getString(R.string.seekbar_tracking_off));
	}
}