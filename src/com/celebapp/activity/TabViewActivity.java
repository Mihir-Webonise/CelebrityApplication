package com.celebapp.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.celebapp.activity.R;

public class TabViewActivity extends TabActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabtest);

		TabHost tabHost = getTabHost();

		// Tab for About
		TabSpec aboutspec = tabHost.newTabSpec("About");
		aboutspec.setIndicator("About", getResources().getDrawable(R.drawable.icon_offers));
		Intent songsIntent = new Intent(this, AboutActivity.class);
		aboutspec.setContent(songsIntent);

		// Tab for Feeds
		TabSpec feedsspec = tabHost.newTabSpec("Feeds");
		feedsspec.setIndicator("Feeds", getResources().getDrawable(R.drawable.icon_rule));
		Intent feedsIntent = new Intent(this, FeedsDisplayerActivity.class);
		feedsspec.setContent(feedsIntent);

		// Tab for Photos
		TabSpec photosspec = tabHost.newTabSpec("Photos");
		// setting Title and Icon for the Tab
		photosspec.setIndicator("Photos", getResources().getDrawable(R.drawable.icon_images));
		Intent photosIntent = new Intent(this, GridViewActivity.class);
		photosspec.setContent(photosIntent);

		// Tab for Photos
		TabSpec settingsspec = tabHost.newTabSpec("Settings");
		// setting Title and Icon for the Tab
		settingsspec.setIndicator("Settings", getResources().getDrawable(R.drawable.icon_setting));
		Intent settingsIntent = new Intent(this, SettingsActivity.class);
		settingsspec.setContent(settingsIntent);



		// Adding all TabSpec to TabHost
		tabHost.addTab(aboutspec); // Adding songs tab
		tabHost.addTab(feedsspec); // Adding videos tab
		tabHost.addTab(photosspec); // Adding photos tab
		tabHost.addTab(settingsspec); // Adding settings tab

		for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
		{
			tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#7392B5"));
		}    

		if(SettingsActivity.callBackSettings){
			/*Intent intent1 = new Intent(this, SettingsActivity.class);
        	settingsspec.setIndicator("Settings").setContent(intent1);*/
			tabHost.setCurrentTab(3);
		}
	}
}