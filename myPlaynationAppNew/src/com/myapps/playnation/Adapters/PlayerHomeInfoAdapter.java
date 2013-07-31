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

public class PlayerHomeInfoAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private ArrayList<HashMap<String, String>> tempList;

	public PlayerHomeInfoAdapter(Context context,
			ArrayList<HashMap<String, String>> list) {
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.tempList = list;
	}

	@Override
	public int getCount() {
		return tempList.size();
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

		HashMap<String, String> mapEntry = tempList.get(arg0);
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
		// }
		return view;
	}

}
