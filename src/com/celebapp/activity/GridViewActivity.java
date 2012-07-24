package com.celebapp.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.celebapp.utilities.LazyAdapter;
import com.celebapp.utilities.RestClient;

public class GridViewActivity extends Activity{

	LazyAdapter adapter;
	RestClient restClient = new RestClient();
	String json;
	GridView gridview;
	JSONArray photoDetailsArray = null;

	ArrayList<String> photoSourceList = new ArrayList<String>();
	String[] photoSourceArray;
	Intent intent;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridview);

		gridview = (GridView) findViewById(R.id.gridview);
		/*gridview.setAdapter(new ImageAdapter(this));

	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            Toast.makeText(GridViewActivity.this, "" + position, Toast.LENGTH_SHORT).show();
	        }
	    });
	}

}
		 */
		if(FacebookActivity.facebook.isSessionValid()){

			//Button b=(Button)findViewById(R.id.button1);
			//b.setOnClickListener(listener);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);		
			nameValuePairs.add(new BasicNameValuePair("access_token", FacebookActivity.facebook.getAccessToken()));
			//nameValuePairs.add(new BasicNameValuePair("result_type", "mixed"));
			try {
				json = restClient.doNewApiCall("https://graph.facebook.com/469575279737093/photos", "GET", nameValuePairs);
				Log.i("json::::::::::", String.valueOf(json));
				//String stringUrl = "https://api.zomato.com/v1/cuisines.json?apikey=4fd09cc934aa62322962394fd09cc934&city_id=5";
				//json = restClient.getResponseText(stringUrl);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				Log.i("ClientProtocolException::::::::::", String.valueOf(e));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.i("IOException::::::::::", String.valueOf(e));
			}
			enlistPhotoSourceDetails();

			photoSourceArray = new String[photoSourceList.size()];
			int cnt=0;
			for(String photosrc : photoSourceList){
				photoSourceArray[cnt] = photosrc;
				cnt++;
			}


			//list=(ListView)findViewById(R.id.list);
			adapter=new LazyAdapter(this, photoSourceArray);
			gridview.setAdapter(adapter);
			gridview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View vi, int position,
						long id) {
					// TODO Auto-generated method stub
					String photosrc = photoSourceArray[position];
					Log.i("img photosrc :::::", ""+photosrc);
					intent = new Intent(GridViewActivity.this,FullSizeImageDisplayerActivity.class);
					intent.putExtra("imagesrc", ""+photosrc);
					startActivity(intent);
				}
			});

		}
		else{
			Toast.makeText(getBaseContext(),
					"Please enable Facebook settings under Settings Tab.",
					Toast.LENGTH_LONG).show();
		}

	}

	public void enlistPhotoSourceDetails(){
		try {

			JSONObject jsonPhotos = new JSONObject(json);
			Log.i("json::::::::::", String.valueOf(jsonPhotos));
			photoDetailsArray = jsonPhotos.getJSONArray("data");
			//System.out.println(""+json);
			for(int i = 0; i < photoDetailsArray.length(); i++){
				JSONObject photoSource = photoDetailsArray.getJSONObject(i);	

				String photoSourceText = photoSource.getString("source");
				photoSourceList.add(photoSourceText);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onDestroy()
	{
		gridview.setAdapter(null);
		super.onDestroy();
	}
	 


	/*public OnClickListener listener=new OnClickListener(){
		@Override
		public void onClick(View arg0) {
			adapter.imageLoader.clearCache();
			adapter.notifyDataSetChanged();
		}
	};*/

	/*public OnClickListener itemClickListener=new OnClickListener(){
		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent(PhotosActivity.this,
					FullSizeImageDisplayerActivity.class);
			startActivity(intent);
		}
	};*/


}