package com.myapps.playnation.Fragments.Tabs.Home;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

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
			con.queryPlayerWall(Keys.TEMPLAYERID);
		}

		listParents.clear();
		for (Bundle hashMap : con.getTable(Keys.HomeWallTable, "")) {
			ExpandbleParent parentItem = new ExpandbleParent();
			parentItem.setFirstChild(hashMap);
			listParents.add(parentItem);
		}

		ExpandableListView eListView = (ExpandableListView) view
				.findViewById(R.id.fragMsgAndWallTemp_expList);

		eListView.setAdapter(new HomExpandableAdapter(context, listParents,
				eListView, this));
		return view;
	}
}
