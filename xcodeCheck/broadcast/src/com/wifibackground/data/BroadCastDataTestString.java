package com.wifibackground.data;

import java.io.Serializable;

public class BroadCastDataTestString implements Serializable{
	private static final long serialVersionUID = 766663667563647037L;
	String s;
	public BroadCastDataTestString(String s){
		this.s = s;
	}
	public String getinfo() {
		return s;
	}
}
