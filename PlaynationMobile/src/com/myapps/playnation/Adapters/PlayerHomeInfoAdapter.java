package com.myapps.playnation.Adapters;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.myapps.playnation.R;
import com.myapps.playnation.Classes.Keys;

public class PlayerHomeInfoAdapter extends BaseAdapter implements MyBaseAdapter {
	private LayoutInflater inflater;
	private ArrayList<Bundle> tempList;
	private int count;
	private boolean showMore = true;

	public PlayerHomeInfoAdapter(Context context, ArrayList<Bundle> list) {
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.tempList = list;
		count = 10;
	}

	@Override
	public int getCount() {
		if (tempList != null) {
			if (tempList.size() <= count) {
				return tempList.size();
			} else {
				return count;
			}
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int arg0) {
		return tempList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View view = arg1;
		// if (currentFragment instanceof HomeFriendsFragment
		// || currentFragment instanceof PlayerFriendsFragment) {
		view = inflater.inflate(R.layout.component_homeheader_layout, arg2,
				false);

		QuickContactBadge playerIcon = (QuickContactBadge) view
				.findViewById(R.id.quickContactBadge1);

		TextView txPlName = (TextView) view.findViewById(R.id.txPlName);
		TextView txPlNick = (TextView) view.findViewById(R.id.txPlNick);
		TextView txPlAge = (TextView) view.findViewById(R.id.txPlAge);
		TextView txPlCountry = (TextView) view.findViewById(R.id.txPlCountry);
		TextView txEdit = (TextView) view.findViewById(R.id.txtEdit);
		txEdit.setVisibility(View.GONE);
		Bundle mapEntry = null;
		if (tempList != null)
			mapEntry = tempList.get(arg0);

		if (mapEntry != null) {
			playerIcon.setImageResource(R.drawable.no_player_100x100);
			playerIcon.setContentDescription(mapEntry
					.getString(Keys.PLAYERNICKNAME));
			txPlName.setText("" + mapEntry.getString(Keys.FirstName) + " , "
					+ mapEntry.getString(Keys.LastName));
			txPlNick.setText(view.getResources().getString(R.string.Nick)
					+ mapEntry.getString(Keys.PLAYERNICKNAME));
			txPlCountry.setText(view.getResources().getString(R.string.Country)
					+ mapEntry.getString(Keys.COUNTRY));
			String[] dates = mapEntry.getString(Keys.Age).split("-");
			int year = Integer.parseInt(dates[0]);
			int currentYear = Calendar.getInstance().get(Calendar.YEAR);
			txPlAge.setText(view.getResources().getString(R.string.Age)
					+ (currentYear - year));
		}
		// }
		return view;
	}

	@Override
	public void showMore() {
		if (showMore)
			if (count + 5 <= tempList.size())
				count = count + 5;
			else {
				count = tempList.size();
				showMore = false;
			}
	}

	@Override
	public boolean canShowMore() {
		return showMore;
	}

	@Override
	public ArrayList<Bundle> getList() {
		// TODO Auto-generated method stub
		return null;
	}

}
