package com.mHere.wifibackground.log;

import com.mHere.common.database.DatabaseHelper;
import com.mHere.common.database.DbTable;

import android.content.Context;

public class LogDataBase extends DatabaseHelper{
	private static String dbName = "logs.db";
	private static int version = 1;
	//注册数据表对象
	private static DbTable[] tableInfos = new DbTable[]{
		new WifiChangeLogDataTable(null),
		new WifiScanResultDataTable(null),
		new WifiScanLogDataTable(null)
	};
	
	//构造函数
	public LogDataBase(Context context) {
		super(context, dbName, version, tableInfos);
	}
}
