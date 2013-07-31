package com.myapps.playnation.Fragments.Tabs.Home;

import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

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

		mListView.setAdapter(new PlayerHomeInfoAdapter(getActivity(), con
				.getTable(Keys.HomeFriendsTable, "")));
		mListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				@SuppressWarnings("unchecked")
				HashMap<String, String> results = (HashMap<String, String>) parent
						.getItemAtPosition(position);
				Bundle args = new Bundle();
				args.putString(Keys.ID_PLAYER, results.get(Keys.ID_PLAYER));
				args.putString(Keys.CITY, results.get(Keys.CITY));
				args.putString(Keys.COUNTRY, results.get(Keys.COUNTRY));
				args.putString(Keys.PLAYERNICKNAME,
						results.get(Keys.PLAYERNICKNAME));
				args.putString(Keys.Email, results.get(Keys.Email));
				args.putString(Keys.PLAYERAVATAR,
						results.get(Keys.PLAYERAVATAR));
				args.putString(Keys.FirstName, results.get(Keys.FirstName));
				args.putString(Keys.LastName, results.get(Keys.LastName));

				mCall.setPageAndTab(Keys.PlayersSTATE, 4, args);
			}
		});
		return view;
	}
}
