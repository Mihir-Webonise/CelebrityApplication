package com.celebapp.activity;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.celebapp.activity.R;
import com.celebapp.utilities.TwitterUtils;

public class TwitterActivity extends Activity {

	private SharedPreferences prefs;
	public static boolean isTwitterLoggedIn=false;
	private final Handler mTwitterHandler = new Handler();
	
	Intent intent;
	
    final Runnable mUpdateTwitterNotification = new Runnable() {
        public void run() {
        	Toast.makeText(getBaseContext(), "Tweet sent !", Toast.LENGTH_LONG).show();
        }
    };
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twitter);
        this.prefs = PreferenceManager.getDefaultSharedPreferences(this);

        	/**
        	 * Send a tweet. If the user hasn't authenticated to Tweeter yet, he'll be redirected via a browser
        	 * to the twitter login page. Once the user authenticated, he'll authorize the Android application to send
        	 * tweets on the users behalf.
        	 */
           
            	if (TwitterUtils.isAuthenticated(prefs)) {
            		//sendTweet();
            		Toast.makeText(getBaseContext(),
							"Twitter login successful.",
							Toast.LENGTH_SHORT).show();
					//sendTweet();
            		isTwitterLoggedIn=true;
					
					/*Bundle extras = getIntent().getExtras();
					String callback_activity = extras.getString("callback_activity");
					Log.i("callback_activity :::::", ""+callback_activity);*/
					/*if(callback_activity.equalsIgnoreCase("SettingsActivity")){
						intent = new Intent(getApplicationContext(), SettingsActivity.class);
					}
					else{
						intent = new Intent(getApplicationContext(), TabViewActivity.class);
					}*/
            		intent = new Intent(getApplicationContext(), TabViewActivity.class);
					startActivity(intent);
            	} else {
    				Intent i = new Intent(getApplicationContext(), PrepareRequestTokenActivity.class);
    				i.putExtra("tweet_msg",getTweetMsg());
    				startActivity(i);
            	}
   
	}
	
	

	private String getTweetMsg() {
		return "Tweeting from Android App at " + new Date().toLocaleString();
	}	
	
	/*public void sendTweet() {
		Thread t = new Thread() {
	        public void run() {
	        	
	        	try {
	        		TwitterUtils.sendTweet(prefs,getTweetMsg());
	        		mTwitterHandler.post(mUpdateTwitterNotification);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
	        }

	    };
	    t.start();
	}*/


}