package com.swlkr.candyhearts;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class CandyHearts extends Activity implements OnClickListener {
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/BATMAN.TTF");
		TextView tv = (TextView) findViewById(R.id.main_title_textview);
		tv.setTypeface(tf);
        
        View button = findViewById(R.id.hearts_button);
        button.setOnClickListener(this);
    }
    
    public void onClick(View v){
    	switch(v.getId()){
    		case R.id.hearts_button:
    			Intent i = new Intent(this, Hearts.class);
    			startActivity(i);
    			break;
    	}
    }

    protected void onResume() {
    	super.onResume();
    }
    
    protected void onDestroy() {
    	super.onDestroy();
    }
    
    protected void onStop() {
    	super.onStop();
    }
    
    protected void onPause() {
    	super.onPause();
    }
	
}