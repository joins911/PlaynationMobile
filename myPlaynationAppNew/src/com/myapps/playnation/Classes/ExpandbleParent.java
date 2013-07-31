package com.myapps.playnation.Classes;

import java.util.ArrayList;
import java.util.HashMap;

public class ExpandbleParent {
	private String Title;
	private String Message;
	private String Date;
	private ArrayList<HashMap<String, String>> ArrayChildren;
	private HashMap<String, String> firstChild;

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

	public ArrayList<HashMap<String, String>> getArrayChildren() {
		return ArrayChildren;
	}

	public void setArrayChildren(
			ArrayList<HashMap<String, String>> mArrayChildren) {
		this.ArrayChildren = mArrayChildren;
	}

	public HashMap<String, String> getFirstChild() {
		return firstChild;
	}

	public void setFirstChild(HashMap<String, String> e) {
		this.firstChild = e;
	}
}