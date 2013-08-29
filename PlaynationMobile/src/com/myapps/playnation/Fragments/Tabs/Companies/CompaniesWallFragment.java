package com.myapps.playnation.Fragments.Tabs.Companies;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.myapps.playnation.R;
import com.myapps.playnation.Adapters.CommExpListAdapter;
import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Operations.DataConnector;
import com.myapps.playnation.Operations.HelperClass;

public class CompaniesWallFragment extends Fragment {
	DataConnector con;
	EditText commentText;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		con = DataConnector.getInst(getActivity());
		View mView = inflater.inflate(R.layout.fragment_template_wall,
				container, false);
		final ExpandableListView expList = (ExpandableListView) mView
				.findViewById(R.id.fragMsgAndWallTemp_expList);

		CommExpListAdapter expAdapter = new CommExpListAdapter(getActivity(),
				HelperClass.modifyDataSet(
						getArguments().getString(Keys.EventID_COMPANY),
						"company"));
		View footer = inflater.inflate(R.layout.component_comment_footer, null);
		Button commentBut = (Button) footer.findViewById(R.id.wallsF_commBut);
		commentText = (EditText) footer.findViewById(R.id.wallsF_comment_EBox);
		commentBut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				con.insertComment(commentText.getText().toString(), "company",
						getArguments().getString(Keys.CompanyName),
						getArguments().getString(Keys.EventID_COMPANY));

				CommExpListAdapter expAdapter = new CommExpListAdapter(
						getActivity(), HelperClass.modifyDataSet(getArguments()
								.getString(Keys.EventID_COMPANY), "company"));
				expList.setAdapter(expAdapter);
				expAdapter.notifyDataSetChanged();
				Log.i("Games Wall", "Comment Button Pressed"
						+ commentText.getText().toString());
			}
		});
		expList.addFooterView(footer);

		if (expAdapter.isEmpty()) {

			TextView msgText = new TextView(getActivity());
			msgText.setText(R.string.emptyListString);
			msgText.setTextColor(Color.parseColor("#CFCFCF"));
			msgText.setTextSize(TypedValue.COMPLEX_UNIT_SP, Keys.testSize);
			msgText.setGravity(Gravity.CENTER_HORIZONTAL);

			expList.addHeaderView(msgText);
		}
		expList.setAdapter(expAdapter);
		for (int i = 0; i < expAdapter.getGroupCount(); i++)
			expList.expandGroup(i);
		// Inflate the layout for this fragment
		return mView;
	}

}
