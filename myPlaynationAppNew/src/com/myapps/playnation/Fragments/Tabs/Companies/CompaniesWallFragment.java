package com.myapps.playnation.Fragments.Tabs.Companies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.myapps.playnation.R;
import com.myapps.playnation.Adapters.CommExpListAdapter;
import com.myapps.playnation.Operations.DataConnector;

public class CompaniesWallFragment extends Fragment {
	DataConnector con;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		con = DataConnector.getInst(getActivity());
		View mView = inflater.inflate(R.layout.fragment_template_wall,
				container, false);
		ExpandableListView expList = (ExpandableListView) mView
				.findViewById(R.id.fragMsgAndWallTemp_expList);
		CommExpListAdapter expAdapter = new CommExpListAdapter(getActivity(),
				con.getComments());
		expList.setAdapter(expAdapter);
		for (int i = 0; i < expAdapter.getGroupCount(); i++)
			expList.expandGroup(i);
		// Inflate the layout for this fragment
		return mView;
	}
}
