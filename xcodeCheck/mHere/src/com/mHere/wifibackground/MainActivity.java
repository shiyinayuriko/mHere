package com.mHere.wifibackground;

import com.mHere.common.imageLoader.AsyImageLoader;
import com.mHere.wifibackground.contentProvider.WifiContentProvider;
import com.mHere.wifibackground.contentProvider.WifiListProvider;
import com.mHere.wifibackground.notification.MyNotification;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		startService(new Intent("com.wifibackground.service"));
		Cursor c = getContentResolver().query(WifiListProvider.CONTENT_URI, null, null, null, null);
//		while(c.moveToNext()){
//			Log.i(c.getString(c.getColumnIndex("SSID"))+c.getString(c.getColumnIndex("enduptime")), "aaa");
//			Toast.makeText(this, c.getString(c.getColumnIndex("SSID"))+c.getString(c.getColumnIndex("enduptime")), Toast.LENGTH_LONG).show();
//		}
		
		AsyImageLoader.showImageAsyn((ImageView) findViewById(R.id.imageView1), "http://s1.bdstatic.com/r/www/cache/static/global/img/icons_15d9b398.png", R.drawable.ic_launcher);
//		MyNotification.show(this, "title","text");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	

}
