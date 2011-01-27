package com.swlkr.candyhearts;

import java.util.Random;
import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.graphics.Color;
//import android.graphics.drawable.TransitionDrawable;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class CandyHearts extends Activity implements AccelerometerListener {
	
	private static Context _context;
	//private int forwardsCounter = 0, backwardsCounter = 0;
	//private boolean wasMovingForward = true;
	private ImageView candyHeartImageView;
	private Random generator;
	private TypedArray heartArray;
	private Animation hyperspaceJump;
	private int lastRandomInt;

    /**
     * onShake callback 
     */
	public void onShake(float force) {
		
		candyHeartImageView.startAnimation(hyperspaceJump);
		
		//if(hyperspaceJump.hasEnded())
			
		
		/*TransitionDrawable drawable = (TransitionDrawable) candyHeart.getDrawable();		

		if(backwardsCounter > forwardsCounter || forwardsCounter == 2) {
			// Moving backwards
			drawable.reverseTransition(500);
			wasMovingForward = false;
		}
		else if(forwardsCounter > backwardsCounter) {
			// Moving forwards
			drawable.startTransition(500);
			wasMovingForward = true;
		}
		else if(forwardsCounter == backwardsCounter) {
			// Extra boolean flag that remembers the last state
			if(wasMovingForward) {
				drawable.startTransition(500);
			}
			else {
				drawable.reverseTransition(500);
			}
		}
		
		backwardsCounter = forwardsCounter;
		
		if(wasMovingForward)
			forwardsCounter++;
		else
			forwardsCounter--;
		
		/*
		

		*/
	}
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        _context = this;
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        candyHeartImageView = (ImageView)findViewById(R.id.image_view);
        generator = new Random();
        heartArray = this.getResources().obtainTypedArray(R.array.imageHeartArray);
        hyperspaceJump = AnimationUtils.loadAnimation(this, R.anim.hyperspace_jump);
    }

    protected void onResume() {
    	super.onResume();
    	if (AccelerometerManager.isSupported()) {
    		AccelerometerManager.startListening(this);
    	}
    	
    	hyperspaceJump.setAnimationListener(new AnimationListener() {
    		public void onAnimationEnd(Animation anim)
    		{
    			int randomInt = generator.nextInt(heartArray.length());
    	    	
    			while(lastRandomInt == randomInt) {
    				randomInt = generator.nextInt(heartArray.length());
    			}
    			
    			candyHeartImageView.setImageDrawable(heartArray.getDrawable(randomInt));
    			candyHeartImageView.setAlpha(0);
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