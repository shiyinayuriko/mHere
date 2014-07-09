package com.mHere.mclock.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;

import com.mHere.common.database.DbTable;
import com.mHere.mclock.clockManager.Clocker;

public class ClockDataTable extends DbTable {

	private ClockDataBase dbHelper;
	public ClockDataTable(ClockDataBase dbHelper) {
		super();
		addColumns("alarm_condition", collumsType.INTEGER);
		addColumns("alarm_group", collumsType.TEXT);
		addColumns("alarm_type", collumsType.INTEGER);
		addColumns("eventContent", collumsType.TEXT);
		addColumns("isAlarm", collumsType.TEXT);
		addColumns("eventVolume", collumsType.INTEGER);
		addColumns("alarm_state", collumsType.INTEGER);
		addColumns("alarm_switcher", collumsType.INTEGER);
		setTableName("clock");
		this.dbHelper = dbHelper;
	}
	
	public void setClock(Clocker cb){
		ContentValues values = new ContentValues();  
		values.put("alarm_condition", cb.getCondition());
		values.put("alarm_group",  cb.getGroup());
		values.put("alarm_type",  cb.getAlarmType());
		values.put("eventContent",  cb.getEventContent());
		values.put("isAlarm",  cb.getIsAlarm());
		values.put("eventVolume",  cb.getEventVolume());
		values.put("alarm_state", cb.getState());
		values.put("alarm_switcher", cb.getSwitcher());
		int ret = 0;
		if(cb.getID()!=-1){
			ret = dbHelper.getWritableDatabase().update(getTableName(),values, "_ID =?",new String[]{cb.getID()+""});
		}
		if(ret == 0){
			dbHelper.getWritableDatabase().insert(getTableName(), "_ID", values);
		}
	}
	
	public List<Clocker> getClockers(){
		List<Clocker> rets = new ArrayList<Clocker>();
		Cursor c = dbHelper.getWritableDatabase().query(getTableName(), null, null, null, null, null, null);
		while(c.moveToNext()){
			rets.add(
				new Clocker(c.getInt(c.getColumnIndex("_ID")))
					.setCondition(c.getInt(c.getColumnIndex("alarm_condition")))
					.setGroup(c.getString(c.getColumnIndex("alarm_group")))
					.setIsAlarm(c.getInt(c.getColumnIndex("isAlarm")))
					.setAlarmType(c.getInt(c.getColumnIndex("alarm_type")))
					.setEventContent(c.getString(c.getColumnIndex("eventContent")))
					.setEventVolume(c.getInt(c.getColumnIndex("eventVolume")))
					.setState(c.getInt(c.getColumnIndex("alarm_state")))
					.setSwitcher(c.getInt(c.getColumnIndex("alarm_switcher")))	
					);
		}
		c.close();
		return rets;
	}
	
	public List<Clocker> getClockers(String group){
		List<Clocker> rets = new ArrayList<Clocker>();
		Cursor c = dbHelper.getWritableDatabase().query(getTableName(), null, "alarm_group =?", new String[]{group}, null, null, null);
		while(c.moveToNext()){
			rets.add(
				new Clocker(c.getInt(c.getColumnIndex("_ID")))
					.setCondition(c.getInt(c.getColumnIndex("alarm_condition")))
					.setGroup(c.getString(c.getColumnIndex("alarm_group")))
					.setIsAlarm(c.getInt(c.getColumnIndex("isAlarm")))
					.setAlarmType(c.getInt(c.getColumnIndex("alarm_type")))
					.setEventContent(c.getString(c.getColumnIndex("eventContent")))
					.setEventVolume(c.getInt(c.getColumnIndex("eventVolume")))
					.setState(c.getInt(c.getColumnIndex("alarm_state")))
					);
		}
		c.close();
		return rets;
	}
}
