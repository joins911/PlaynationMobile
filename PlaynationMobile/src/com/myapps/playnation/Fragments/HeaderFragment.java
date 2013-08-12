package com.myapps.playnation.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myapps.playnation.R;
import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Fragments.Tabs.Home.HomeEditProfileFragment;
import com.myapps.playnation.Operations.DataConnector;
import com.myapps.playnation.main.ISectionAdapter;

public class HeaderFragment extends Fragment {
	private TabHostDesc mTabFrag;
	private View mView;
	private TextView gName;
	private TextView gType;
	private TextView ratingTV;
	private TextView gRating;
	private int state;
	private DataConnector con;
	ISectionAdapter actContext;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		actContext = (ISectionAdapter) getActivity();
		if (state == Keys.HomeSTATE) {
			mTabFrag = new HomeTabHostDesc();
		}
		if (state == Keys.GamesSTATE) {
			mTabFrag = new GameTabHostDesc();
		}
		if (state == Keys.GroupsSTATE) {
			mTabFrag = new GroupTabHostDesc();
		}
		if (state == Keys.PlayersSTATE) {
			mTabFrag = new PlayersTabHostDesc();
		}
		if (state == Keys.CompaniesSTATE) {
			mTabFrag = new CompaniesTabHostDesc();
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		state = getArguments().getInt(Keys.ARG_POSITION);
		con = DataConnector.getInst(getActivity());
		if (state == Keys.HomeSTATE) {
			mTabFrag = new HomeTabHostDesc();
			mTabFrag.setArguments(getArguments());
			getChildFragmentManager().beginTransaction()
					.replace(R.id.container_home, mTabFrag).commit();
		}
		if (state == Keys.GamesSTATE) {
			mTabFrag = new GameTabHostDesc();
			mTabFrag.setArguments(getArguments());
			getChildFragmentManager().beginTransaction()
					.replace(R.id.container_games, mTabFrag).commit();
		}
		if (state == Keys.GroupsSTATE) {
			mTabFrag = new GroupTabHostDesc();
			mTabFrag.setArguments(getArguments());
			getChildFragmentManager().beginTransaction()
					.replace(R.id.container_games, mTabFrag).commit();
		}
		if (state == Keys.PlayersSTATE) {
			mTabFrag = new PlayersTabHostDesc();
			mTabFrag.setArguments(getArguments());
			getChildFragmentManager().beginTransaction()
					.replace(R.id.container_home, mTabFrag).commit();
		}
		if (state == Keys.CompaniesSTATE) {
			mTabFrag = new CompaniesTabHostDesc();
			mTabFrag.setArguments(getArguments());
			getChildFragmentManager().beginTransaction()
					.replace(R.id.container_games, mTabFrag).commit();
		}
	}

	public void onSavedInstanceState(Bundle outState) {
		outState.putAll(getArguments());
		super.onSaveInstanceState(outState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View header = null;

		if (state == Keys.HomeSTATE) {
			mView = inflater.inflate(R.layout.wrapper_header_home, container,
					false);
			header = mView.findViewById(R.id.include_TabHolder_Home);
			con.queryPlayerInfo(Keys.TEMPLAYERID);
			TextView edit = (TextView) header.findViewById(R.id.txtEdit);
			edit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					getChildFragmentManager()
							.beginTransaction()
							.replace(android.R.id.tabcontent,
									new HomeEditProfileFragment()).commit();

				}
			});
			header = con.populatePlayerGeneralInfo(header, "Wall");
		} else {
			mView = inflater.inflate(R.layout.wrapper_header_games, container,
					false);
			header = mView.findViewById(R.id.include_TabHolder_Games);
			gName = (TextView) header.findViewById(R.id.gameM_NameText_TView);
			gType = (TextView) header.findViewById(R.id.gameM_TypeText_TView);
			gRating = (TextView) header
					.findViewById(R.id.gameM_RatingsNr_TView);
			ratingTV = (TextView) header
					.findViewById(R.id.gamesM_ratingString_TView);

			if (state == Keys.GamesSTATE) {
				gName.setText(getArguments().getString(Keys.GAMENAME));
				gType.setText(getArguments().getString(Keys.GAMETYPE));
				gRating.setText(getArguments().getString(Keys.RATING));
			}
			if (state == Keys.GroupsSTATE) {
				gName.setText(getArguments().getString(Keys.GROUPNAME));
				gType.setText(getArguments().getString(Keys.GROUPTYPE) + " "
						+ getArguments().getString(Keys.GROUPTYPE2));
				gRating.setText(getArguments().getString(Keys.GROUPDATE));
				ratingTV.setText("");
			}
			if (state == Keys.PlayersSTATE) {
				mView = inflater.inflate(R.layout.wrapper_header_home,
						container, false);
				header = mView.findViewById(R.id.include_TabHolder_Home);
				TextView edit = (TextView) mView.findViewById(R.id.txtEdit);
				edit.setVisibility(View.GONE);

				con.queryPlayerInfo(getArguments().getString(Keys.ID_PLAYER));
				header = con.populatePlayerGeneralInfo(header, "Wall");

			}
			if (state == Keys.CompaniesSTATE) {
				gName.setText(getArguments().getString(Keys.CompanyName));
				gType.setText(getArguments().getString(Keys.CompanyOwnership));
				gRating.setText(getArguments().getString(
						Keys.CompanySocialRating));

				ratingTV.setText("");
			}
		}
		return mView;
	}

	public void switchToTab(int tabIndex) {
		if (mTabFrag != null)
			mTabFrag.switchToTab(tabIndex);
	}

	@Override
	public void onDestroy() {
		mView = null;
		super.onDestroy();
	}
}
