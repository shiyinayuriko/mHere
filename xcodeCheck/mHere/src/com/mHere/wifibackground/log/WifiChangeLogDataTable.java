package com.mHere.wifibackground.log;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.util.Log;

import com.mHere.common.database.DbTable;

@SuppressLint("SimpleDateFormat")
public class WifiChangeLogDataTable extends DbTable{
	
	private LogDataBase dbHelper;
	public WifiChangeLogDataTable(LogDataBase dbHelper) {
		super();
		addColumns("time", collumsType.TEXT);
		addColumns("fromSSID", collumsType.TEXT);
		addColumns("toSSID", collumsType.TEXT);
		setTableName("wifiChanges");
		this.dbHelper = dbHelper;
	}
	
	public void insert(String from,String to){
		SimpleDateFormat sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm:ss");     
		String   date   =   sDateFormat.format(new Date());
		ContentValues values = new ContentValues();  
		values.put("time", date);
		values.put("fromSSID", from);
		values.put("toSSID", to);
		dbHelper.getWritableDatabase().insert(getTableName(), "time", values);
		Log.i("change", date);
	}
}
