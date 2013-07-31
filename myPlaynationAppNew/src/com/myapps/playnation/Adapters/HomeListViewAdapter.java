package com.myapps.playnation.Adapters;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
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
	private ArrayList<HashMap<String, String>> generalList;

	private static int TYPE_HEADER = 0;
	private static int TYPE_CHILD = 1;
	private LayoutInflater inflater;
	// Only used as mark which class is currently present.
	private Object currentFragment;

	public HomeListViewAdapter(Context context,
			ArrayList<HashMap<String, String>> list, Object currentFragment) {
		super();
		this.generalList = list;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		this.currentFragment = currentFragment;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return generalList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return generalList.get(position);
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

			final HashMap<String, String> mapEntry = generalList.get(position);
			if (mapEntry != null) {
				txEHeadline.setText("" + mapEntry.get(Keys.EventHeadline));
				txELocation.setText(view.getResources().getString(
						R.string.Location)
						+ mapEntry.get(Keys.EventLocation));
				txEDate.setText(view.getResources().getString(R.string.Date)
						+ mapEntry.get(Keys.EventTime));
				txEDuration.setText(mapEntry.get(Keys.EventDuration));
				txText.setText(Html.fromHtml(mapEntry
						.get(Keys.EventDescription)));
			}
		} else if (currentFragment instanceof HomeGamesFragment
				|| currentFragment instanceof PlayerGamesFragment) {
			view = inflater.inflate(R.layout.fragment_home_msggame, viewGroup,
					false);

			txEHeadline = (TextView) view.findViewById(R.id.txEHeadline);
			txELocation = (TextView) view.findViewById(R.id.txELocation);
			txText = (TextView) view.findViewById(R.id.txText);

			ImageView img = (ImageView) view.findViewById(R.id.imgEvent);
			img.setImageResource(R.drawable.game);

			final HashMap<String, String> mapEntry = generalList.get(position);
			if (mapEntry != null) {
				txEHeadline.setText("" + mapEntry.get(Keys.GAMENAME));
				txELocation.setText(mapEntry.get(Keys.GAMETYPE));

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
			img.setImageResource(R.drawable.event);

			final HashMap<String, String> mapEntry = generalList.get(position);
			if (mapEntry != null) {
				txEHeadline.setText("" + mapEntry.get(Keys.GROUPNAME));
				txELocation.setText(mapEntry.get(Keys.GAMENAME));
				txText.setText(mapEntry.get(Keys.GROUPDESC));
				txEDuration.setText(mapEntry.get(Keys.GroupMemberCount)
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

			HashMap<String, String> mapEntry = generalList.get(position);
			if (mapEntry != null) {
				txEHeadline.setText("" + mapEntry.get(Keys.ItemName));
				txELocation.setText(mapEntry.get(Keys.ItemType));
				txEDate.setText(mapEntry.get(Keys.SubscriptionTime));
			}
		}
		return view;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
