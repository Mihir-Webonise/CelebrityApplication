package com.celebapp.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class FullSizeImageDisplayerActivity extends Activity {
	
	 private static final int PROGRESS = 0x1;

     private ProgressBar mProgress;
     private int mProgressStatus = 0;

     private int fileSize;
     private Handler mHandler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fullpic);
		Bundle extras = getIntent().getExtras();
		String url = extras.getString("imagesrc");
		Log.i("img url :::::", ""+url);
		//ProgressBar progressBar = (ProgressBar)findViewById(R.id.imgprogressBar);
		 mProgress = (ProgressBar) findViewById(R.id.imgprogressBar);
		ImageView fullImage = (ImageView)findViewById(R.id.fullimage);
		
		new Thread(new Runnable() {
            public void run() {
                while (mProgressStatus < 100) {
                    mProgressStatus = doWork();

                    try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            mProgress.setProgress(mProgressStatus);
                        }
                    });
                }
            }
        }).start();
    
		
		try{
	        //String url = "http://farm1.static.flickr.com/150/399390737_7a3d508730_b.jpg";			
	        //String url = "http://a3.sphotos.ak.fbcdn.net/hphotos-ak-ash4/s720x720/283743_471684339526187_1045487749_n.jpg";
			
	        Drawable image =ImageOperations(this,url);
	        fullImage.setImageDrawable(image);
	    }
	    catch(Exception ex)
	    {
	        ex.printStackTrace();
	    }


	    /*fullImage.setMinimumWidth(width);
	    fullImage.setMinimumHeight(height);
	    fullImage.setMaxWidth(width);
	    fullImage.setMaxHeight(height);*/
		
		
	}
	
	public Object fetch(String address) throws MalformedURLException,
    IOException {
        URL url = new URL(address);
        Object content = url.getContent();
        return content;
    } 
	
	public int doWork() {
		 
		while (fileSize <= 1000000) {
 
			fileSize++;
 
			if (fileSize == 100000) {
				return 10;
			} else if (fileSize == 200000) {
				return 20;
			} else if (fileSize == 300000) {
				return 30;
			} else if (fileSize == 400000) {
				return 40;
			} else if (fileSize == 500000) {
				return 50;
			} else if (fileSize == 600000) {
				return 60;
			} else if (fileSize == 700000) {
				return 70;
			} else if (fileSize == 800000) {
				return 80;
			} else if (fileSize == 900000) {
				return 90;
			} 
			// ...add your own
 
		}
 
		return 100;
 
	}

    private Drawable ImageOperations(Context ctx, String url) {
        try {
            InputStream is = (InputStream) this.fetch(url);
            Drawable d = Drawable.createFromStream(is, "src");
            return d;
        } catch (MalformedURLException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

}
