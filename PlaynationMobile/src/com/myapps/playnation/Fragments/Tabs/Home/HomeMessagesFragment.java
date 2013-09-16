package com.myapps.playnation.Fragments.Tabs.Home;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.myapps.playnation.R;
import com.myapps.playnation.Adapters.HomExpandableAdapter;
import com.myapps.playnation.Classes.ExpandbleParent;
import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Operations.Configurations;
import com.myapps.playnation.Operations.DataConnector;

public class HomeMessagesFragment extends Fragment {
	private DataConnector con;
	private ArrayList<ExpandbleParent> listParents = new ArrayList<ExpandbleParent>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_template_wall,
				container, false);
		con = DataConnector.getInst(getActivity());

		ExpandableListView eListView = (ExpandableListView) view
				.findViewById(R.id.fragMsgAndWallTemp_expList);

		if (!con.checkDBTableExits(Keys.HomeMsgTable))
			con.queryPlayerMessages(Configurations.CurrentPlayerID);

		listParents.clear();
		ArrayList<Bundle> list = con.getTable(Keys.HomeMsgTable,
				Configurations.CurrentPlayerID);
		if (list != null)
			for (Bundle hashMap : list) {
				if (hashMap != null) {
					ExpandbleParent parentItem = new ExpandbleParent();
					parentItem.setFirstChild(hashMap);
					listParents.add(parentItem);
				}
			}
		HomExpandableAdapter expAdapter = new HomExpandableAdapter(
				getActivity(), listParents, eListView, this);

		if (expAdapter.isEmpty()) {

			TextView msgText = new TextView(getActivity());
			msgText.setText(R.string.emptyMsgListString);
			msgText.setTextColor(Color.parseColor("#CFCFCF"));
			msgText.setTextSize(TypedValue.COMPLEX_UNIT_SP, Keys.testSize);
			msgText.setGravity(Gravity.CENTER_HORIZONTAL);
			eListView.addHeaderView(msgText);

		}
		eListView.setAdapter(expAdapter);
		return view;
	}
}
