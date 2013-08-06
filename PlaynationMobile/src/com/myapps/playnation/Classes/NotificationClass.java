package com.myapps.playnation.Classes;

import java.text.SimpleDateFormat;

import com.myapps.playnation.Operations.HelperClass;


import android.annotation.SuppressLint;

public class NotificationClass {
	private int iD_NOTIFICATION;
	private String notificationType;
	private String notificationTime;
	private int iD_FROM;
	private String fromType;
	private String lastReadTime;
	private int iSRead;

	public int getiD_NOTIFICATION() {
		return iD_NOTIFICATION;
	}

	public void setiD_NOTIFICATION(int iD_NOTIFICATION) {
		this.iD_NOTIFICATION = iD_NOTIFICATION;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	@SuppressLint("SimpleDateFormat")
	public String getNotificationTime() {
		return HelperClass.convertTime(Integer.parseInt(notificationTime),
				new SimpleDateFormat("MMM dd,yy HH:mm"));
	}

	public void setNotificationTime(String notificationTime) {
		this.notificationTime = notificationTime;
	}

	public int getiD_FROM() {
		return iD_FROM;
	}

	public void setiD_FROM(int iD_FROM) {
		this.iD_FROM = iD_FROM;
	}

	public String getFromType() {
		return fromType;
	}

	public void setFromType(String fromType) {
		this.fromType = fromType;
	}

	@SuppressLint("SimpleDateFormat")
	public String getLastReadTime() {
		return HelperClass.convertTime(Integer.parseInt(lastReadTime),
				new SimpleDateFormat("MMM dd,yy HH:mm"));
	}

	public void setLastReadTime(String lastReadTime) {
		this.lastReadTime = lastReadTime;
	}

	public int getiSRead() {
		return iSRead;
	}

	public void setiSRead(int iSRead) {
		this.iSRead = iSRead;
	}
}
