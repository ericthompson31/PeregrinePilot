package com.examples.peregrinepilot;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

/**
 * This activity will display an image and some information about Peregrine
 * Engineering
 * 
 * @author Eric Thompson
 */
public class AboutPeregrineEngineeringActivity extends Activity {
	TextView tv1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);

		tv1 = (TextView) findViewById(R.id.tv1);

		loadDoc();
	}

	private void loadDoc() {

		String s = "Peregrine Pilot\n"
				+ "Version 1.3 (28 February, 2012)\n"
				+ "\n"
				+ "Peregrine Engineering was established"
				+ " in August of 2011 by Doug Padzensky, Eric Thompson,"
				+ " and Jason Fugett.  Their work is primarily motivated"
				+ " by requirements from the Senior Computer Engineering"
				+ " Design Lab at the Unviersity of Kansas, under the tutelage"
				+ " of Distinguished Professor Joe Evans.\n"
				+ "\n"
				+ "The primary goal of our project is to successfully fly a"
				+ " a modern quadcopter (DIY Drones' ArduCopter2) via an"
				+ " Android device.  While much of our preliminary work has"
				+ " been based off of the Xbee 2.4 GHz and 900 MHz modules,"
				+ " our final implementation will be direct WiFi communication"
				+ " through a WiFi chip and a capable mobile phone.\n"
				+ "\n"
				+ "We are not responsible for any damages to personnel or"
				+ " property.  However, we will offer the worthwhile advice"
				+ " of wearing safety goggles during flight tests, removing"
				+ " blades when setting up motors, and flying while tethered"
				+ " to the ground during indoor tests.\n"
				+ "\n"
				+ "This project will last as long as Eric, Doug, and Jason"
				+ " remain interested.  Please visit peregrineengineering.com"
				+ " for any comments or questions\n"
				+ "\n"
				+ "This last paragraph only has one purpose.  To test and"
				+ " ensure that my scrolling movement is working properly."
				+ " I very much appreciate you taking the time to read it.\n";

		// for(int x = 0; x <= 100; x++){
		// s += "Line: "+String.valueOf(x)+"\n";
		// }

		tv1.setMovementMethod(new ScrollingMovementMethod());

		tv1.setText(s);

	}
}