package com.myapps.playnation.Classes;

public class CommentInfo {
	private String userName = "";
	private String text = "";
	private String imageUrl = "";
	private CharSequence time;

	public CommentInfo(String userName, String text, String imageUrl,
			String time) {
		this.userName = userName;
		this.text = text;
		this.time = time;
		this.imageUrl = imageUrl;
	}

	public String getName() {
		return userName;
	}

	public void setName(String userName) {
		this.userName = userName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public CharSequence getTime() {
		return time;
	}

	public void setTime(CharSequence time) {
		this.time = time;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
