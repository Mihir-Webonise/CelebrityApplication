package com.ecs.android.sample.twitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

public class CelebrityAppActivity extends Activity {
    /** Called when the activity is first created. */
	protected boolean _active = true;
	protected int _splashTime = 5000;
	Intent intent;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.splash);
	 
	    // thread for displaying the SplashScreen
	    Thread splashTread = new Thread() {
	        @Override
	        public void run() {
	            try {
	                int waited = 0;
	                while(_active && (waited < _splashTime)) {
	                    sleep(100);
	                    if(_active) {
	                        waited += 100;
	                    }
	                    intent = new Intent(CelebrityAppActivity.this, AndroidTwitterSample.class);
	                }
	            } catch(InterruptedException e) {
	                // do nothing
	            } finally {
	                finish(); 	        		
	        		startActivity(intent);
	                //stop();
	            }
	        }
	    };
	    splashTread.start();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	    if (event.getAction() == MotionEvent.ACTION_DOWN) {
	        _active = false;
	    }
	    return true;
	}
}