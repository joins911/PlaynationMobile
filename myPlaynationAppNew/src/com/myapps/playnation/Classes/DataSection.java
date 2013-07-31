package com.myapps.playnation.Classes;


public class DataSection implements NewsFeedItem {
	private String Key_Title;

	public String getKey_Title() {
		return Key_Title;
	}

	public void setKey_Title(String title) {
		this.Key_Title = title;
	}

	@Override
	public boolean isSection() {
		return true;
	}
}
