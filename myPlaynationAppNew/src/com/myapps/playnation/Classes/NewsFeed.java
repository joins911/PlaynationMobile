package com.myapps.playnation.Classes;

import java.util.Calendar;
import java.util.Date;

public class NewsFeed implements NewsFeedItem {
	private int Key_NewsFeedID;
	private int Key_NewsImage;
	private String Key_NewsTitle;
	private String Key_NewsIntroText;
	private String Key_NewsText;
	private Calendar Key_NewsDate;
	private String Key_Author;

	public int getKey_NewsFeedID() {
		return Key_NewsFeedID;
	}

	public void setKey_NewsFeedID(int key_NewsFeedID) {
		Key_NewsFeedID = key_NewsFeedID;
	}

	public int getKey_NewsImage() {
		return Key_NewsImage;
	}

	public void setKey_NewsImage(int newsImage) {
		this.Key_NewsImage = newsImage;
	}

	public String getKey_NewsTitle() {
		return Key_NewsTitle;
	}

	public void setKey_NewsTitle(String newsTitle) {
		this.Key_NewsTitle = newsTitle;
	}

	public String getKey_NewsIntroText() {
		return Key_NewsIntroText;
	}

	public void setKey_NewsIntroText(String key_NewsIntroText) {
		Key_NewsIntroText = key_NewsIntroText;
	}

	public String getKey_NewsText() {
		return Key_NewsText;
	}

	public void setKey_NewsText(String newsText) {
		this.Key_NewsText = newsText;
	}

	public Calendar getKey_NewsDate() {
		return Key_NewsDate;
	}

	public void setKey_NewsDate(Calendar newsDate) {
		this.Key_NewsDate = newsDate;
	}

	public Calendar convertTime(int fetchDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date((long) fetchDate * 1000));
		return cal;
	}

	@Override
	public boolean isSection() {
		return false;
	}

	public String getKey_Author() {
		return Key_Author;
	}

	public void setKey_Author(String key_Author) {
		String[] arr = key_Author.split(" ");
		if (arr[0].equals(arr[1]))
			Key_Author = arr[0];
		else
			Key_Author = key_Author;
	}
}
