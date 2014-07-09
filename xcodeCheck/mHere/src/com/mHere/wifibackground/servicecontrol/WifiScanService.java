package com.mHere.wifibackground.servicecontrol;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mHere.common.httpnet.GetPostThread;
import com.mHere.common.httpnet.ResultHandler;
import com.mHere.wifibackground.contentProvider.database.ProviderDataBase;
import com.mHere.wifibackground.contentProvider.database.WifiDurationDataTable;
import com.mHere.wifibackground.contentProvider.database.WifiInfoDataTable;
import com.mHere.wifibackground.log.LogDataBase;
import com.mHere.wifibackground.log.WifiChangeLogDataTable;
import com.mHere.wifibackground.log.WifiScanLogDataTable;
import com.mHere.wifibackground.log.WifiScanResultDataTable;
import com.mHere.wifibackground.notification.MyNotification;
import com.mHere.wifibackground.wificontrol.WifiScanner;
import com.mHere.wifibackground.wificontrol.WifiScanner.WifiCipherType;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;

public class WifiScanService extends Service {
	private WifiScanner ws =null;
	private LogDataBase dbh = new LogDataBase(WifiScanService.this);
	private WifiScanLogDataTable scanLog = new WifiScanLogDataTable(dbh);
	private WifiChangeLogDataTable changeLog = new WifiChangeLogDataTable(dbh);
	private WifiScanResultDataTable scanRLog = new WifiScanResultDataTable(dbh);
	private WifiInfo wifiInfo;
	private WakeLock wakeLock;
	public static String URL = "http://10.131.228.215/mHere.php";
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null ;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
//        wakeLock.release();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		
		 PowerManager pm = (PowerManager)this.getSystemService(Context.POWER_SERVICE); 
         wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,this.getClass().getCanonicalName()); 
         wakeLock.acquire(); 
         
		 ws = new WifiScanner(this,"wifibackground");
		 wifiInfo = ws.getWifiInfo();
		IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);   
	    AutoServiceReceiver receiver = new AutoServiceReceiver();   
	    registerReceiver(receiver, filter);
		Log.i("creat", "aaa");
		Timer mTimer = new Timer();
		mTimer.schedule(new MyTimerTask(), 0 , 10 * 1000);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("start","aaa");
		//TODO dispatch
		if(intent!=null && intent.getExtras()!=null){
			String action = intent.getStringExtra("action");
			if(action.equals("com.wifibackground.server.enterNewWifi")) enterNewWifi(intent);
			if(action.equals("com.wifibackground.server.enterExistWifi")) enterExistWifi(intent);
			if(action.equals("com.wifibackground.server.deleteExistWifi")) deleteExistWifi(intent);
		}
		return START_STICKY;
	}
	


	private boolean enterNewWifi(Intent intent){
        String ssid = intent.getStringExtra("ssid");
        String password = intent.getStringExtra("password");
        String type = intent.getStringExtra("type");
        if("WIFICIPHER_WEP".equals(type)) return ws.addNetWordLink(ssid, password, WifiCipherType.WIFICIPHER_WEP);
        else if("WIFICIPHER_WPA".equals(type)) return ws.addNetWordLink(ssid, password, WifiCipherType.WIFICIPHER_WPA);
        else if("WIFICIPHER_NOPASS".equals(type)) return  ws.addNetWordLink(ssid, password, WifiCipherType.WIFICIPHER_NOPASS);
        else if("WIFICIPHER_INVALID".equals(type)) return ws.addNetWordLink(ssid, password, WifiCipherType.WIFICIPHER_INVALID);
        else return false;
	}
	
	private boolean enterExistWifi(Intent intent){
		WifiConfiguration config = intent.getParcelableExtra("config");
        int networkId = intent.getIntExtra("networkId", -1);
        if(config!=null) return ws.linkNetWordLink(config.networkId);
        else if(networkId !=-1)return ws.linkNetWordLink(networkId);
        else return false;
	}
	
	private boolean deleteExistWifi(Intent intent) {
		WifiConfiguration config = intent.getParcelableExtra("config");
        int networkId = intent.getIntExtra("networkId", -1);
        if(config!=null) return ws.removeNetworkLink(config.networkId);
        else if(networkId !=-1)return ws.removeNetworkLink(networkId);
        else return false;		
	}
	
	class MyTimerTask extends TimerTask{  
		@Override  
	    public void run() {  
			ws.lockWifi();
			ws.startScan();
			WifiInfo wifiInfo2 = ws.getWifiInfo();
			scanLog.insert(wifiInfo2.getSSID());
			ArrayList<WifiConfiguration> wifiConfigList = new ArrayList<WifiConfiguration>(ws.getWifiConfigList());
			ArrayList<ScanResult> scanList = new ArrayList<ScanResult>(ws.getScanResultList());
//			scanRLog.insert(scanList);
			
			Bundle extras = new Bundle();
			extras.putParcelable("wifiInfo", wifiInfo);
			extras.putParcelableArrayList("wifiConfigList", wifiConfigList);
			extras.putParcelableArrayList("scanList", scanList);
			
			int i = 0;
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			WifiDurationDataTable dtable = new WifiDurationDataTable(new ProviderDataBase(WifiScanService.this));
			for(ScanResult scan:scanList){
				if(!dtable.checkTime(scan.SSID)) {
					nameValuePairs.add(new BasicNameValuePair("SSID"+(i++), scan.SSID));
					dtable.foundSSID(scan.SSID);
					Log.i("foundNewSSID",scan.SSID);
//					MyNotification.show(WifiScanService.this, "ÕÒµ½ÐÂwifi", scan.SSID);
					scanRLog.insert(scan.SSID);
				}
//				Log.i("foundSSID",scan.SSID);
			}
//			Log.i("foundNewSSID",System.currentTimeMillis()+"");
			if(nameValuePairs.size()!=0){
				nameValuePairs.add(new BasicNameValuePair("SSIDnum", i+""));
				GetPostThread net = new GetPostThread();
				net.PostAsy(URL, nameValuePairs, new ResultHandler() {
					
					@Override
					public void handleResult(String result) {
						Log.i("result","aaax"+result);
						WifiInfoDataTable table = new WifiInfoDataTable(new ProviderDataBase(WifiScanService.this));
						WifiDurationDataTable dtable = new WifiDurationDataTable(new ProviderDataBase(WifiScanService.this));
						try {
							JSONArray array = new JSONArray(result);
							for(int i=0;i<array.length();i++){
								JSONObject w = array.getJSONObject(i);
								table.insertInfo(w.getString("ssid"), w.getString("version"), w.getString("title"), w.getString("decribe"), w.getString("from"), w.getString("time"), w.getString("data"),w.getString("wifi_icon"));
								dtable.foundSSID(w.getString("ssid"));
								Log.i("updateSSID",w.getString("ssid"));
	//							MyNotification.show(context, title, messgae);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
			sendBroadcast(new Intent("com.wifibackground.wifiscan").putExtras(extras));
			if(	wifiInfo.getSSID() != null && !wifiInfo.getSSID().equals(wifiInfo2.getSSID()) ||
				wifiInfo.getSSID() == null && wifiInfo2.getSSID() != null ){
				changeLog.insert(wifiInfo.getSSID(),wifiInfo2.getSSID());

				sendBroadcast(new Intent("com.wifibackground.wifichange").putExtras(extras));
				wifiInfo = wifiInfo2;
			}
	    }
	} 
}
