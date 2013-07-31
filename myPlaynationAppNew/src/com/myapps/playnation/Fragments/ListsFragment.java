package com.myapps.playnation.Fragments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.myapps.playnation.R;
import com.myapps.playnation.Adapters.CompanyListAdapter;
import com.myapps.playnation.Adapters.FriendsListAdapter;
import com.myapps.playnation.Adapters.GamesListAdapter;
import com.myapps.playnation.Adapters.GroupsListAdapter;
import com.myapps.playnation.Adapters.NewsListAdapter;
import com.myapps.playnation.Classes.Keys;
import com.myapps.playnation.Classes.NewsFeed;
import com.myapps.playnation.Operations.DataConnector;
import com.myapps.playnation.Operations.HelperClass;
import com.myapps.playnation.main.ISectionAdapter;

public class ListsFragment extends Fragment {

	private DataConnector con;
	private View rootView;
	private int mViewPagerState;
	private ISectionAdapter mCallback;
	private ViewFlipper flipper = null;

	public ListsFragment() {
		con = DataConnector.getInst(getActivity());
		// setRetainInstance(true);
	}

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
		rootView = inflater.inflate(R.layout.component_mainlist, container,
				false);
		mViewPagerState = this.getArguments().getInt(Keys.ARG_POSITION);
		ListView list = (ListView) rootView.findViewById(R.id.mainList);

		if (HelperClass.isTablet(getActivity())) {
			flipper = (ViewFlipper) rootView.findViewById(R.id.viewFlipper1);
		}

		if (Keys.GamesSTATE == mViewPagerState) {
			initializeGames(list);
		} else if (Keys.GroupsSTATE == mViewPagerState) {
			initializeGroups(list);
		} else if (Keys.NewsSTATE == mViewPagerState) {
			initializeNews(list);
		} else if (Keys.PlayersSTATE == mViewPagerState) {
			initializePlayers(list);
		} else if (Keys.CompaniesSTATE == mViewPagerState) {
			initializeCompanies(list);
		}
		return rootView;
	}

	private void initializeGames(ListView list) {
		final ArrayList<HashMap<String, String>> results = con.getTable(
				Keys.gamesTable, "");

		list = (ListView) rootView.findViewById(R.id.mainList);
		LinearLayout rs = (LinearLayout) rootView.findViewById(R.id.searchLL);
		rs.setVisibility(View.GONE);

		GamesListAdapter bindingData = new GamesListAdapter(getActivity(),
				results);
		list.setAdapter(bindingData);

		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Bundle args = new Bundle();
				args.putString(Keys.GAMENAME,
						results.get(position).get(Keys.GAMENAME));
				args.putString(Keys.GAMETYPE,
						results.get(position).get(Keys.GAMETYPE));
				args.putString(Keys.RATING,
						results.get(position).get(Keys.RATING));
				args.putString(Keys.GAMEDATE,
						results.get(position).get(Keys.GAMEDATE));
				args.putString(Keys.GAMEDESC,
						results.get(position).get(Keys.GAMEDESC));
				args.putString(Keys.GAMEESRB,
						results.get(position).get(Keys.GAMEESRB));
				args.putString(Keys.GAMEURL,
						results.get(position).get(Keys.GAMEURL));
				args.putString(Keys.GAMEPLAYERSCOUNT, results.get(position)
						.get(Keys.GAMEPLAYERSCOUNT));
				args.putString(Keys.ID_GAME,
						results.get(position).get(Keys.ID_GAME));

				// For the other stuff on game info
				args.putString(Keys.GAMETYPENAME,
						results.get(position).get(Keys.GAMETYPENAME));
				args.putString(Keys.GAMEPLATFORM,
						results.get(position).get(Keys.GAMEPLATFORM));
				args.putString(Keys.GAMECompanyDistributor,
						results.get(position).get(Keys.GAMECompanyDistributor));
				args.putString(Keys.CompanyFounded,
						results.get(position).get(Keys.CompanyFounded));
				args.putString(Keys.CompanyName,
						results.get(position).get(Keys.CompanyName));
				// if (!con.checkDBTableExits(Keys.newsTempTable))
				con.writeTempNewsTab(results.get(position).get(Keys.ID_GAME),
						Keys.gamesubNewsTAB);

				tabletOrPhoneControll(Keys.GamesSTATE, args);
				// mCallback.getAdapter().switchTo(Keys.GamesSTATE, args);
				// getChildFragmentManager().executePendingTransactions();

			}
		});
	}

	private void initializeGroups(ListView list) {
		final ArrayList<HashMap<String, String>> results = con.getTable(
				Keys.groupsTable, "");
		list = (ListView) rootView.findViewById(R.id.mainList);
		LinearLayout rs = (LinearLayout) rootView.findViewById(R.id.searchLL);
		rs.setVisibility(View.GONE);

		GroupsListAdapter bindingData = new GroupsListAdapter(getActivity(),
				results);
		list.setAdapter(bindingData);
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Bundle args = new Bundle();
				args.putString(Keys.GROUPNAME,
						results.get(position).get(Keys.GROUPNAME));
				args.putString(Keys.GROUPTYPE,
						results.get(position).get(Keys.GROUPTYPE));
				args.putString(Keys.GROUPTYPE2,
						results.get(position).get(Keys.GROUPTYPE2));
				args.putString(Keys.GROUPDATE,
						results.get(position).get(Keys.GROUPDATE));
				args.putString(Keys.GroupMemberCount, results.get(position)
						.get(Keys.GroupMemberCount));
				args.putString(Keys.GROUPDESC,
						results.get(position).get(Keys.GROUPDESC));
				args.putString(Keys.GruopIsLeader,
						results.get(position).get(Keys.GruopIsLeader));
				args.putString(Keys.ID_GROUP,
						results.get(position).get(Keys.ID_GROUP));
				args.putString(Keys.GruopCreatorName, results.get(position)
						.get(Keys.GruopCreatorName));
				// mCallback.getAdapter().switchTo(Keys.GroupsSTATE, args);
				tabletOrPhoneControll(Keys.GroupsSTATE, args);
				// getChildFragmentManager().executePendingTransactions();

			}
		});
	}

	private void initializeNews(ListView list) {
		final ArrayList<HashMap<String, String>> results = con.getTable(
				Keys.newsTable, "");
		list = (ListView) rootView.findViewById(R.id.mainList);
		LinearLayout rs = (LinearLayout) rootView.findViewById(R.id.searchLL);
		rs.setVisibility(View.GONE);

		NewsListAdapter bindingData = new NewsListAdapter(getActivity(),
				HelperClass.createHeaderListView(HelperClass
						.queryNewsList(results)));
		list.setAdapter(bindingData);
		list.setOnItemClickListener(new OnItemClickListener() {

			@SuppressLint("SimpleDateFormat")
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (parent.getItemAtPosition(position) instanceof NewsFeed) {
					NewsFeed feed = (NewsFeed) parent
							.getItemAtPosition(position);
					Bundle edit = new Bundle();
					SimpleDateFormat format = con.dataTemplate;

					edit.putInt(Keys.NEWSCOLID_NEWS, feed.getKey_NewsFeedID());
					edit.putInt(Keys.NEWSCOLIMAGE, feed.getKey_NewsImage());
					edit.putString(Keys.NEWSCOLHEADLINE,
							feed.getKey_NewsTitle());
					edit.putString(Keys.NEWSCOLINTROTEXT,
							feed.getKey_NewsIntroText());
					edit.putString(Keys.NEWSCOLNEWSTEXT, feed.getKey_NewsText());
					edit.putString(Keys.Author, feed.getKey_Author());
					edit.putString(Keys.NEWSCOLPOSTINGTIME,
							format.format(feed.getKey_NewsDate().getTime()));

					if (flipper != null) {
						flipper.setDisplayedChild(2);
						flipper.showNext();
					}
					mCallback.getAdapter().switchTo(Keys.NewsSTATE, edit);
				} else {
					if (flipper != null) {
						flipper.setDisplayedChild(1);
						flipper.showNext();
					}
				}
			}
		});
	}

	private void tabletOrPhoneControll(int state, Bundle edit) {
		if (flipper != null) {
			flipper.setDisplayedChild(2);
			flipper.showNext();
			mCallback.getAdapter().switchTo(state, edit);
		} else {
			mCallback.getAdapter().switchTo(state, edit);
		}
	}

	private void initializePlayers(ListView list) {

		final EditText edit = (EditText) rootView.findViewById(R.id.editText1);

		final ArrayList<HashMap<String, String>> results = con
				.queryPlayerFriendsSearch(edit.getText());
		list = (ListView) rootView.findViewById(R.id.mainList);
		if (results != null) {
			FriendsListAdapter bindingData = new FriendsListAdapter(
					getActivity(), results);
			list.setAdapter(bindingData);
			list.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent,
						View view, int position, long id) {
					Bundle args = new Bundle();
					args.putString(Keys.ID_PLAYER, results
							.get(position).get(Keys.ID_PLAYER));
					args.putString(Keys.CITY, results.get(position)
							.get(Keys.CITY));
					args.putString(Keys.COUNTRY, results.get(position)
							.get(Keys.COUNTRY));
					args.putString(
							Keys.PLAYERNICKNAME,
							results.get(position).get(
									Keys.PLAYERNICKNAME));
					args.putString(Keys.Email, results.get(position)
							.get(Keys.Email));
					args.putString(Keys.PLAYERAVATAR,
							results.get(position)
									.get(Keys.PLAYERAVATAR));
					args.putString(Keys.FirstName, results
							.get(position).get(Keys.FirstName));
					args.putString(Keys.LastName, results.get(position)
							.get(Keys.LastName));

					tabletOrPhoneControll(Keys.PlayersSTATE, args);
				}
			});
		}

		//Players ListView initialized twice Could cause bugs later on. NEEDS TO CHANGE!
		
		Button btn = (Button) rootView.findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final ArrayList<HashMap<String, String>> results = con
						.queryPlayerFriendsSearch(edit.getText());
				ListView list = (ListView) rootView.findViewById(R.id.mainList);
				if (results != null) {
					FriendsListAdapter bindingData = new FriendsListAdapter(
							getActivity(), results);
					list.setAdapter(bindingData);
					list.setOnItemClickListener(new OnItemClickListener() {
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							Bundle args = new Bundle();
							args.putString(Keys.ID_PLAYER, results
									.get(position).get(Keys.ID_PLAYER));
							args.putString(Keys.CITY, results.get(position)
									.get(Keys.CITY));
							args.putString(Keys.COUNTRY, results.get(position)
									.get(Keys.COUNTRY));
							args.putString(
									Keys.PLAYERNICKNAME,
									results.get(position).get(
											Keys.PLAYERNICKNAME));
							args.putString(Keys.Email, results.get(position)
									.get(Keys.Email));
							args.putString(Keys.PLAYERAVATAR,
									results.get(position)
											.get(Keys.PLAYERAVATAR));
							args.putString(Keys.FirstName, results
									.get(position).get(Keys.FirstName));
							args.putString(Keys.LastName, results.get(position)
									.get(Keys.LastName));

							tabletOrPhoneControll(Keys.PlayersSTATE, args);
						}
					});
					
				}
			}
		});

		// GroupsListAdapter bindingData = new GroupsListAdapter(getActivity(),
		// results);
		// list.setAdapter(bindingData);
		// list.setOnItemClickListener(new OnItemClickListener() {
		// public void onItemClick(AdapterView<?> parent, View view,
		// int position, long id) {
		// Bundle args = new Bundle();
		// args.putString(Keys.PLAYERNAME,
		// results.get(position).get(Keys.GROUPNAME));
		// args.putString(Keys.PLAYERTYPE,
		// results.get(position).get(Keys.GROUPTYPE));
		//
		// args.putString(Keys.PLAYERDATE,
		// results.get(position).get(Keys.GROUPDATE));
		// mCallback.getAdapter().switchTo(Keys.PlayersSTATE, args);
		// // getChildFragmentManager().executePendingTransactions();
		//
		// }
		// });
	}

	private void initializeCompanies(ListView list) {
		final ArrayList<HashMap<String, String>> results = con.getTable(
				Keys.companyTable, "");
		list = (ListView) rootView.findViewById(R.id.mainList);
		LinearLayout rs = (LinearLayout) rootView.findViewById(R.id.searchLL);
		rs.setVisibility(View.GONE);

		CompanyListAdapter bindingData = new CompanyListAdapter(getActivity(),
				results);
		list.setAdapter(bindingData);
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Bundle args = new Bundle();
				args.putString(Keys.EventID_COMPANY,
						results.get(position).get(Keys.EventID_COMPANY));
				args.putString(Keys.CompanyAddress,
						results.get(position).get(Keys.CompanyAddress));
				args.putString(Keys.CompanyCreatedTime, results.get(position)
						.get(Keys.CompanyCreatedTime));
				args.putString(Keys.CompanyDesc,
						results.get(position).get(Keys.CompanyDesc));
				args.putString(Keys.CompanyEmployees, results.get(position)
						.get(Keys.CompanyEmployees));
				args.putString(Keys.CompanyEventCount, results.get(position)
						.get(Keys.CompanyEventCount));
				args.putString(Keys.CompanyFounded,
						results.get(position).get(Keys.CompanyFounded));
				args.putString(Keys.CompanyGameCount, results.get(position)
						.get(Keys.CompanyGameCount));
				args.putString(Keys.CompanyImageURL,
						results.get(position).get(Keys.CompanyImageURL));
				args.putString(Keys.CompanyName,
						results.get(position).get(Keys.CompanyName));
				args.putString(Keys.CompanyNewsCount, results.get(position)
						.get(Keys.CompanyNewsCount));
				args.putString(Keys.CompanyOwnership, results.get(position)
						.get(Keys.CompanyOwnership));
				args.putString(Keys.CompanySocialRating, results.get(position)
						.get(Keys.CompanySocialRating));
				args.putString(Keys.CompanyURL,
						results.get(position).get(Keys.CompanyURL));
				args.putString(Keys.CompanyType,
						results.get(position).get(Keys.CompanyType));

				// if (!con.checkDBTableExits(Keys.companyTempTable))
				con.writeTempNewsTab(
						results.get(position).get(Keys.EventID_COMPANY),
						Keys.companysubNewsTAB);

				tabletOrPhoneControll(Keys.CompaniesSTATE, args);
				// mCallback.getAdapter().switchTo(Keys.CompaniesSTATE, args);
			}
		});
	}

}
