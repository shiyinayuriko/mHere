package com.mHere.common.database;

import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * ʵ�ֱ����������ݿ�ӿ�
 * @author yuriko
 * @version 0.01
 */
public abstract class DatabaseHelper extends SQLiteOpenHelper{
	private DbTable[] tableInfos;
	
	protected DatabaseHelper(Context context, String name,	int version,DbTable[] tableInfos) {
		super(context, name, null, version);
		this.tableInfos = tableInfos;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		for(DbTable tableInfo:tableInfos){
			String sql="CREATE TABLE "+tableInfo.getTableName()+" (";
			
			sql+= "_ID INTEGER PRIMARY KEY";
			Map<String, String> columns = tableInfo.getColumns();
			for(String key:columns.keySet()){
				String value = columns.get(key);
				sql+= (","+key + " "+value);
			}
			
			sql+=" );";
			db.execSQL(sql);
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		for(DbTable tableInfo:tableInfos){
	        db.execSQL("DROP TABLE IF EXISTS " + tableInfo.getTableName());  
		}
        onCreate(db);
	}

	
}
