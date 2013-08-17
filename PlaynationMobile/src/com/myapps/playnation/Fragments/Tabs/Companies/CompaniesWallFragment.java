package com.myapps.playnation.Fragments.Tabs.Companies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.myapps.playnation.R;
import com.myapps.playnation.Adapters.CommExpListAdapter;
import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Operations.DataConnector;

public class CompaniesWallFragment extends Fragment {
	DataConnector con;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		con = DataConnector.getInst(getActivity());
		View mView = inflater.inflate(R.layout.fragment_template_wall,
				container, false);
		// Button commentBut = (Button) mView.findViewById(R.id.wallF_commBut);
		EditText commentText = (EditText) mView
				.findViewById(R.id.wallF_comment_EBox);
		ExpandableListView expList = (ExpandableListView) mView
				.findViewById(R.id.fragMsgAndWallTemp_expList);
		CommExpListAdapter expAdapter = new CommExpListAdapter(getActivity(),
				con.getComments(getArguments().getString(Keys.EventID_COMPANY),
						"company"));
		expList.setAdapter(expAdapter);
		/*
		 * commentBut.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { Log.i("Games Wall",
		 * "Comment Button Pressed"); // insert the comment } });
		 */
		for (int i = 0; i < expAdapter.getGroupCount(); i++)
			expList.expandGroup(i);
		// Inflate the layout for this fragment
		return mView;
	}
}
