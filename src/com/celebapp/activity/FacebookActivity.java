package com.celebapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.celebapp.activity.R;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

public class FacebookActivity extends Activity {
	/** Called when the activity is first created. */

	// replace it with your own Facebook App ID
	// public final String facebook_APP_ID = "358310527573301";
	// public static boolean isFbLoggedIn = false;

	Intent intent;
	public static String myResult;
	public static boolean isFbLoggedIn=false;
	public static Facebook facebook = new Facebook("358310527573301");
	public static AsyncFacebookRunner mAsyncRunner = new AsyncFacebookRunner(facebook); 
	String permissions[] = { "user_about_me", "user_activities",
			"user_birthday", "user_checkins", "user_education_history",
			"user_events", "user_groups", "user_hometown", "user_interests",
			"user_likes", "user_location", "user_notes",
			"user_online_presence", "user_photo_video_tags", "user_photos",
			"user_relationships", "user_relationship_details",
			"user_religion_politics", "user_status", "user_videos",
			"user_website", "user_work_history", "email",
			"read_friendlists", "read_insights", "read_mailbox",
			"read_requests", "read_stream", "xmpp_login", "ads_management",
			"create_event", "manage_friendlists", "manage_notifications",
			"offline_access", "publish_checkins", "publish_stream",
			"rsvp_event", "sms",
			// "publish_actions",

			"manage_pages"

	};
	public static SharedPreferences mPrefs;
	Bundle bundle = new Bundle();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.facebook);

		mPrefs = getPreferences(MODE_PRIVATE);
		String access_token = mPrefs.getString("access_token", null);
		long expires = mPrefs.getLong("access_expires", 0);
		if (access_token != null) {
			facebook.setAccessToken(access_token);
		}
		if (expires != 0) {
			facebook.setAccessExpires(expires);
		}

		/*
		 * Only call authorize if the access_token has expired.
		 */
		if (!facebook.isSessionValid()) {

			facebook.authorize(this, permissions,Facebook.FORCE_DIALOG_AUTH, new DialogListener() {
				@Override
				public void onComplete(Bundle values) {
					SharedPreferences.Editor editor = mPrefs.edit();
					editor.putString("access_token", facebook.getAccessToken());
					editor.putLong("access_expires",
							facebook.getAccessExpires());
					editor.commit();
					Log.d("facebook", "complete");
					Log.d("access_token ::::", facebook.getAccessToken());
                     
					isFbLoggedIn=true;
					/*Bundle extras = getIntent().getExtras();
					String callback_activity = extras.getString("callback_activity");
					Log.i("callback_activity :::::", ""+callback_activity);*/
					intent = new Intent(getApplicationContext(), TabViewActivity.class);
					/*if(callback_activity.equalsIgnoreCase("SettingsActivity")){
						intent = new Intent(getApplicationContext(), SettingsActivity.class);
					}
					else{
						intent = new Intent(getApplicationContext(), TabViewActivity.class);
					}*/
					startActivity(intent);
				}

				@Override
				public void onFacebookError(FacebookError error) {
					Log.i("FacebookError", "FacebookError");
				}

				@Override
				public void onError(DialogError e) {
					Log.i("Error", "Error");
				}

				@Override
				public void onCancel() {
					Log.i("Cancel", "Cancel");
				}
			});
		} else {
			isFbLoggedIn=true;
			/*Bundle extras = getIntent().getExtras();
			String callback_activity = extras.getString("callback_activity");
			Log.i("callback_activity :::::", ""+callback_activity);*/
			intent = new Intent(getApplicationContext(), TabViewActivity.class);			
			startActivity(intent);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		facebook.authorizeCallback(requestCode, resultCode, data);
	}
}
