package com.mHere.mclock;

import java.util.List;

import com.mHere.mclock.clockManager.ClockManager;
import com.mHere.mclock.clockManager.Clocker;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ClockManager cm = new ClockManager(this);
		Clocker clock = new Clocker().setGroup("ggggg");
		cm.setClock(clock);
		cm.bindSSID2GRoup("yuriko3g", "ggggg");
		List<Clocker> a = cm.getClocks();
		cm.getClocks();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
