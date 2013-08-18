package com.myapps.playnation.Fragments.Tabs.Home;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myapps.playnation.R;
import com.myapps.playnation.Adapters.HomExpandableAdapter;
import com.myapps.playnation.Classes.ExpandbleParent;
import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Operations.DataConnector;

public class HomeWallFragment extends Fragment {
	private DataConnector con;
	private ArrayList<ExpandbleParent> listParents = new ArrayList<ExpandbleParent>();

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_template_wall,
				container, false);
		con = DataConnector.getInst(getActivity());
		Context context = getActivity();
		if (!con.checkDBTableExits(Keys.HomeWallTable)) {
			con.queryPlayerWall(Keys.TEMPLAYERID, "player");
		}

		listParents.clear();
		ArrayList<Bundle> list = con.getTable(Keys.HomeWallTable,
				Keys.TEMPLAYERID);
		if (list != null)
			for (Bundle hashMap : list) {
				if (hashMap != null) {
					ExpandbleParent parentItem = new ExpandbleParent();
					parentItem.setFirstChild(hashMap);
					listParents.add(parentItem);
				}
			}

		ExpandableListView eListView = (ExpandableListView) view
				.findViewById(R.id.fragMsgAndWallTemp_expList);
		HomExpandableAdapter expAdapter = new HomExpandableAdapter(context,
				listParents, eListView, this);
		eListView.setAdapter(expAdapter);

		if (expAdapter.isEmpty()) {
			RelativeLayout rl = (RelativeLayout) view
					.findViewById(R.id.fragMsgAndWallTemp);

			TextView msgText = new TextView(getActivity());
			msgText.setText(R.string.emptyListString);
			msgText.setTextColor(Color.parseColor("#CFCFCF"));
			msgText.setTextSize(TypedValue.COMPLEX_UNIT_SP, Keys.testSize);
			msgText.setGravity(Gravity.CENTER_HORIZONTAL);
			rl.addView(msgText);

		}
		return view;
	}
}
