package com.example.broadcast;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		startService(new Intent("com.wifibackground.service"));
		sendBroadcast(new Intent("com.wifibackground.server.enterWifi")
						.putExtra("ssid", "whitecomet")
						.putExtra("password", "shiyinayuriko")
						.putExtra("type", "WIFICIPHER_NOPASS"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
