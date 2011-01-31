package com.swlkr.candyhearts;

import java.util.Random;

import com.admob.android.ads.AdManager;
import com.admob.android.ads.AdView;

import android.app.Activity;
import android.os.Bundle;
import android.os.PowerManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Hearts extends Activity implements AccelerometerListener {
	
	private static Context _context;
	private ImageView candyHeartImageView;
	private Random generator;
	private TypedArray heartArray;
	private Animation hyperspaceJump, fadeIn, fadeOut, enterStageRight, enterStageLeft, hyperspaceJumpIn;
	private int lastRandomInt;
	private static DisplayMetrics displayMetrics;
	private static PowerManager pm;

    /**
     * onShake callback 
     */
	public void onShake(float force) {
		
		int randomInt = generator.nextInt(3);
		pm.userActivity(System.currentTimeMillis(), false);
		
		if(randomInt == 1)		
			candyHeartImageView.startAnimation(enterStageLeft);
		
		if(randomInt == 2)
			candyHeartImageView.startAnimation(fadeOut);
	}
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hearts);
        _context = this;
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        candyHeartImageView = (ImageView)findViewById(R.id.image_view);
        generator = new Random();
        heartArray = this.getResources().obtainTypedArray(R.array.hearts);
        hyperspaceJump = AnimationUtils.loadAnimation(this, R.anim.hyperspace_jump);
        hyperspaceJumpIn = AnimationUtils.loadAnimation(this, R.anim.hyperspace_jump_in);
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        enterStageLeft = AnimationUtils.loadAnimation(this, R.anim.enter_stage_left);
        enterStageRight = AnimationUtils.loadAnimation(this, R.anim.enter_stage_right);
        displayMetrics = new DisplayMetrics();
        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);  
        
        AdManager.setTestDevices( new String[] { 
			      AdManager.TEST_EMULATOR,
			      "43F72ACAB2B37D4FC2198747189A0D62"
			});
    }

    protected void onResume() {
    	super.onResume();
    	
    	AdView adView = (AdView) findViewById(R.id.ad);
		adView.requestFreshAd();
		
    	if (AccelerometerManager.isSupported()) {
    		AccelerometerManager.startListening(this);
    	}
    	
    	fadeOut.setAnimationListener(new AnimationListener() {
    		public void onAnimationEnd(Animation anim)
    		{
    			int randomInt = generator.nextInt(heartArray.length());
    	    	
    			while(lastRandomInt == randomInt) {
    				randomInt = generator.nextInt(heartArray.length());
    			}
    			
    			Drawable drawable = heartArray.getDrawable(randomInt);
    			    			
    			candyHeartImageView.setImageDrawable(drawable);
    			candyHeartImageView.startAnimation(fadeIn);
    			
    			lastRandomInt = randomInt;
    		}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationStart(Animation animation) {
				
				
			}
    	});
    	
    	enterStageLeft.setAnimationListener(new AnimationListener() {
    		public void onAnimationEnd(Animation anim)
    		{
    			int randomInt = generator.nextInt(heartArray.length());
    	    	
    			while(lastRandomInt == randomInt) {
    				randomInt = generator.nextInt(heartArray.length());
    			}
    			
    			Drawable drawable = heartArray.getDrawable(randomInt);
    			    			
    			candyHeartImageView.setImageDrawable(drawable);
    			candyHeartImageView.startAnimation(enterStageRight);
    			
    			lastRandomInt = randomInt;
    		}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationStart(Animation animation) {
				
				
			}
    	});
    	
    	hyperspaceJump.setAnimationListener(new AnimationListener() {
    		public void onAnimationEnd(Animation anim)
    		{
    			
    			int randomInt = generator.nextInt(heartArray.length());
    	    	
    			while(lastRandomInt == randomInt) {
    				randomInt = generator.nextInt(heartArray.length());
    			}
    			
    			Drawable drawable = heartArray.getDrawable(randomInt);
    			    			
    			candyHeartImageView.setImageDrawable(drawable);
    			
    			lastRandomInt = randomInt;
    			
    			candyHeartImageView.startAnimation(hyperspaceJumpIn);
    		}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationStart(Animation animation) {
				
				
			}
    	});

    }
    
    protected void onDestroy() {
    	super.onDestroy();
    	if (AccelerometerManager.isListening()) {
    		AccelerometerManager.stopListening();
    	}
    }
    
    protected void onStop() {
    	super.onStop();
    	if(AccelerometerManager.isListening()) {
    		AccelerometerManager.stopListening();
    	}
    }
    
    protected void onPause() {
    	super.onPause();
    	if(AccelerometerManager.isListening()) {
    		AccelerometerManager.stopListening();
    	}
    }
	
    public static Context getContext() {
		return _context;
	}

	/**
	 * onAccelerationChanged callback
	 */
	public void onAccelerationChanged(float x, float y, float z) {
	}
	
}