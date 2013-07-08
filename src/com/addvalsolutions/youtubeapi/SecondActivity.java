package com.addvalsolutions.youtubeapi;

import addvalsolutions.videosearch.R;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class SecondActivity extends FragmentActivity {

	Button ytube;
	Button fb;
	String video="";
	TextView tv_watch,tv_share;
	private MainFragment mainFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		// Show the Up button in the action bar.
		setupActionBar();
		tv_watch=(TextView)findViewById(R.id.tv_watch);
		ytube=(Button)findViewById(R.id.play_video);
		fb=(Button)findViewById(R.id.share_fb);
		Intent intent=getIntent();
		Bundle b=intent.getExtras();
		video=b.getString("video");
		
		
		ytube.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(video)));
			}
		});
		fb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mainFragment=new MainFragment();
				Bundle b=new Bundle();
				b.putString("video", video);
				mainFragment.setArguments(b);
				getSupportFragmentManager().beginTransaction().add(android.R.id.content, mainFragment).commit();
			}
		});
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.second, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
