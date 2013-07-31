package com.myapps.playnation.Fragments.Tabs.Home;

import java.util.ArrayList;
import java.util.HashMap;

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
			con.queryPlayerMessages(Keys.TEMPLAYERID);

		listParents.clear();
		for (HashMap<String, String> hashMap : con.getTable(Keys.HomeMsgTable,
				"")) {
			ExpandbleParent parentItem = new ExpandbleParent();
			parentItem.setFirstChild(hashMap);
			listParents.add(parentItem);
		}

		eListView.setAdapter(new HomExpandableAdapter(getActivity(),
				listParents, eListView, this));
		return view;
	}
}
