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

public class FriendsListAdapter extends BaseAdapter implements MyBaseAdapter {
	private LayoutInflater inflater;
	private ArrayList<Bundle> generalList;
	private int count;
	private boolean showMore = true;

	public FriendsListAdapter(Context context, ArrayList<Bundle> list) {
		this.generalList = list;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		count = 10;
	}

	@Override
	public int getCount() {
		if (generalList.size() >= count)
			return count;
		else
			return generalList.size();
	}

	@Override
	public Object getItem(int position) {
		return generalList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		view = inflater.inflate(R.layout.component_homeheader_layout, parent,
				false);

		// TextView txlabel = (TextView)
		// view.findViewById(R.id.txWhereLabel);
		// txlabel.setVisibility(View.GONE);

		QuickContactBadge playerIcon = (QuickContactBadge) view
				.findViewById(R.id.quickContactBadge1);

		TextView txPlName = (TextView) view.findViewById(R.id.txPlName);
		TextView txPlNick = (TextView) view.findViewById(R.id.txPlNick);
		TextView txPlAge = (TextView) view.findViewById(R.id.txPlAge);
		TextView txPlCountry = (TextView) view.findViewById(R.id.txPlCountry);
		TextView txEdit = (TextView) view.findViewById(R.id.txtEdit);
		txEdit.setVisibility(View.GONE);

		Bundle mapEntry = generalList.get(position);
		if (mapEntry != null) {
			playerIcon.setImageResource(R.drawable.person);
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
		return view;
	}

	@Override
	public void showMore() {
		if (showMore)
			if (count + 5 <= generalList.size())
				count = count + 5;
			else {
				count = generalList.size();
				showMore = false;
			}

	}

	@Override
	public boolean canShowMore() {
		return showMore;
	}

	@Override
	public ArrayList<Bundle> getList() {
		return null;
	}

}
