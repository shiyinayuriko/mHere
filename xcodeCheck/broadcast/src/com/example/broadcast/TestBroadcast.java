package com.example.broadcast;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.util.Log;

/**
 * 
 * @author yuriko
 * require manifest receiver,intent-filter="com.seven.broadcast"
 */
public class TestBroadcast extends BroadcastReceiver  {

	@Override
	public void onReceive(Context context, Intent intent) {
		//接收到消息时，打印
//		BroadCastDataTestString i = (BroadCastDataTestString) intent.getSerializableExtra("user");
//		Log.i("bbb","bbb"+i.getinfo());
		ArrayList<WifiConfiguration> wifiConfigList = intent.getParcelableArrayListExtra("wifiConfigList");
		ArrayList<ScanResult> scanList = intent.getParcelableArrayListExtra("scanList");
		WifiConfiguration config = wifiConfigList.get(0);
		Log.i(intent.getAction(),"aaabbb");
	}

}
