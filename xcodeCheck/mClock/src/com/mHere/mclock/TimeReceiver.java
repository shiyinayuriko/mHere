package com.mHere.mclock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mHere.mclock.clockManager.Clocker;
import com.mHere.mclock.database.ClockDataBase;
import com.mHere.mclock.database.ClockDataTable;
import com.mHere.mclock.database.ClockGroupDataTable;
import com.mHere.mclock.notification.MyNotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.util.Log;

public class TimeReceiver extends BroadcastReceiver {
	private static Map<String, Long> groupLocation = null;
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if(intent.getAction().equals("com.wifibackground.wifiscan")){
			Log.i("Receiver",intent.getAction());
			ArrayList<ScanResult> scanList = intent.getParcelableArrayListExtra("scanList");
			ClockGroupDataTable cg = new ClockGroupDataTable(new ClockDataBase(context));
			Set<String> groups = cg.getGroup(scanList);
			
			if(groupLocation == null){
				groupLocation = new HashMap<String, Long>();
				for(String group:groups) groupLocation.put(group, System.currentTimeMillis()+1000*60*5*0);
			}else{
				for(String group:groupLocation.keySet()){
					if(!groups.contains(group) && groupLocation.get(group) < System.currentTimeMillis()){
						leaveGroup(context, group);
						groupLocation.remove(group);
					}
				}
				
				for(String group:groups){
					if(groupLocation.get(group)==null) enterGroup(context, group);
					groupLocation.put(group, System.currentTimeMillis()+1000*60*5*0);
				}
			}
		}
	}
	
	
	private void leaveGroup(Context context, String group) {
		// TODO Auto-generated method stub
		ClockDataTable cdt = new ClockDataTable(new ClockDataBase(context));
		List<Clocker> clocks = cdt.getClockers(group);
		for(Clocker clock :clocks){
			if(clock.getCondition() != Clocker.CONDITION_OUT 
					|| clock.getState() == Clocker.STATE_OFF
					|| clock.getSwitcher() == Clocker.SWITCH_OFF) continue;
//			if(clock.getEventVolume() == -1)
			MyNotification.show(context, "Àë¿ª"+group, clock.getEventContent(),clock.getIsAlarm() == Clocker.ISALARM_ON);
			switch(clock.getAlarmType()){
				case Clocker.TYPE_ONCE:clock.setState(Clocker.SWITCH_OFF);cdt.setClock(clock);break;
				case Clocker.TYPE_EVERYDAY:clock.setState(Clocker.SWITCH_OFF);cdt.setClock(clock);break;
			}
		}
	}


	private void enterGroup(Context context, String group) {
		ClockDataTable cdt = new ClockDataTable(new ClockDataBase(context));
		List<Clocker> clocks = cdt.getClockers(group);
		for(Clocker clock :clocks){
			if(clock.getCondition() != Clocker.CONDITION_IN 
					|| clock.getState() == Clocker.STATE_OFF
					|| clock.getSwitcher() == Clocker.SWITCH_OFF) continue;
//			if(clock.getEventVolume() == -1)
			MyNotification.show(context, "½øÈë"+group, clock.getEventContent(),clock.getIsAlarm() == Clocker.ISALARM_ON);
			switch(clock.getAlarmType()){
				case Clocker.TYPE_ONCE:clock.setState(Clocker.SWITCH_OFF);cdt.setClock(clock);break;
				case Clocker.TYPE_EVERYDAY:clock.setState(Clocker.SWITCH_OFF);cdt.setClock(clock);break;
			}
		}	
	}
}
