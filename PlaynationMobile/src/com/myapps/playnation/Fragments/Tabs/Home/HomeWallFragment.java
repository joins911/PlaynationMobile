package com.myapps.playnation.Fragments.Tabs.Home;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
		View footer = inflater.inflate(R.layout.component_comment_footer, null);
		Button commentBut = (Button) footer.findViewById(R.id.wallF_commBut);
		EditText commentText = (EditText) footer
				.findViewById(R.id.wallF_comment_EBox);
		commentBut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		eListView.setAdapter(new HomExpandableAdapter(context, listParents,
				eListView, this));
		eListView.addFooterView(footer);

		return view;
	}
}
