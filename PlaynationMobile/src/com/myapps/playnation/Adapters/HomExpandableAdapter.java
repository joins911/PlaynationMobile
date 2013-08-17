package com.myapps.playnation.Adapters;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myapps.playnation.R;
import com.myapps.playnation.Classes.ExpandbleParent;
import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Fragments.Tabs.Home.HomeMessagesFragment;
import com.myapps.playnation.Fragments.Tabs.Home.HomeWallFragment;
import com.myapps.playnation.Operations.DataConnector;
import com.myapps.playnation.Operations.HelperClass;

public class HomExpandableAdapter extends BaseExpandableListAdapter {
	private LayoutInflater inflater;
	private ArrayList<ExpandbleParent> mParent;
	private int lastParent;
	private ExpandableListView mExpandableList;

	private DataConnector con;
	private ArrayList<Bundle> array;
	@SuppressWarnings("unused")
	private Context context;
	// Only used as mark which class is currently present.
	private Object currentFragment;

	public HomExpandableAdapter() {
	}

	public HomExpandableAdapter(Context context,
			ArrayList<ExpandbleParent> parent, ExpandableListView exp,
			Object currentFragment) {
		mParent = parent;
		inflater = LayoutInflater.from(context);
		con = DataConnector.getInst(context);
		this.mExpandableList = exp;
		this.currentFragment = currentFragment;
		this.context = context;

	}

	// ---------------------------------------------------------------

	// ---------------------------------------------------------------

	@Override
	// counts the number of group/parent items so the list knows how many
	// times calls getGroupView() method
	public int getGroupCount() {
		return mParent.size();
	}

	@Override
	// counts the number of children items so the list knows how many times
	// calls getChildView() method
	public int getChildrenCount(int i) {
		if (mParent.get(i).getArrayChildren() != null)
			return mParent.get(i).getArrayChildren().size();
		else
			return 0;
	}

	@Override
	// gets the title of each parent/group
	public Object getGroup(int i) {
		return mParent.get(i).getTitle();
	}

	@Override
	// gets the name of each item
	public Object getChild(int i, int i1) {
		return mParent.get(i).getArrayChildren().get(i1);
	}

	@Override
	public long getGroupId(int i) {
		return i;
	}

	@Override
	public long getChildId(int i, int i1) {
		return i1;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	// in this method you must set the text to see the parent/group on the
	// list
	public View getGroupView(int groupPosition, boolean b, View view,
			ViewGroup viewGroup) {

		TextView txEHeadline;
		TextView txELocation;
		TextView txEDate;
		TextView txText;

		Bundle mapEntry = mParent.get(groupPosition).getFirstChild();
		if (currentFragment instanceof HomeWallFragment) {
			view = inflater.inflate(R.layout.fragment_home_wall, viewGroup,
					false);
			txEHeadline = (TextView) view.findViewById(R.id.txEHeadline);
			txText = (TextView) view.findViewById(R.id.txText);
			txEDate = (TextView) view.findViewById(R.id.txEDate);
			ImageView img = (ImageView) view.findViewById(R.id.imgEvent);
			img.setImageResource(R.drawable.event);

			txEHeadline.setText(""
					+ mapEntry.getString(Keys.WallPosterDisplayName));
			txText.setText(""
					+ Html.fromHtml(mapEntry.getString(Keys.WallMessage)));
			txEDate.setText("Date: "
					+ HelperClass.convertTime(Integer.parseInt(mapEntry
							.getString(Keys.WallPostingTime)), con.dataTemplate));
		} else if (currentFragment instanceof HomeMessagesFragment) {
			view = inflater.inflate(R.layout.fragment_home_msggame, viewGroup,
					false);
			ImageView img = (ImageView) view.findViewById(R.id.imgEvent);
			img.setImageResource(R.drawable.msgclose);
			txEHeadline = (TextView) view.findViewById(R.id.txEHeadline);
			txText = (TextView) view.findViewById(R.id.txText);
			txELocation = (TextView) view.findViewById(R.id.txELocation);

			txEHeadline.setText("" + mapEntry.getString(Keys.PLAYERNICKNAME));
			txELocation.setText(mapEntry.getString(Keys.MessageTime));
			txText.setText(mapEntry.getString(Keys.MessageText));
		}

		// return the entire view
		return view;
	}

	int index = 0;

	@SuppressLint("SimpleDateFormat")
	@Override
	// in this method you must set the text to see the children on the list
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View view, ViewGroup viewGroup) {
		view = null;

		// return the entire view

		TextView txEHeadline;
		TextView txELocation;
		TextView txEDate;
		TextView txText;

		if (!array.isEmpty()) {
			Bundle mapEntry = array.get(childPosition);
			if (mapEntry != null) {
				if (currentFragment instanceof HomeWallFragment) {
					view = inflater.inflate(R.layout.fragment_home_wall,
							viewGroup, false);
					RelativeLayout mainLayoutPlayer = (RelativeLayout) view
							.findViewById(R.id.mainLayoutPlayer);
					mainLayoutPlayer.setPadding(50, 0, 50, 0);

					txEHeadline = (TextView) view
							.findViewById(R.id.txEHeadline);
					txText = (TextView) view.findViewById(R.id.txText);
					txEDate = (TextView) view.findViewById(R.id.txEDate);
					ImageView img = (ImageView) view
							.findViewById(R.id.imgEvent);
					img.setImageResource(R.drawable.event);

					txEHeadline.setText(""
							+ mapEntry.getString(Keys.WallPosterDisplayName));
					txText.setText(""
							+ Html.fromHtml(mapEntry
									.getString(Keys.WallMessage)));
					txEDate.setText("Date: "
							+ mapEntry.getString(Keys.WallPostingTime));
				} else if (currentFragment instanceof HomeMessagesFragment) {
					view = inflater.inflate(R.layout.fragment_home_msggame,
							viewGroup, false);
					RelativeLayout mainLayoutPlayer = (RelativeLayout) view
							.findViewById(R.id.mainLayoutPlayer);
					mainLayoutPlayer.setPadding(50, 0, 50, 0);

					ImageView img = (ImageView) view
							.findViewById(R.id.imgEvent);
					img.setImageResource(R.drawable.msgopen);
					txEHeadline = (TextView) view
							.findViewById(R.id.txEHeadline);
					txText = (TextView) view.findViewById(R.id.txText);
					txELocation = (TextView) view
							.findViewById(R.id.txELocation);

					txEHeadline.setText(""
							+ mapEntry.getString(Keys.PLAYERNICKNAME));
					txELocation.setText(mapEntry.getString(Keys.MessageTime));

					txEHeadline.setText(""
							+ mapEntry.getString(Keys.PLAYERNICKNAME));
					txELocation.setText(mapEntry.getString(Keys.MessageTime));
					txText.setText(Html.fromHtml(mapEntry
							.getString(Keys.MessageText)));
				}
			}
		}
		return view;
	}

	@Override
	public boolean isChildSelectable(int i, int i1) {
		return true;
	}

	// @Override
	// public void registerDataSetObserver(DataSetObserver observer) {
	// /* used to make the notifyDataSetChanged() method work */
	// super.registerDataSetObserver(observer);
	// }

	@Override
	public void onGroupExpanded(int groupPosition) {
		super.onGroupExpanded(groupPosition);
		mExpandableList.expandGroup(groupPosition);
		if (groupPosition != lastParent) {
			mExpandableList.collapseGroup(lastParent);
		}
		array = new ArrayList<Bundle>();
		Bundle mapEntry = mParent.get(groupPosition).getFirstChild();
		if (lastParent == groupPosition
				|| currentFragment instanceof HomeWallFragment) {
			con.queryPlayerWallReplices(mapEntry.getString(Keys.ID_WALLITEM),
					Keys.TEMPLAYERID);
			array = con.getTable(Keys.HomeWallRepliesTable,
					mapEntry.getString(Keys.ID_WALLITEM));
			mParent.get(groupPosition).setArrayChildren(array);
		} else if (currentFragment instanceof HomeMessagesFragment) {
			con.queryPlayerMSGReplices(
					mapEntry.getString(Keys.MessageID_CONVERSATION),
					Keys.TEMPLAYERID);
			array = con.getTable(Keys.HomeMsgRepliesTable,
					mapEntry.getString(Keys.MessageID_CONVERSATION));
			mParent.get(groupPosition).setArrayChildren(array);
		}

		lastParent = groupPosition;
		notifyDataSetChanged();
	}

	@Override
	public void onGroupCollapsed(int groupPosition) {
		super.onGroupCollapsed(groupPosition);
		mExpandableList.collapseGroup(groupPosition);

	}
}
