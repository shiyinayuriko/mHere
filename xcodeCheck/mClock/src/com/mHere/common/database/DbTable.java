package com.mHere.common.database;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于定义一张表的数据对象
 * @author yuriko
 *
 */
public class DbTable {
	/**
	 * 允许的列类型
	 * @author yuriko
	 *
	 */
	public enum collumsType{
		INTEGER,
		TEXT,
	}
	/**
	 * 表名
	 */
	private String TABLE_NAME;
	/**
	 * 列名
	 */
	private Map<String,String> COLUMNS = new HashMap<String, String>();
	
	public String getTableName() {
		return TABLE_NAME;
	}
	protected void setTableName(String tableName) {
		TABLE_NAME = tableName;
	}
	
	public Map<String, String> getColumns() {
		return new HashMap<String, String>(COLUMNS);
	}
	protected void addColumns(String key, collumsType value){
		COLUMNS.put(key, value.toString());
	}
}
