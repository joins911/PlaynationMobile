package com.myapps.playnation.Classes;

import java.util.ArrayList;

import android.os.Bundle;

public class ExpandbleParent {
	private String Title;
	private String Message;
	private String Date;
	private ArrayList<Bundle> ArrayChildren;
	private Bundle firstChild;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String mTitle) {
		this.Title = mTitle;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public ArrayList<Bundle> getArrayChildren() {
		return ArrayChildren;
	}

	public void setArrayChildren(ArrayList<Bundle> mArrayChildren) {
		this.ArrayChildren = mArrayChildren;
	}

	public Bundle getFirstChild() {
		return firstChild;
	}

	public void setFirstChild(Bundle e) {
		this.firstChild = e;
	}
}