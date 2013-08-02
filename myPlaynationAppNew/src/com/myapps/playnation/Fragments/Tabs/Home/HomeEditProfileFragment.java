package com.myapps.playnation.Fragments.Tabs.Home;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.myapps.playnation.R;
import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Operations.DataConnector;
import com.myapps.playnation.main.ISectionAdapter;

public class HomeEditProfileFragment extends Fragment {
	ISectionAdapter mCallback;
	private View mView;
	private DataConnector con;

	public void initPlayer() {
		con = DataConnector.getInst(getActivity());

		EditText editFirst = (EditText) mView
				.findViewById(R.id.txtChiledItemFirstName);

		EditText editLast = (EditText) mView
				.findViewById(R.id.txtChiledItemLastName);

		EditText editDisp = (EditText) mView
				.findViewById(R.id.txtChiledItemDisplayName);

		EditText editCity = (EditText) mView
				.findViewById(R.id.txtChiledItemCity);

		EditText editCountry = (EditText) mView
				.findViewById(R.id.txtChiledItemCountry);

		EditText editEmail = (EditText) mView
				.findViewById(R.id.txtChiledItemEmail);

		Bundle map = con.getTable(Keys.PlayerTable, "").get(0);
		// Bundle args = getArguments();
		editFirst.setText(map.getString(Keys.FirstName));
		editLast.setText(map.getString(Keys.LastName));
		editDisp.setText(map.getString(Keys.PLAYERNICKNAME));
		editCity.setText(map.getString(Keys.CITY));
		editCountry.setText(map.getString(Keys.COUNTRY));
		editEmail.setText(map.getString(Keys.Email));
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_home_editprofile, container,
				false);
		initPlayer();
		return mView;
	}

	@Override
	public void onDestroy() {
		mView = null;
		super.onDestroy();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception
		try {
			mCallback = (ISectionAdapter) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnHomeEditListener");
		}
	}
}
