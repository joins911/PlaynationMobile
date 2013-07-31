package com.myapps.playnation.Fragments.Tabs.Players;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.myapps.playnation.R;
import com.myapps.playnation.Classes.Keys;

public class PlayerInfoFragment extends Fragment {
	private View view;

	public void initInfo() {
		EditText editFirst = (EditText) view
				.findViewById(R.id.txtChiledItemFirstName);
		editFirst.setEnabled(false);
		EditText editLast = (EditText) view
				.findViewById(R.id.txtChiledItemLastName);
		editLast.setEnabled(false);
		EditText editDisp = (EditText) view
				.findViewById(R.id.txtChiledItemDisplayName);
		editDisp.setEnabled(false);
		EditText editCity = (EditText) view
				.findViewById(R.id.txtChiledItemCity);
		editCity.setEnabled(false);
		EditText editCountry = (EditText) view
				.findViewById(R.id.txtChiledItemCountry);
		editCountry.setEnabled(false);
		EditText editEmail = (EditText) view
				.findViewById(R.id.txtChiledItemEmail);
		editEmail.setVisibility(View.GONE);
		TextView lblEmail = (TextView) view
				.findViewById(R.id.list_item_text_childEmail);
		lblEmail.setVisibility(View.GONE);

		Bundle args = getArguments();
		editFirst.setText(args.getString(Keys.FirstName));
		editLast.setText(args.getString(Keys.LastName));
		editDisp.setText(args.getString(Keys.PLAYERNICKNAME));
		editCity.setText(args.getString(Keys.CITY));
		editCountry.setText(args.getString(Keys.COUNTRY));
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_home_editprofile, container,
				false);
		initInfo();
		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
