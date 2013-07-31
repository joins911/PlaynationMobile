package com.myapps.playnation.Adapters;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.myapps.playnation.R;
import com.myapps.playnation.Classes.Keys;

public class FriendsListAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private ArrayList<HashMap<String, String>> generalList;

	public FriendsListAdapter(Context context,
			ArrayList<HashMap<String, String>> list) {
		this.generalList = list;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
	public long getItemId(int position) {
		// TODO Auto-generated method stub
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

		HashMap<String, String> mapEntry = generalList.get(position);
		if (mapEntry != null) {
			playerIcon.setImageResource(R.drawable.person);
			playerIcon.setContentDescription(mapEntry.get(Keys.PLAYERNICKNAME));
			txPlName.setText("" + mapEntry.get(Keys.FirstName) + " , "
					+ mapEntry.get(Keys.LastName));
			txPlNick.setText(view.getResources().getString(R.string.Nick)
					+ mapEntry.get(Keys.PLAYERNICKNAME));
			txPlCountry.setText(view.getResources().getString(R.string.Country)
					+ mapEntry.get(Keys.COUNTRY));
			String[] dates = mapEntry.get(Keys.Age).split("-");
			int year = Integer.parseInt(dates[0]);
			int currentYear = Calendar.getInstance().get(Calendar.YEAR);
			txPlAge.setText(view.getResources().getString(R.string.Age)
					+ (currentYear - year));
		}
		return view;
	}

}
