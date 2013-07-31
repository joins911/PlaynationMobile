package com.myapps.playnation.Fragments.Tabs.Players;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.myapps.playnation.R;

public class PlayerSubscriptionFragment extends Fragment {
	
	Context context;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_home_main, container,
				false);
			return view;
	}

	
}
