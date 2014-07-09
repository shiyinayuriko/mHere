package com.mHere.common.database;

import java.util.HashMap;
import java.util.Map;

/**
 * ���ڶ���һ�ű�����ݶ���
 * @author yuriko
 *
 */
public class DbTable {
	/**
	 * �����������
	 * @author yuriko
	 *
	 */
	public enum collumsType{
		INTEGER,
		TEXT,
	}
	/**
	 * ����
	 */
	private String TABLE_NAME;
	/**
	 * ����
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
