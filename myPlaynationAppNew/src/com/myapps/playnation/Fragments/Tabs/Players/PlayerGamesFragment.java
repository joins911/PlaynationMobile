package com.myapps.playnation.Fragments.Tabs.Players;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.myapps.playnation.R;
import com.myapps.playnation.Adapters.HomeListViewAdapter;
import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Operations.DataConnector;
import com.myapps.playnation.main.ISectionAdapter;

public class PlayerGamesFragment extends Fragment {
	private DataConnector con;
	private ISectionAdapter mCallback;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception
		try {
			mCallback = (ISectionAdapter) getActivity();
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnHeadlineSelectedListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_template_tabslistview,
				container, false);
		con = DataConnector.getInst(getActivity());

		ListView mListView = (ListView) view
				.findViewById(R.id.generalPlayerListView);

		Bundle args = getArguments();
		con.queryPlayerGames(args.getString(Keys.ID_PLAYER));
		mListView.setAdapter(new HomeListViewAdapter(getActivity(), con
				.getTable(Keys.HomeGamesTable, ""), this));
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				@SuppressWarnings("unchecked")
				HashMap<String, String> mapEntry = (HashMap<String, String>) arg0
						.getItemAtPosition(arg2);
				Bundle args = new Bundle();
				args.putString(Keys.Segment, Keys.HomeGamesTable);
				args.putString(Keys.GAMENAME, mapEntry.get(Keys.GAMENAME));
				args.putString(Keys.GAMEDESC, mapEntry.get(Keys.GAMEDESC));
				args.putString(Keys.GAMEDATE, mapEntry.get(Keys.GAMEDATE));
				args.putString(Keys.RATING, mapEntry.get(Keys.RATING));
				args.putString(Keys.ID_GAME, mapEntry.get(Keys.ID_GAME));
				mCallback.setPageAndTab(Keys.GamesSTATE, 3, args);
			}
		});
		return view;
	}
}