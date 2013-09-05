package com.myapps.playnation.Adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myapps.playnation.R;
import com.myapps.playnation.Classes.CommentInfo;
import com.myapps.playnation.Classes.UserComment;
import com.myapps.playnation.Operations.LoadImage;

/**
 * 
 * @author viperime
 * @version 1.0; Adapter for the Comments Expandable List which first resided
 *          under the game Desc, but might be change to be useld for comments
 *          everywhere in the app
 */
public class CommExpListAdapter extends BaseExpandableListAdapter {

	private Context context;
	private ArrayList<UserComment> commentList;

	public CommExpListAdapter(Context context,
			ArrayList<UserComment> commentList) {
		this.context = context;
		this.commentList = commentList;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		ArrayList<CommentInfo> replyList = commentList.get(groupPosition)
				.getReplyList();
		return replyList.get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View view, ViewGroup parent) {

		CommentInfo replyInfo = (CommentInfo) getChild(groupPosition,
				childPosition);
		if (view == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = infalInflater.inflate(
					R.layout.component_comment_eitem_layout, null);
		}

		TextView userName = (TextView) view
				.findViewById(R.id.gamesCI_replyUsername_TView);
		userName.setText(replyInfo.getName().trim());
		TextView commentText = (TextView) view
				.findViewById(R.id.gamesCI_replyText_TView);
		commentText.setText(replyInfo.getText().trim());
		TextView timeTV = (TextView) view
				.findViewById(R.id.gamesCI_replyTime_TView);
		timeTV.setText(replyInfo.getTime());

		return view;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return commentList.get(groupPosition).getReplyList().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return commentList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return commentList.size();

	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isLastChild, View view,
			ViewGroup parent) {

		UserComment userComment = (UserComment) getGroup(groupPosition);
		if (view == null) {
			LayoutInflater inf = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inf.inflate(R.layout.component_comment_elist_layout, null);
		}
		if (userComment != null) {
			TextView userName = (TextView) view
					.findViewById(R.id.gamesCL_commentUsername_TView);
			userName.setText(userComment.getComment().getName().trim());
			TextView commentText = (TextView) view
					.findViewById(R.id.gamesCL_commentText_TView);
			commentText.setText(userComment.getComment().getText().trim());
			TextView timeTV = (TextView) view
					.findViewById(R.id.gamesCL_commentTime_TView);
			timeTV.setText(userComment.getComment().getTime());
			ImageView imageTV = (ImageView) view
					.findViewById(R.id.gamesCL_commentImage);
			String imageUrl = userComment.getComment().getImageUrl();

			new LoadImage("", "", "", imageUrl, imageTV, "wall_photos")
					.execute();
		}
		return view;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}