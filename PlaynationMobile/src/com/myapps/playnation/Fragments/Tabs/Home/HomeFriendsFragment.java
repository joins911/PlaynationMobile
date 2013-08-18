package com.myapps.playnation.Fragments.Tabs.Home;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myapps.playnation.R;
import com.myapps.playnation.Adapters.PlayerHomeInfoAdapter;
import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Operations.DataConnector;
import com.myapps.playnation.main.ISectionAdapter;

public class HomeFriendsFragment extends Fragment {
	private DataConnector con;
	private ISectionAdapter mCall;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_template_tabslistview,
				container, false);
		con = DataConnector.getInst(getActivity());
		mCall = (ISectionAdapter) getActivity();

		ListView mListView = (ListView) view
				.findViewById(R.id.generalPlayerListView);

		if (!con.checkDBTableExits(Keys.HomeFriendsTable))
			con.queryPlayerFriends(Keys.TEMPLAYERID);

		PlayerHomeInfoAdapter expAdapter = new PlayerHomeInfoAdapter(
				getActivity(), con.getTable(Keys.HomeFriendsTable,
						Keys.TEMPLAYERID));
		mListView.setAdapter(expAdapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Bundle results = (Bundle) parent.getItemAtPosition(position);

				mCall.setPageAndTab(Keys.PlayersSTATE, 4, results);
			}
		});

		if (expAdapter.isEmpty()) {
			RelativeLayout rl = (RelativeLayout) view
					.findViewById(R.id.generalPlayerListViewLayout);

			TextView msgText = new TextView(getActivity());
			msgText.setText(R.string.emptyFriendsListString);
			msgText.setTextColor(Color.parseColor("#CFCFCF"));
			msgText.setTextSize(TypedValue.COMPLEX_UNIT_SP, Keys.testSize);
			msgText.setGravity(Gravity.CENTER_HORIZONTAL);
			rl.addView(msgText);

		}
		return view;
	}
}
