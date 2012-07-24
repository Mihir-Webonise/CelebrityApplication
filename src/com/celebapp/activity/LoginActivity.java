package com.celebapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.celebapp.activity.R;

public class LoginActivity extends Activity {

	Intent intent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button btnFacebookLogin = (Button) findViewById(R.id.btnfbconnector);
		Button btnTwitterLogin = (Button) findViewById(R.id.btn_tweet);
		
		btnTwitterLogin.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				intent = new Intent(LoginActivity.this, TwitterActivity.class);
				intent.putExtra("callback_activity", "TabViewActivity");
				startActivity(intent); 
			}
		});

		btnFacebookLogin.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				intent = new Intent(LoginActivity.this, FacebookActivity.class);
				intent.putExtra("callback_activity", "TabViewActivity");
				startActivity(intent); 
			}
		});

	}

}
