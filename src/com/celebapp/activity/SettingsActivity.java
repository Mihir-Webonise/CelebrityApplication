package com.celebapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.celebapp.activity.R;
import com.celebapp.utilities.TwitterUtils;

public class SettingsActivity extends Activity {

	private SharedPreferences prefs;	
	TextView usingTwitterlbl;
	ToggleButton twitterToggleButton;
	TextView usingFacebooklbl;
	ToggleButton facebookToggleButton;
	Button btnClearTwitterCredentials;
	Button btnClearFacebookCredentials;
	Button btnProvideTwitterCredentials;
	Button btnProvideFacebookCredentials;
	Button btnHome;
	Intent intent;
	public static boolean USETWITTER;
	public static boolean USEFACEBOOK;
	public static boolean callBackSettings = false;


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newsettings);
		this.prefs = PreferenceManager.getDefaultSharedPreferences(this);

		usingTwitterlbl = (TextView)findViewById(R.id.textViewUsingTwitter);
		twitterToggleButton = (ToggleButton)findViewById(R.id.toggleButtonTwitter);
		usingFacebooklbl = (TextView)findViewById(R.id.textViewUsingFacebook);
		facebookToggleButton = (ToggleButton)findViewById(R.id.toggleButtonFacebook);
		btnProvideTwitterCredentials = (Button)findViewById(R.id.btnTwitterLogin);
		btnProvideFacebookCredentials = (Button)findViewById(R.id.btnFacebookLogin);		

		if (TwitterUtils.isAuthenticated(prefs)) {
			USETWITTER = prefs.getBoolean("USETWITTER" , USETWITTER);
			twitterToggleButton.setChecked(USETWITTER);
			btnProvideTwitterCredentials.setEnabled(false);
		}
		else{
			USETWITTER = false;
			twitterToggleButton.setChecked(USETWITTER);
		}

		if (FacebookActivity.facebook.isSessionValid()) {
			USEFACEBOOK = prefs.getBoolean("USEFACEBOOK" , USEFACEBOOK);
			facebookToggleButton.setChecked(USEFACEBOOK);
			btnProvideFacebookCredentials.setEnabled(false);
		}
		else{
			USEFACEBOOK = false;
			facebookToggleButton.setChecked(USEFACEBOOK);
		}

		btnProvideFacebookCredentials.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				intent = new Intent(SettingsActivity.this, FacebookActivity.class);
				//intent.putExtra("callback_activity", "SettingsActivity");
				callBackSettings = true;
				startActivity(intent); 
			}
		});



		btnProvideTwitterCredentials.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				intent = new Intent(SettingsActivity.this, TwitterActivity.class);
				//intent.putExtra("callback_activity", "SettingsActivity");
				callBackSettings = true;
				startActivity(intent); 
			}
		});



		twitterToggleButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{								
				setTwitterToggleButton();
			}
		});

		facebookToggleButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				setFacebookToggleButton();
			}
		});


	}



	public void setFacebookToggleButton(){
		if(!USEFACEBOOK){
			if(FacebookActivity.facebook.isSessionValid()){
				USEFACEBOOK=true;
				prefs.edit().putBoolean("USEFACEBOOK", USEFACEBOOK).commit();
				usingFacebooklbl.setText("Fetching latest Facebook Feeds : YES");
				facebookToggleButton.setChecked(true);

			}
			else{
				USEFACEBOOK=false;
				prefs.edit().putBoolean("USEFACEBOOK", USEFACEBOOK).commit();
				usingFacebooklbl.setText("Fetching latest Facebook Feeds : NO");
				Toast.makeText(getBaseContext(),
						"Please provide Facebook credentials",
						Toast.LENGTH_SHORT).show();
				facebookToggleButton.setChecked(false);

			}					
		}
		else{
			USEFACEBOOK=false;
			prefs.edit().putBoolean("USEFACEBOOK", USEFACEBOOK).commit();
			usingFacebooklbl.setText("Fetching latest Facebook Feeds : NO");
			facebookToggleButton.setChecked(false);

		}
	}

	public void setTwitterToggleButton(){
		if(!USETWITTER){

			if (TwitterUtils.isAuthenticated(prefs)) {
				USETWITTER=true;
				prefs.edit().putBoolean("USETWITTER", USETWITTER).commit();
				usingTwitterlbl.setText("Fetching latest Twitter Feeds : YES");
				twitterToggleButton.setChecked(true);
			}
			else{
				Toast.makeText(getBaseContext(),
						"Please provide Twitter credentials",
						Toast.LENGTH_LONG).show();
				USETWITTER=false;
				prefs.edit().putBoolean("USETWITTER", USETWITTER).commit();
				usingTwitterlbl.setText("Fetching latest Twitter Feeds : NO");
				twitterToggleButton.setChecked(false);
				Toast.makeText(getBaseContext(),
						"Please provide Twitter credentials",
						Toast.LENGTH_SHORT).show();
				facebookToggleButton.setChecked(false);
			}										
		}
		else{
			USETWITTER=false;
			prefs.edit().putBoolean("USETWITTER", USETWITTER).commit();
			usingTwitterlbl.setText("Fetching latest Twitter Feeds : NO");
			twitterToggleButton.setChecked(false);
		}
	}
}
