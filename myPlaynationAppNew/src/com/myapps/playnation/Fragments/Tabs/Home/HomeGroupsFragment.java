package com.myapps.playnation.Fragments.Tabs.Home;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.myapps.playnation.R;
import com.myapps.playnation.Adapters.HomeListViewAdapter;
import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Operations.DataConnector;
import com.myapps.playnation.main.ISectionAdapter;

public class HomeGroupsFragment extends Fragment {
	private DataConnector con;
	@SuppressWarnings("unused")
	private ISectionAdapter mCallback;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception
		try {
			mCallback = (ISectionAdapter) getActivity();
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnHeadlineSelectedListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_template_tabslistview,
				container, false);
		con = DataConnector.getInst(getActivity());

		ListView mListView = (ListView) view
				.findViewById(R.id.generalPlayerListView);

		if (!con.checkDBTableExits(Keys.HomeGroupTable))
			con.queryPlayerGroup(Keys.TEMPLAYERID);

		mListView.setAdapter(new HomeListViewAdapter(getActivity(), con
				.getTable(Keys.HomeGroupTable, ""), this));
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) { // TODO Auto-generated method stub
				Bundle args = new Bundle();

				@SuppressWarnings("unchecked")
				HashMap<String, String> mapEntry = (HashMap<String, String>) arg0
						.getItemAtPosition(arg2);
				args.putString(Keys.Segment, Keys.HomeGroupTable);

				args.putString(Keys.GROUPNAME, mapEntry.get(Keys.GROUPNAME));
				args.putString(Keys.GROUPTYPE, mapEntry.get(Keys.GROUPTYPE));
				args.putString(Keys.GROUPTYPE2, mapEntry.get(Keys.GROUPTYPE2));
				args.putString(Keys.GroupMemberCount,
						mapEntry.get(Keys.GroupMemberCount));
				args.putString(Keys.GROUPDATE, mapEntry.get(Keys.GROUPDATE));
				args.putString(Keys.GROUPDESC, mapEntry.get(Keys.GROUPDESC));
				args.putString(Keys.GruopIsLeader,
						mapEntry.get(Keys.GruopIsLeader));
				args.putString(Keys.GruopCreatorName,
						mapEntry.get(Keys.GruopCreatorName));
				mCallback.setPageAndTab(Keys.GroupsSTATE, 2, args);
			}
		});

		return view;
	}
}
