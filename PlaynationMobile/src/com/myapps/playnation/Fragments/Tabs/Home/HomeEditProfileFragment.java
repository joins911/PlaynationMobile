package com.myapps.playnation.Fragments.Tabs.Home;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.myapps.playnation.R;
import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Operations.Configurations;
import com.myapps.playnation.Operations.DataConnector;
import com.myapps.playnation.Operations.HelperClass;
import com.myapps.playnation.main.ISectionAdapter;

public class HomeEditProfileFragment extends Fragment {
	ISectionAdapter mCallback;
	private View mView;
	private DataConnector con;
	EditText editFirst;
	EditText editLast;
	EditText editDisp;
	EditText editCity;
	EditText editCountry;
	EditText editEmail;
	Bundle map;

	public void initPlayer() {
		con = DataConnector.getInst(getActivity());

		editFirst = (EditText) mView.findViewById(R.id.txtChiledItemFirstName);

		editLast = (EditText) mView.findViewById(R.id.txtChiledItemLastName);

		editDisp = (EditText) mView.findViewById(R.id.txtChiledItemDisplayName);

		editCity = (EditText) mView.findViewById(R.id.txtChiledItemCity);

		editCountry = (EditText) mView.findViewById(R.id.txtChiledItemCountry);

		editEmail = (EditText) mView.findViewById(R.id.txtChiledItemEmail);
		Button saveProfileB = (Button) mView
				.findViewById(R.id.saveProfileButton);
		saveProfileB.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				saveData();
			}
		});

		map = con.getPlayer(Configurations.CurrentPlayerID);
		// Bundle args = getArguments();
		if (map != null) {
			editFirst.setText(map.getString(Keys.FirstName));
			editLast.setText(map.getString(Keys.LastName));
			editDisp.setText(map.getString(Keys.PLAYERNICKNAME));
			editCity.setText(map.getString(Keys.CITY));
			editCountry.setText(map.getString(Keys.COUNTRY));
			editEmail.setText(map.getString(Keys.Email));
		}
	}

	public void saveData() {
		boolean state = true;
		editFirst.setError(null);
		editLast.setError(null);
		editDisp.setError(null);
		editCity.setError(null);
		editCountry.setError(null);
		editEmail.setError(null);

		if (HelperClass.isEmpty(editFirst)) {
			state = false;
			editFirst.setError("Empty First Name Field.");
		} else if (HelperClass.isEmpty(editLast)) {
			state = false;
			editLast.setError("Empty Last Name Field.");
		} else if (HelperClass.isEmpty(editCity)) {
			state = false;
			editCity.setError("Empty Field City.");
		} else if (HelperClass.isEmpty(editCountry)) {
			state = false;
			editCountry.setError("Empty Field County.");
		} else if (HelperClass.isEmpty(editEmail)) {
			state = false;
			editEmail.setError("Empty Field Email.");
		} else if (HelperClass.isEmpty(editDisp)) {
			state = false;
			editDisp.setError("Empty Field Nick Name.");
		} else if (!HelperClass.EmailValidation(editEmail)) {
			state = false;
			editEmail.setError("Incorrect Email.");
		}

		if (state) {
			con.savePersonalInfo(map, editFirst, editLast, editDisp, editCity,
					editCountry, editEmail, getActivity());
		}
	}

	@Override
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
