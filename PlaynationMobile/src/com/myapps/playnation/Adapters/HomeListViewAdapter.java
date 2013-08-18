package com.myapps.playnation.Adapters;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myapps.playnation.R;
import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Fragments.Tabs.Home.HomeEventsFragment;
import com.myapps.playnation.Fragments.Tabs.Home.HomeGamesFragment;
import com.myapps.playnation.Fragments.Tabs.Home.HomeGroupsFragment;
import com.myapps.playnation.Fragments.Tabs.Home.HomeSubscriptionFragment;
import com.myapps.playnation.Fragments.Tabs.Players.PlayerGamesFragment;
import com.myapps.playnation.Operations.HelperClass;

public class HomeListViewAdapter extends BaseAdapter {
	private ArrayList<Bundle> generalList;

	private static int TYPE_HEADER = 0;
	private static int TYPE_CHILD = 1;
	private LayoutInflater inflater;
	// Only used as mark which class is currently present.
	private Object currentFragment;

	public HomeListViewAdapter(Context context, ArrayList<Bundle> list,
			Object currentFragment) {
		super();
		this.generalList = list;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		this.currentFragment = currentFragment;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (generalList != null)
			return generalList.size();
		else
			return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub.
		if (generalList != null)
			return generalList.get(position);
		else
			return 0;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		if (position == 0)
			return TYPE_HEADER;
		else
			return TYPE_CHILD;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(final int position, View convertView,
			final ViewGroup viewGroup) {
		View view = convertView;
		TextView txEHeadline;
		TextView txELocation;
		TextView txEDate;
		TextView txEDuration;
		TextView txText;

		if (currentFragment instanceof HomeEventsFragment) {
			view = inflater.inflate(R.layout.fragment_home_event, viewGroup,
					false);
			txEHeadline = (TextView) view.findViewById(R.id.txEHeadline);
			txELocation = (TextView) view.findViewById(R.id.txELocation);
			txEDate = (TextView) view.findViewById(R.id.txEDate);
			txEDuration = (TextView) view.findViewById(R.id.txEDuration);
			txText = (TextView) view.findViewById(R.id.txText);

			ImageView img = (ImageView) view.findViewById(R.id.imgEvent);
			img.setImageResource(R.drawable.event);

			final Bundle mapEntry = generalList.get(position);
			if (mapEntry != null) {
				txEHeadline
						.setText("" + mapEntry.getString(Keys.EventHeadline));
				txELocation.setText(view.getResources().getString(
						R.string.Location)
						+ mapEntry.getString(Keys.EventLocation));
				txEDate.setText(view.getResources().getString(R.string.Date)
						+ mapEntry.getString(Keys.EventTime));
				txEDuration.setText(mapEntry.getString(Keys.EventDuration));
				txText.setText(Html.fromHtml(mapEntry
						.getString(Keys.EventDescription)));
			}
		} else if (currentFragment instanceof HomeGamesFragment
				|| currentFragment instanceof PlayerGamesFragment) {
			view = inflater.inflate(R.layout.fragment_home_msggame, viewGroup,
					false);

			txEHeadline = (TextView) view.findViewById(R.id.txEHeadline);
			txELocation = (TextView) view.findViewById(R.id.txELocation);
			txText = (TextView) view.findViewById(R.id.txText);

			ImageView img = (ImageView) view.findViewById(R.id.imgEvent);
			img.setImageResource(R.drawable.no_game_100x100);

			final Bundle mapEntry = generalList.get(position);
			if (mapEntry != null) {
				txEHeadline.setText("" + mapEntry.getString(Keys.GAMENAME));
				txELocation.setText(mapEntry.getString(Keys.GAMETYPE));

				txText.setText(Html.fromHtml(HelperClass
						.checkGameComments(mapEntry)));
			}
		} else if (currentFragment instanceof HomeGroupsFragment) {
			view = inflater.inflate(R.layout.fragment_home_group, viewGroup,
					false);

			txEHeadline = (TextView) view.findViewById(R.id.txEHeadline);
			txELocation = (TextView) view.findViewById(R.id.txELocation);
			txEDuration = (TextView) view.findViewById(R.id.txEDuration);
			txText = (TextView) view.findViewById(R.id.txText);

			ImageView img = (ImageView) view.findViewById(R.id.imgEvent);
			img.setImageResource(R.drawable.no_group_100x100);

			final Bundle mapEntry = generalList.get(position);
			if (mapEntry != null) {
				txEHeadline.setText("" + mapEntry.getString(Keys.GROUPNAME));
				txELocation.setText(mapEntry.getString(Keys.GAMENAME));
				txText.setText(mapEntry.getString(Keys.GROUPDESC));
				txEDuration.setText(mapEntry.getString(Keys.GroupMemberCount)
						+ view.getResources().getString(R.string.Members));
			}

		} else if (currentFragment instanceof HomeSubscriptionFragment) {
			view = inflater.inflate(R.layout.fragment_home_subscrition,
					viewGroup, false);

			txEHeadline = (TextView) view.findViewById(R.id.txEHeadline);
			txELocation = (TextView) view.findViewById(R.id.txELocation);
			txEDate = (TextView) view.findViewById(R.id.txEDate);

			ImageView img = (ImageView) view.findViewById(R.id.imgEvent);
			img.setImageResource(R.drawable.subscription);

			Bundle mapEntry = generalList.get(position);
			if (mapEntry != null) {
				txEHeadline.setText("" + mapEntry.getString(Keys.ItemName));
				txELocation.setText(mapEntry.getString(Keys.ItemType));
				txEDate.setText(mapEntry.getString(Keys.SubscriptionTime));
			}
		}
		return view;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
