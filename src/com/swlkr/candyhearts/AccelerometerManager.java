package com.swlkr.candyhearts;

import java.util.List;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class AccelerometerManager {
	
	private static Sensor sensor;
	private static SensorManager sensorManager;
	// you could use an OrientationListener array instead
	// if you plans to use more than one listener
	private static AccelerometerListener listener;
	
	/** indicates whether or not Accelerometer Sensor is supported */
	private static Boolean supported;
	/** indicates whether or not Accelerometer Sensor is running */
	private static boolean running = false;
	
	/**
	 * Returns true if the manager is listening to orientation changes
	 */
	public static boolean isListening() {
		return running;
	}
	
	/**
	 * Unregisters listeners
	 */
	public static void stopListening() {
		running = false;
		try {
			if (sensorManager != null && sensorEventListener != null) {
				sensorManager.unregisterListener(sensorEventListener);
			}
		} catch (Exception e) {}
	}
	
	/**
	 * Returns true if at least one Accelerometer sensor is available
	 */
	public static boolean isSupported() {
		if (supported == null) {
			if (CandyHearts.getContext() != null) {
				sensorManager = (SensorManager) CandyHearts.getContext().
						getSystemService(Context.SENSOR_SERVICE);
				List<Sensor> sensors = sensorManager.getSensorList(
						Sensor.TYPE_ACCELEROMETER);
				supported = new Boolean(sensors.size() > 0);
			} else {
				supported = Boolean.FALSE;
			}
		}
		return supported;
	}

	/**
	 * Configure the listener for shaking
	 * @param threshold
	 * 			minimum acceleration variation for considering shaking
	 * @param interval
	 * 			minimum interval between to shake events
	 */
	public static void configure(int threshold, int interval) {
	}

	/**
	 * Registers a listener and start listening
	 * @param accelerometerListener
	 * 			callback for accelerometer events
	 */
	public static void startListening(
			AccelerometerListener accelerometerListener) {
		sensorManager = (SensorManager) CandyHearts.getContext().
				getSystemService(Context.SENSOR_SERVICE);
		List<Sensor> sensors = sensorManager.getSensorList(
				Sensor.TYPE_ACCELEROMETER);
		if (sensors.size() > 0) {
			sensor = sensors.get(0);
			running = sensorManager.registerListener(
					sensorEventListener, sensor, 
					SensorManager.SENSOR_DELAY_NORMAL);
			listener = accelerometerListener;
		}
	}
	
	/**
	 * Configures threshold and interval
	 * And registers a listener and start listening
	 * @param accelerometerListener
	 * 			callback for accelerometer events
	 * @param threshold
	 * 			minimum acceleration variation for considering shaking
	 * @param interval
	 * 			minimum interval between to shake events
	 */
	public static void startListening(
			AccelerometerListener accelerometerListener, 
			int threshold, int interval) {
		configure(threshold, interval);
		startListening(accelerometerListener);
	}

	/**
	 * The listener that listen to events from the accelerometer listener
	 */
	private static SensorEventListener sensorEventListener = 
		new SensorEventListener() {
		
		private float mAccel = 0.00f; // acceleration apart from gravity
		private float mAccelCurrent = SensorManager.GRAVITY_EARTH; // current acceleration including gravity
		private float mAccelLast = SensorManager.GRAVITY_EARTH; // last acceleration including gravity
		private long lastShaken, now;
		
		public void onAccuracyChanged(Sensor sensor, int accuracy) {}
		
		public void onSensorChanged(SensorEvent event) {
			
			now = System.currentTimeMillis();
			
			if(now - lastShaken > 500) {
			
				float x = event.values[0];
			    float y = event.values[1];
			    float z = event.values[2];
			    mAccelLast = mAccelCurrent;
			    mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
			    float delta = mAccelCurrent - mAccelLast;
			    mAccel = mAccel * 0.9f + delta; // perform low-cut filter
			    
			    if(mAccel > 2) {
			    	listener.onShake(mAccel);
			    	lastShaken = System.currentTimeMillis();
			    }
			}
		}
		
	};

}
