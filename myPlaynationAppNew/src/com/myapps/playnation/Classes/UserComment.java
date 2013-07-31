package com.myapps.playnation.Classes;

import java.util.ArrayList;

public class UserComment {
	private CommentInfo comment;
	private ArrayList<CommentInfo> replyList = new ArrayList<CommentInfo>();
	
	public UserComment(CommentInfo comment , ArrayList<CommentInfo> replyList)
	{
		this.comment = comment;
		this.replyList = replyList;
	}

	public CommentInfo getComment() {
		return comment;
	}

	public void setComment(CommentInfo comment) {
		this.comment = comment;
	}

	public ArrayList<CommentInfo> getReplyList() {
		return replyList;
	}

	public void setProductList(ArrayList<CommentInfo> replyList) {
		this.replyList = replyList;
	}
}
