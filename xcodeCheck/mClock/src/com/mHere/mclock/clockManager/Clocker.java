package com.mHere.mclock.clockManager;

public class Clocker{
	public static final int TYPE_ONCE = 0x0;
	public static final int TYPE_EVERYDAY = 0x1;
	public static final int TYPE_EVERYTIME = 0x2;
	
	public static final int CONDITION_IN = 0x0;
	public static final int CONDITION_OUT = 0x1;
	
	public static final int VOLUME_OFF= 0;
	public static final int VOLUME_MAX= 100;
	public static final int VOLUME_NO_CHANGE= -1;
	
	public static final int ISALARM_ON= 0x0;
	public static final int ISALARM_OFF = 0x1;
	
	public static final int STATE_ON = 0x0;
	public static final int STATE_OFF = 0x1;	
	
	public static final int SWITCH_ON = 0x0;
	public static final int SWITCH_OFF = 0x1;

	private int _ID = -1;
	private int type = TYPE_ONCE;
	private String group = null;
	private int condition = CONDITION_IN;
	private int isAlarm = ISALARM_OFF;
	private String eventContent = "";
	private int eventVolume = VOLUME_OFF;//max:100%
	private int state = STATE_ON;
	private int switcher = SWITCH_ON;
	
	public Clocker(int _ID){
		this._ID = _ID;
	}
	public Clocker(){
	}
	
	public Clocker setAlarmType(int type){
		if(type !=TYPE_ONCE && type !=TYPE_EVERYDAY && type !=TYPE_EVERYTIME) this.type = TYPE_ONCE;
		else this.type = type;
		return this;
	}
	public Clocker setGroup(String group) {
		this.group = group;
		return this;
	}
	public Clocker setCondition(int condition) {
		if(condition !=CONDITION_IN && condition !=CONDITION_OUT) this.condition = CONDITION_IN;
		else this.condition = condition;
		return this;
	}
	
	public Clocker setEventContent(String eventContent) {
		this.eventContent = eventContent;
		return this;
	}
	
	public Clocker setEventVolume(int eventVolume) {
		int volume = eventVolume;
		if(volume>100) volume=100;
		if(volume<-1) volume=0;
		this.eventVolume = volume;
		return this;
	}

	public Clocker setIsAlarm(int isAlarm) {
		if(isAlarm !=ISALARM_ON && isAlarm !=ISALARM_OFF) this.isAlarm = ISALARM_OFF;
		this.isAlarm = isAlarm;
		return this;
	}
	
	public Clocker setState(int state) {
		if(state !=STATE_ON && state !=STATE_OFF) this.state = STATE_ON;
		this.state = state;
		return this;
	}
	public Clocker setSwitcher(int switcher) {
		if(switcher !=SWITCH_ON && switcher !=SWITCH_OFF) this.switcher = SWITCH_ON;
		this.switcher = switcher;
		return this;
	}

	public int getAlarmType() {
		return type;
	}
	public String getGroup() {
		return group;
	}
	public int getCondition() {
		return condition;
	}
	public int getIsAlarm() {
		return isAlarm;
	}
	public String getEventContent() {
		return eventContent;
	}

	public int getEventVolume() {
		return eventVolume;
	}
	public int getState() {
		return state;
	}
	public int getID() {
		return _ID;
	}
	public int getSwitcher() {
		return switcher;
	}
}