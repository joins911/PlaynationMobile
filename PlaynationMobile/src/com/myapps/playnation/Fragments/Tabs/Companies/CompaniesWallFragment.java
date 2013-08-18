package com.myapps.playnation.Fragments.Tabs.Companies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.myapps.playnation.R;
import com.myapps.playnation.Adapters.CommExpListAdapter;
import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Operations.DataConnector;

public class CompaniesWallFragment extends Fragment {
	DataConnector con;
	EditText commentText;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		con = DataConnector.getInst(getActivity());
		View mView = inflater.inflate(R.layout.fragment_template_wall,
				container, false);
		ExpandableListView expList = (ExpandableListView) mView
				.findViewById(R.id.fragMsgAndWallTemp_expList);
		CommExpListAdapter expAdapter = new CommExpListAdapter(getActivity(),
				con.getComments(getArguments().getString(Keys.EventID_COMPANY),
						"company"));
		View footer = inflater.inflate(R.layout.component_comment_footer, null);
		Button commentBut = (Button) footer.findViewById(R.id.wallsF_commBut);
		commentText = (EditText) footer.findViewById(R.id.wallsF_comment_EBox);
		commentBut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("Games Wall", "Comment Button Pressed"
						+ commentText.getText().toString());
			}
		});
		expList.addFooterView(footer);
		expList.setAdapter(expAdapter);
		for (int i = 0; i < expAdapter.getGroupCount(); i++)
			expList.expandGroup(i);
		// Inflate the layout for this fragment
		return mView;
	}

}
