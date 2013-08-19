package com.myapps.playnation.Fragments.Tabs.Game;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.myapps.playnation.R;
import com.myapps.playnation.Adapters.CommExpListAdapter;
import com.myapps.playnation.Adapters.ReviewListAdapter;
import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Operations.DataConnector;

//import android.widget.TextView;

public class GameReviewFragment extends Fragment {

	ArrayList<Bundle> reviewList;
	DataConnector con;
	CommExpListAdapter commentsAdapter;
	ReviewListAdapter reviewsAdapter;

	public GameReviewFragment() {
		con = DataConnector.getInst(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View mainView = inflater.inflate(R.layout.fragment_game_reviews, null);

		ListView reviews = (ListView) mainView
				.findViewById(R.id.frag_reviewTemp_reviewList);

		reviewList = new ArrayList<Bundle>();
		reviewList.add(con.getTable(Keys.gamesTable, "").get(3));
		reviewList.add(con.getTable(Keys.gamesTable, "").get(4));
		reviewList.add(con.getTable(Keys.gamesTable, "").get(5));
		reviewList.add(con.getTable(Keys.gamesTable, "").get(6));

		reviewsAdapter = new ReviewListAdapter(getActivity(), reviewList);
		reviews.setAdapter(reviewsAdapter);
		/*
		 * reviews.setOnItemClickListener(new OnItemClickListener() { public
		 * void onItemClick(AdapterView<?> parent, View view, int position, long
		 * id) {
		 * 
		 * Intent i = new Intent(); i.setClass(getActivity(),
		 * GameDescriptionActivity.class); i.putExtra(Keys.GAMENAME,
		 * reviewList.get(position).get(Keys.GAMENAME));
		 * i.putExtra(Keys.GAMETYPE,
		 * reviewList.get(position).get(Keys.GAMETYPE));
		 * i.putExtra(Keys.GAMEDESC,
		 * reviewList.get(position).get(Keys.GAMEDESC)); i.putExtra("date",
		 * reviewList.get(position).get(Keys.GAMEDATE)); i.putExtra(Keys.RATING,
		 * reviewList.get(position).get(Keys.RATING)); i.putExtra("STATE",
		 * Keys.GamesSTATE); // parameters i.putExtra("position",
		 * String.valueOf(position + 1)); startActivity(i); } });
		 */
		return mainView;
	}

}
