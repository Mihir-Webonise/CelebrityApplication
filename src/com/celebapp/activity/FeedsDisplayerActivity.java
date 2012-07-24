package com.celebapp.activity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.celebapp.activity.R;
import com.celebapp.utilities.RestClient;
import com.celebapp.utilities.TwitterUtils;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;

public class FeedsDisplayerActivity extends ListActivity{
	RestClient restClient = new RestClient();
	String json;
	JSONArray tweetsArray = null;
	JSONArray fbstatusesArray = null;
	ArrayList<String> tweetsList = new ArrayList<String>();
	ArrayList<String> fbstatusesList = new ArrayList<String>();
	JSONObject facebookfeeds;
	Button btnTwitterFeeds;
	Button btnFacebookFeeds;
	private SharedPreferences prefs;
	private static final String TEXT = "text";
	private static final String MESSAGE = "message";
	ListView mListView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feeds);
		this.prefs = PreferenceManager.getDefaultSharedPreferences(this);


		btnTwitterFeeds = (Button)findViewById(R.id.btnTwitterFeeds);
		btnTwitterFeeds.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				if (TwitterUtils.isAuthenticated(prefs)) {

					
					if(SettingsActivity.USETWITTER){
						displayLatestTweets();
					}
					else{
						displayTweets();
					}
				}
				else{
					Toast.makeText(getBaseContext(),
							"Please provide Twitter credentials under settings tab.",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		btnFacebookFeeds = (Button)findViewById(R.id.btnFacebookFeeds);
		btnFacebookFeeds.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				if (FacebookActivity.facebook.isSessionValid()) {													
					if(SettingsActivity.USEFACEBOOK){
						displayLatestFacebookUpdates();
					}
					else{
						displayFacebookUpdates();
					}
				}
				else{
					Toast.makeText(getBaseContext(),
							"Please provide Facebook credentials under settings tab.",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}



	public void displayLatestTweets(){
		try {
			tweetsList.clear();
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);		
			nameValuePairs.add(new BasicNameValuePair("q", "@Ash_Rai_Bach"));
			nameValuePairs.add(new BasicNameValuePair("result_type", "mixed"));
			try {
				json = restClient.doApiCall("search.json", "GET", nameValuePairs);
				Log.i("json::::::::::", String.valueOf(json));

			} catch (ClientProtocolException e) {
				Log.i("ClientProtocolException::::::::::", String.valueOf(e));
			} catch (IOException e) {

				e.printStackTrace();
				Log.i("IOException::::::::::", String.valueOf(e));
			}				
			JSONObject jsonTweets = new JSONObject(json);
			Log.i("json::::::::::", String.valueOf(jsonTweets));
			tweetsArray = jsonTweets.getJSONArray("results");			
			for(int i = 0; i < tweetsArray.length(); i++){
				JSONObject tweet = tweetsArray.getJSONObject(i);	

				String tweetText = tweet.getString(TEXT);
				tweetsList.add(tweetText);
			}
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, tweetsList);
			setListAdapter(adapter);				
		} catch (JSONException e) {			
			e.printStackTrace();
		}

	}

	public void displayLatestFacebookUpdates(){

		Bundle b = new Bundle();
		b.putString("access_token", FacebookActivity.facebook.getAccessToken());
		b.putString("query", "SELECT message FROM status WHERE uid = '100000538743561'");
		b.putString("format", "json");
		fbstatusesList.clear();

		try {
			String myResult = Util.openUrl("https://api.facebook.com/method/fql.query", "GET", b);
			Log.i("MyResult", myResult);
			myResult = " { \"data\":"+myResult+"}";
			Log.i("NewMyResult::", myResult);
			facebookfeeds = Util.parseJson(myResult);
			fbstatusesArray = facebookfeeds.getJSONArray("data");
			for(int i = 0; i < fbstatusesArray.length(); i++){
				JSONObject fbstatus = fbstatusesArray.getJSONObject(i);	

				String fbstatusText = fbstatus.getString(MESSAGE);
				Log.i("fbstatusText::::"," Status msg ::"+fbstatusText);
				fbstatusesList.add(fbstatusText);
			}
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, fbstatusesList);
			setListAdapter(adapter);			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FacebookError e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void displayTweets(){
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, tweetsList);
		setListAdapter(adapter);
	}

	public void displayFacebookUpdates(){
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, fbstatusesList);
		setListAdapter(adapter);
	}

}
