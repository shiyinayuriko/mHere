package com.mHere.mclock.notification;

import com.mHere.mclock.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

public class MyNotification {
	public static void show(Context context,String title,String messgae){
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(ns);
		
		@SuppressWarnings("deprecation")
		Notification notification = new Notification.Builder(context)
        .setContentTitle(title)
        .setContentText(messgae)
        .setSmallIcon(R.drawable.ic_launcher)
        .setAutoCancel(true)
        .getNotification();
		
		notification.defaults |=Notification.DEFAULT_SOUND;
		notification.flags |= Notification.FLAG_INSISTENT;
		notification.defaults |= Notification.DEFAULT_VIBRATE;
//		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		mNotificationManager.notify(0,notification);
	}
	
	public static void show(Context context,String title,String messgae,boolean isNoisy){
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(ns);
		
		@SuppressWarnings("deprecation")
		Notification notification = new Notification.Builder(context)
        .setContentTitle(title)
        .setContentText(messgae)
        .setSmallIcon(R.drawable.ic_launcher)
        .setAutoCancel(true)
        .getNotification();
		if(isNoisy){
			notification.defaults |=Notification.DEFAULT_SOUND;
			notification.flags |= Notification.FLAG_INSISTENT;
			notification.defaults |= Notification.DEFAULT_VIBRATE;
		}
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		mNotificationManager.notify(0,notification);
	}
	
}
