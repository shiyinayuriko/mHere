package com.mHere.mclock.database;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.wifi.ScanResult;

import com.mHere.common.database.DbTable;

public class ClockGroupDataTable extends DbTable {
	private ClockDataBase dbHelper;
	public ClockGroupDataTable(ClockDataBase dbHelper) {
		super();
		addColumns("alarm_group", collumsType.TEXT);
		addColumns("alarm_SSID", collumsType.TEXT);
		setTableName("ssid_group");
		this.dbHelper = dbHelper;
	}
	
	public void bindGroup(String group,String ssid){
		ContentValues values = new ContentValues();  
		values.put("alarm_group", group);
		values.put("alarm_SSID", ssid);
		
		int ret = dbHelper.getWritableDatabase().update(getTableName(), values, "alarm_group=? AND alarm_SSID=?",  new String[]{group,ssid});
		if(ret == 0){
			dbHelper.getWritableDatabase().insert(getTableName(), "_ID", values);
		}
	}
	
	public Set<String> getGroup(String ssid){
		Cursor c = dbHelper.getWritableDatabase().query(getTableName(), null, "alarm_SSID=?", new String[]{ssid}, null, null, null);
		Set<String> ret = new HashSet<String>();
		while(c.moveToNext()){
			ret.add(c.getString(c.getColumnIndex("alarm_group")));
		}
		c.close();
		return ret;
	}
	
	public Set<String> getGroup(ArrayList<ScanResult> scanList){
		Set<String> ret = new HashSet<String>();
		for(ScanResult scan:scanList){
			String ssid = scan.SSID;
			Cursor c = dbHelper.getWritableDatabase().query(getTableName(), null, "alarm_SSID=?", new String[]{ssid}, null, null, null);
			while(c.moveToNext()){
				ret.add(c.getString(c.getColumnIndex("alarm_group")));
			}
			c.close();
		}
		return ret;
	}
	
	public Set<String> getGroups(){
		Cursor c = dbHelper.getWritableDatabase().query(getTableName(), null, null ,null, null, null, null);
		Set<String> ret = new HashSet<String>();
		while(c.moveToNext()){
			ret.add(c.getString(c.getColumnIndex("alarm_group")));
		}
		c.close();
		return ret;
	}
	
	public Set<String> getSSIDs(String group){
		Cursor c = dbHelper.getWritableDatabase().query(getTableName(), null,"alarm_group=?", new String[]{group}, null, null, null);
		Set<String> ret = new HashSet<String>();
		while(c.moveToNext()){
			ret.add(c.getString(c.getColumnIndex("alarm_SSID")));
		}
		c.close();
		return ret;
	}
}
