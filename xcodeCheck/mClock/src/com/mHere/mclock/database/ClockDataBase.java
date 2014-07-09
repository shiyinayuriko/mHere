package com.mHere.mclock.database;

import android.content.Context;

import com.mHere.common.database.DatabaseHelper;
import com.mHere.common.database.DbTable;

public class ClockDataBase extends DatabaseHelper {
	private static String dbName = "clock.db";
	private static int version = 1;
	//注册数据表对象
	private static DbTable[] tableInfos = new DbTable[]{
		new ClockDataTable(null),
		new ClockGroupDataTable(null),
	};
	
	public ClockDataBase(Context context) {
		super(context, dbName, version, tableInfos);
		// TODO Auto-generated constructor stub
	}

}
