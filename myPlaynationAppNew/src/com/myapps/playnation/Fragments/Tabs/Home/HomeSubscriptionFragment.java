package com.myapps.playnation.Fragments.Tabs.Home;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.myapps.playnation.R;
import com.myapps.playnation.Adapters.HomeListViewAdapter;
import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Operations.DataConnector;

public class HomeSubscriptionFragment extends Fragment {
	private DataConnector con;
	Context context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_template_tabslistview,
				container, false);
		context = getActivity();
		con = DataConnector.getInst(getActivity());

		ListView mListView = (ListView) view
				.findViewById(R.id.generalPlayerListView);
		if (!con.checkDBTableExits(Keys.HomeSubscriptionTable)) {
			con.queryPlayerSubscription(Keys.TEMPLAYERID);
		}
		mListView.setAdapter(new HomeListViewAdapter(context, con.getTable(
				Keys.HomeSubscriptionTable, ""), this));
		return view;
	}
}
