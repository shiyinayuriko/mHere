package com.mHere.mclock.clockManager;

import java.util.List;
import java.util.Set;

import android.content.Context;

import com.mHere.mclock.database.ClockDataBase;
import com.mHere.mclock.database.ClockDataTable;
import com.mHere.mclock.database.ClockGroupDataTable;

public class ClockManager {

	private Context context;
	public ClockManager(Context context){
		this.context = context;
	}
	
	public boolean setClock(Clocker cb){
		if(cb.getGroup() == null) return false;
		ClockDataTable cdt = new ClockDataTable(new ClockDataBase(context));
		cdt.setClock(cb);
		return true;
	}
	
	public List<Clocker> getClocks(){
		ClockDataTable cdt = new ClockDataTable(new ClockDataBase(context));
		return cdt.getClockers();
	}
	public Set<String> getGroups(){
		ClockGroupDataTable cgd = new ClockGroupDataTable(new ClockDataBase(context));
		return cgd.getGroups();
	}
	public Set<String> getSSIDs(String group){
		ClockGroupDataTable cgd = new ClockGroupDataTable(new ClockDataBase(context));
		return cgd.getSSIDs(group);
	}
	
	public boolean bindSSID2GRoup(String ssid,String group){
		if(ssid==null && group==null) return false;
		ClockGroupDataTable cg = new ClockGroupDataTable(new ClockDataBase(context));
		cg.bindGroup(group, ssid);
		return true;
	}
	

}
