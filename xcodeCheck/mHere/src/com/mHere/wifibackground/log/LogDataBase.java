package com.mHere.wifibackground.log;

import com.mHere.common.database.DatabaseHelper;
import com.mHere.common.database.DbTable;

import android.content.Context;

public class LogDataBase extends DatabaseHelper{
	private static String dbName = "logs.db";
	private static int version = 1;
	//ע�����ݱ����
	private static DbTable[] tableInfos = new DbTable[]{
		new WifiChangeLogDataTable(null),
		new WifiScanResultDataTable(null),
		new WifiScanLogDataTable(null)
	};
	
	//���캯��
	public LogDataBase(Context context) {
		super(context, dbName, version, tableInfos);
	}
}
