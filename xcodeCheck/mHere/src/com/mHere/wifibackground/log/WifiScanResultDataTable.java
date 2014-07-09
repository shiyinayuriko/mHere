package com.mHere.wifibackground.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.util.Log;

import com.mHere.common.database.DbTable;

@SuppressLint("SimpleDateFormat")
public class WifiScanResultDataTable extends DbTable{
	
	private LogDataBase dbHelper;
	public WifiScanResultDataTable(LogDataBase dbHelper) {
		super();
		addColumns("time", collumsType.TEXT);
		addColumns("SSID", collumsType.TEXT);
		setTableName("wifiScanResult");
		this.dbHelper = dbHelper;
	}
	
	public void insert(String ssid){
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");     
		String   date   =   sDateFormat.format(new Date());
		ContentValues values = new ContentValues();  
		values.put("time", date);
		values.put("SSID", ssid);
		dbHelper.getWritableDatabase().insert(getTableName(), "time", values);
		Log.i("change", date);
	}
}
