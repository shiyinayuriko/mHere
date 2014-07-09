package com.mHere.wifibackground.log;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.util.Log;

import com.mHere.common.database.DbTable;

public class WifiScanLogDataTable extends DbTable{
	
	private LogDataBase dbHelper;
	public WifiScanLogDataTable(LogDataBase dbHelper) {
		super();
		addColumns("time", collumsType.TEXT);
		addColumns("wifi", collumsType.TEXT);
		setTableName("Scan");
		this.dbHelper = dbHelper;
	}
	
	@SuppressLint("SimpleDateFormat")
	public void insert(String wifi){
		SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd hh:mm:ss");     
		String   date   =   sDateFormat.format(new Date());
		ContentValues values = new ContentValues();  
		values.put("time", date);
		values.put("wifi", wifi);
		dbHelper.getWritableDatabase().insert(getTableName(), "time", values);
		Log.i("scan", date);
	}
}
