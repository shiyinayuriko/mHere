package com.mHere.wifibackground.servicecontrol;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class AutoServiceReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(intent.getAction(),"aaa");
		//×Ô¶¯Æô¶¯
		if (intent.getAction().equals(Intent.ACTION_TIME_TICK) || 
			intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) { 
			ActivityManager manager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
			boolean test =true;
			for (RunningServiceInfo service :manager.getRunningServices(Integer.MAX_VALUE)) { 
				if("com.mHere.wifibackground.servicecontrol.WifiScanService".equals(service.service.getClassName()))
					test = false;
			}
			if(test) context.startService(new Intent("com.wifibackground.service")); 
//			context.sendBroadcast(new Intent("com.wifibackground.server.enterNewWifi")
//			.putExtra("ssid", "yuriko_3G")
//			.putExtra("password", "12345")
//			.putExtra("type", "WIFICIPHER_WEP"));
		}else{
			Bundle extras = new Bundle();
			extras.putAll(intent.getExtras() == null?new Bundle():intent.getExtras());
			extras.putString("action", intent.getAction());
			context.startService(new Intent("com.wifibackground.service").putExtras(extras));
		}
	}
}
